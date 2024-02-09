package software.sigma.internship.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import software.sigma.internship.enums.Role;
import software.sigma.internship.enums.Status;
import software.sigma.internship.validator.ValidEmail;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDto {
    private Long id;
    private String firstName;
    private String lastName;
    @ValidEmail
    private String email;
    private Role role;
    private Status status;
    @ToString.Exclude
    private String password;
    @ToString.Exclude
    private String verificationCode;
}
