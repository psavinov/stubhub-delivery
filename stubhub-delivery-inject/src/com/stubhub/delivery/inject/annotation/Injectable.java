package com.stubhub.delivery.inject.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Injectable dependency annotation. Annotate classes with this annotation to
 * make them available for injection in the @Inject-annotated fields.
 * Implementation with the highest ranking has highest priority, all the other
 * implementations will be ignored.
 *
 * @author Pavel Savinov
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Injectable {

	/**
	 * Injectable interface type.
	 *
	 * @return Class object of the interface of the injectable type.
	 */
	Class<?> type();

	/**
	 * Injectable service ranking.
	 *
	 * @return Injectable service ranking
	 */
	int ranking() default 1;

}
