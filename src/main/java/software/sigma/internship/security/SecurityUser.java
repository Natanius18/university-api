package software.sigma.internship.security;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import software.sigma.internship.entity.Person;
import software.sigma.internship.enums.Status;

import java.util.Collection;
import java.util.List;

@Data
@RequiredArgsConstructor
public class SecurityUser implements UserDetails {

    private final String userName;
    private final String password;
    private final List<? extends GrantedAuthority> authorities;
    private final boolean isActive;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    private boolean isActive(){
        return isActive;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive();
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public static UserDetails fromPerson(Person person) {
        return new User(
                person.getEmail(), person.getPassword(),
                person.getStatus().equals(Status.ACTIVE),
                person.getStatus().equals(Status.ACTIVE),
                person.getStatus().equals(Status.ACTIVE),
                person.getStatus().equals(Status.ACTIVE),
                person.getRole().getAuthorities()
        );
    }
}