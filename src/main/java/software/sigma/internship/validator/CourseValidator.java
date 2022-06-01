package software.sigma.internship.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CourseValidator implements ConstraintValidator<ValidCourse, Integer> {
    private final int maxCourse = 6;
    private final int minCourse = 1;

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return (value >= minCourse && value <= maxCourse);
    }
}
