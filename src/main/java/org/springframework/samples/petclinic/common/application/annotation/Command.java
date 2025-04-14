package org.springframework.samples.petclinic.common.application.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * Marks a class as a Command in the application layer. A command is transactional by
 * default.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Transactional
public @interface Command {

	/**
	 * Alias for the {@link Component#value()} attribute. Allows for more concise
	 * annotation declarations, e.g.: {@code @Query("myQuery")} instead of
	 * {@code @Query(value = "myQuery")}
	 */
	@AliasFor(annotation = Component.class)
	String value() default "";

}
