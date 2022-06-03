package software.sigma.internship.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    private List<Test> tests;

    public Teacher(Long id, String firstName, String lastName, Position position, List<Test> tests) {
        super(id, firstName, lastName);
        this.position = position;
        this.tests = tests;
    }

    public enum Position {
        HEAD_TEACHER,
        DOCENT,
        PROFESSOR
    }
}