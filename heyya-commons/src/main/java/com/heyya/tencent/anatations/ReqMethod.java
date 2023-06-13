package com.heyya.tencent.anatations;
import com.heyya.http.pool.consts.HttpMethod;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface ReqMethod {
	String url();
	HttpMethod method() default HttpMethod.POST;
}
