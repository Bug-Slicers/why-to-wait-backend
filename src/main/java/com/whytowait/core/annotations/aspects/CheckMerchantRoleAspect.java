package com.whytowait.core.annotations.aspects;

import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.api.common.exceptions.UnauthorizedException;
import com.whytowait.core.annotations.enums.RequestSource;
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
import org.springframework.web.servlet.HandlerMapping;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class CheckMerchantRoleAspect {

    @Autowired
    private HttpServletRequest request;


    @Around("@annotation(CheckMerchantManagerRole)")
    public Object handleRequiresAuthorities(ProceedingJoinPoint joinPoint) throws RuntimeException {

        try {

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            CheckMerchantManagerRole checkMerchantManagerRole = method.getAnnotation(CheckMerchantManagerRole.class);


            MerchantRole requiredRole = checkMerchantManagerRole.requiredAuthority();
            RequestSource source = checkMerchantManagerRole.source();
            String sourceField = checkMerchantManagerRole.fieldName();

            String extractedFieldValue = extractFieldValue(source, sourceField, joinPoint);

            if (extractedFieldValue == null || extractedFieldValue.isEmpty()) {
                throw new NoSuchFieldException("Field not Found");
            }

            if (!hasRequiredAuthorities(requiredRole, extractedFieldValue)) {
                throw new UnauthorizedException("Unauthorized to perform action");
            }

            return joinPoint.proceed();
        } catch (Throwable Ex) {
            if (Ex instanceof NoSuchFieldException) {
                throw new RuntimeException(new BadRequestException("No Field Found"));
            } else if (Ex instanceof UnauthorizedException) {
                throw new RuntimeException(new UnauthorizedException("Unauthorized to perform action"));
            }
            throw new RuntimeException(Ex);
        }
    }

    private String extractFieldValue(RequestSource source, String sourceField, ProceedingJoinPoint joinPoint) throws NoSuchFieldException {

        try {
            if (RequestSource.PARAM == source) {
                Map<String, String> parameters = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                return parameters.get(sourceField);
            }
            if (RequestSource.HEADER == source) {
                return request.getHeader(sourceField);
            }
            if (RequestSource.BODY == source) {

                Object[] args = joinPoint.getArgs();
                for (Object arg : args) {
                    if (arg != null) {
                        try {
                            Field field1 = arg.getClass().getDeclaredField(sourceField);
                            field1.setAccessible(true);
                            Object value = field1.get(arg);
                            if (value != null) {
                                return value.toString();
                            }
                        } catch (Exception ex) {
//                          do nothing will throw if nothing is found for all args
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new NoSuchFieldException("MerchantId not found in request");
        }
        throw new NoSuchFieldException("MerchantId not found in request");
    }


    private boolean hasRequiredAuthorities(MerchantRole requiredAuthority, String merchantId) {
        // Get the current user's authorities

        Map<String, Integer> rolePriorityMap = Map.of(
                "MERCHANT_" + merchantId + "_MERCHANT_OWNER", 1,
                "MERCHANT_" + merchantId + "_MERCHANT_ADMIN", 2,
                "MERCHANT_" + merchantId + "_MERCHANT_OPERATOR", 3
        );

        Integer requiredAuthorityPriority = rolePriorityMap.get("MERCHANT_" + merchantId + "_" + requiredAuthority);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<String> authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        List<String> merchantRoles = authorities.stream().filter(x -> x.contains(merchantId)).toList();

        if (merchantRoles.isEmpty()) {
            return false;
        }
        Integer userRolePriority = rolePriorityMap.get(merchantRoles.getFirst());
        return userRolePriority <= requiredAuthorityPriority;
    }
}

