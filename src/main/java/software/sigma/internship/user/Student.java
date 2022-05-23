package software.sigma.internship.user;

import lombok.Data;

@Data
public class Student {
    private Long id;
    private String firstName;
    private String lastName;
    private int course;
}
