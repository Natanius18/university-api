package software.sigma.internship.enums;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@AllArgsConstructor
public enum Permission {
    READ("students:read"),
    READ_FULL("teachers:read"),
    WRITE("writeTest"),
    APPROVE_PROFILE("admin:approveProfile");

    private final String authorityName;

    public SimpleGrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(authorityName);
    }
}