package indi.wzl.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HeadName {
    //表头名称
    public String value() default "";
}
