package software.sigma.internship.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CourseValidator implements ConstraintValidator<ValidCourse, Integer> {
    private static final int MAX_COURSE = 6;
    private static final int MIN_COURSE = 1;

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return (value >= MIN_COURSE && value <= MAX_COURSE);
    }
}
