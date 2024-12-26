package com.whytowait.core.annotations;

import com.whytowait.core.annotations.enums.RequestSource;
import com.whytowait.domain.models.enums.MerchantRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckMerchantManagerRole {

    MerchantRole requiredAuthority();

    String fieldName();

    RequestSource source();

}



