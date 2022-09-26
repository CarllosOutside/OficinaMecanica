package com.projeto.oficina;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = CnpjValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cnpj {
	
    String message() default "Cnpj inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

