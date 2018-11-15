package com.stubhub.delivery.inject.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Dependency injector annotation, use this annotation for the fields you
 * want to inject. If there is no available injectable implementation for
 * the field, {@link IllegalStateException} will be thrown.
 *
 * @author Pavel Savinov
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {

	/**
	 * Interface to inject.
	 *
	 * @return Class object of the interface to inject.
	 */
	Class<?> type();

}
