package com.whytowait.core.annotations.middleware;

import com.whytowait.api.common.exceptions.ApiException;
import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.api.common.exceptions.ForbiddenException;
import com.whytowait.domain.models.MerchantManager;
import com.whytowait.domain.models.enums.MerchantRole;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.SimpleTimeZone;

@Aspect
@Component
public class CheckMerchantRoleAspect {
    @Autowired
    private HttpServletRequest request;

    @Around("@annotation(CheckMerchantManagerRole)")
    public Object checkMerchantManagerRole(ProceedingJoinPoint joinPoint) throws RuntimeException {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            CheckMerchantManagerRole checkMerchantManagerRole = method.getAnnotation(CheckMerchantManagerRole.class);

            SourceType source = checkMerchantManagerRole.source();
            String field = checkMerchantManagerRole.field();
            MerchantRole role = checkMerchantManagerRole.role();
            String merchantId = getFieldValue(joinPoint, source, field);

            if (!checkRole(merchantId, role)) {
                throw new ForbiddenException("Insufficient Merchant Role");
            }

            return joinPoint.proceed();

        } catch (Throwable e) {
            if (e instanceof NoSuchFieldException) {
                throw new RuntimeException(new BadRequestException("No Field Found"));
            }
            throw new RuntimeException(e);
        }
    }

    public boolean checkRole(String merchantId, MerchantRole requiredRole) {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("MERCHANT_" + merchantId + "_" + requiredRole.name()));
    }

    public String getFieldValue(ProceedingJoinPoint joinPoint, SourceType source, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        if (source.equals(SourceType.PARAMS)) {
            return request.getParameter(fieldName);
        }

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg != null) {
                try {
                    Field field1 = arg.getClass().getDeclaredField(fieldName);
                    field1.setAccessible(true);
                    Object value = field1.get(arg);
                    if (value != null) {
                        return value.toString();
                    }
                } catch (Exception ex) {
//                    do nothing will throw if nothing is found for all args
                }
            }
        }
        throw new NoSuchFieldException();
    }
}
