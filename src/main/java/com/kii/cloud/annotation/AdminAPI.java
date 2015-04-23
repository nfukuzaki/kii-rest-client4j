package com.kii.cloud.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A REST API that can be used with app admin credentials.
 * Never embed the admin credentials in your applications that are to be distributed to public.
 * 
 * @see http://documentation.kii.com/en/starts/application/accesskeys/
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface AdminAPI {
}
