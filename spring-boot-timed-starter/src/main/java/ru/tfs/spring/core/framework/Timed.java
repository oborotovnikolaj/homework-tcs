package ru.tfs.spring.core.framework;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Timed {
    boolean isEnable() default true;
}
