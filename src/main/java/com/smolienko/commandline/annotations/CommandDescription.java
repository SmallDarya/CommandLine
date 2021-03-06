package com.smolienko.commandline.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * The class describe annotation for commands. It contains information about 
 * title, parameters and description. 
 * 
 * @author Darya Smolienko
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //can use in method only.
public @interface CommandDescription {
    String description() default "";
    String parameters() default "";
    String name();
}
