package au.gov.hack.health.iot.core.factory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Simple autowire annotation for dependencies which should be re-wired after DAG serialization.
 * 
 * @author davidlevy
 *
 */
@Target(value={ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Rewire {
	
	public String value() default "";
	
	public String name() default "";
	
}
