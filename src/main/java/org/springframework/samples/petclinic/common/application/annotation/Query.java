package org.springframework.samples.petclinic.common.application.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

/**
 * Marks a class as a Query in the application layer. Queries are used to retrieve data
 * from the domain layer and are part of the CQRS pattern. They are immutable and should
 * not modify the system state.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Query {

	/**
	 * Alias for the {@link Component#value()} attribute. Allows for more concise
	 * annotation declarations, e.g.: {@code @Query("myQuery")} instead of
	 * {@code @Query(value = "myQuery")}
	 */
	@AliasFor(annotation = Component.class)
	String value() default "";

}
