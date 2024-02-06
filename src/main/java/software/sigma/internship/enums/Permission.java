package software.sigma.internship.enums;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@RequiredArgsConstructor
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