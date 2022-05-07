package ru.itis.cinema.validation.annotations;


import ru.itis.cinema.validation.validators.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
    String message() default "The password must contain numbers, lowercase and uppercase latin letters";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
