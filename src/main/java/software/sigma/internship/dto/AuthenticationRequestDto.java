package software.sigma.internship.dto;

import lombok.Getter;

@Getter
public class AuthenticationRequestDto {
    private String email;
    private String password;

    @Override
    public String toString() {
        return "AuthenticationRequestDto{" +
                "email='" + email + '\'' +
                ", password='" + password.replaceAll(".", "*") + '\'' +
                '}';
    }
}