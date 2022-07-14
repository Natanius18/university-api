package software.sigma.internship.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@AllArgsConstructor
@Getter
public enum Permission {
    READ("students:read"),
    READ_FULL("teachers:read"),
    WRITE("writeTest"),
    APPROVE_PROFILE("admin:approve-profile");

    private final String permission;

    public SimpleGrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(permission);
    }
}