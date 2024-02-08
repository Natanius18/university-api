package software.sigma.internship.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import software.sigma.internship.validator.exception.JwtAuthenticationException;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static io.jsonwebtoken.security.Keys.secretKeyFor;

@Component
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

    private final SecretKey secretKey = secretKeyFor(HS256);

    @Value("${jwt.header}")
    private String authorizationHeader;

    @Value("${jwt.expires}")
    private long validityInSeconds;

    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String createToken(String username, String role) {
        var claims = Jwts.claims().setSubject(username);
        claims.put("role", role);
        var now = new Date();
        var expires = new Date(now.getTime() + validityInSeconds * 1000);

        return Jwts.builder()
                .addClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expires)
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = getClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
        }
    }

    public Authentication getAuthentication(String token) {
        var userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(authorizationHeader);
    }

    private String getUsername(String token) {
        return getClaimsJws(token).getBody().getSubject();
    }

    private Jws<Claims> getClaimsJws(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
    }
}