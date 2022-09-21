package com.projeto.oficina;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = CpfValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cpf {
	
    String message() default "Cpf inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}