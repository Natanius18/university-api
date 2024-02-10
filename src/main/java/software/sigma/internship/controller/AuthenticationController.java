package software.sigma.internship.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.internship.dto.AuthenticationRequestDto;
import software.sigma.internship.repo.PersonRepository;
import software.sigma.internship.security.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthenticationController {

    private final AuthenticationManager authManager;
    private final PersonRepository personRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequestDto request) {
        try {
            var email = request.getEmail();
            var person = personRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));
            authManager.authenticate(new UsernamePasswordAuthenticationToken(email, request.getPassword()));
            var token = jwtTokenProvider.createToken(email, person.getRole().name());
            var response = new HashMap<>();
            response.put("email", email);
            response.put("token", token);
            return ok(response);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid password. Have you confirmed your email?", FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    @ResponseStatus(NO_CONTENT)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        var handler = new SecurityContextLogoutHandler();
        handler.logout(request, response, null);
    }
}