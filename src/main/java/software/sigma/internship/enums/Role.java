package software.sigma.internship.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN(Set.of(Permission.APPROVE_PROFILE)),
    STUDENT(Set.of(Permission.READ)),
    TEACHER(Set.of(Permission.READ_FULL, Permission.WRITE)),
    USER(Set.of());

    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(Permission::getAuthority)
                .collect(Collectors.toSet());
    }
}