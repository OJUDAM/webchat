package com.example.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME)
@Target( {ElementType.PARAMETER})
public @interface AuthUser {
	public enum Role {ADMIN, USER}
	Role value() default Role.USER;
}
