package software.sigma.internship.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DiscriminatorValue("Teacher")
public class Teacher extends Person {
    private Position position;
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Test> tests;

    public enum Position {
        HEAD_TEACHER,
        DOCENT,
        PROFESSOR
    }
}