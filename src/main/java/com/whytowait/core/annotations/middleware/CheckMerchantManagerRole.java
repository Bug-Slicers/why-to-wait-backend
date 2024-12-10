package com.whytowait.core.annotations.middleware;

import com.whytowait.domain.models.enums.MerchantRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckMerchantManagerRole {
    SourceType source();

    String field();

    MerchantRole role();
}
