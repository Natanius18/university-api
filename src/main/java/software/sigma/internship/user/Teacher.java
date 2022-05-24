package software.sigma.internship.user;

import lombok.Data;
import software.sigma.internship.user.Position;

@Data
public class Teacher {
    private Long id;
    private String firstName;
    private String lastName;
    private Position position;
}
