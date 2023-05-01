package com.iprody.user.profile.api.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Email(message = "Email is not valid")
@NotBlank(message = "Email is mandatory")
@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NonExistingEmail {
    String message() default "User with this email address already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
