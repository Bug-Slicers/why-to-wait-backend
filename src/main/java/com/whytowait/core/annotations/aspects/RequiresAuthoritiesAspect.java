package com.whytowait.core.annotations.aspects;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.api.common.exceptions.UnauthorizedException;
import com.whytowait.config.BufferedRequestWrapper;
import com.whytowait.core.annotations.RequiresAuthorities;
import com.whytowait.core.annotations.enums.RequestSource;
import com.whytowait.domain.models.enums.MerchantRole;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class RequiresAuthoritiesAspect {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before("@annotation(requiresAuthorities)")
    public void handleRequiresAuthorities(RequiresAuthorities requiresAuthorities) throws UnauthorizedException, BadRequestException {

        String[] requiredAuthorities = requiresAuthorities.requiredAuthorities();
        String source = requiresAuthorities.source();

        String activeMerchantClaim = GetActiveMerchantClaim(source);

        if (activeMerchantClaim == null || activeMerchantClaim.isEmpty()) {
            throw new RuntimeException(new BadRequestException("MerchantId not found in request"));
        }

        if (!hasRequiredAuthorities(requiredAuthorities, activeMerchantClaim)) {
            throw new RuntimeException(new UnauthorizedException("Required role not found"));
        }
    }

    private String GetActiveMerchantClaim(String source) throws UnauthorizedException, BadRequestException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        try {
            BufferedRequestWrapper wrappedRequest = new BufferedRequestWrapper(request);
            if (RequestSource.PARAMS.name().equalsIgnoreCase(source)) {

                return wrappedRequest.getParameter("merchantId");

            } else if (RequestSource.HEADER.name().equalsIgnoreCase(source)) {

                return wrappedRequest.getParameter("merchant-id");

            } else if (RequestSource.BODY.name().equalsIgnoreCase(source)) {

                String requestBody = wrappedRequest.getBody();
                JsonNode rootNode = objectMapper.readTree(requestBody);

                return rootNode.path("merchantId").asText();
            }
        } catch (Exception ex) {
            throw new RuntimeException(new BadRequestException("MerchantId not found in request"));
        }
        throw new BadRequestException("MerchantId not found in request");
    }

    ;

    public boolean hasRequiredAuthorities(String[] requiredAuthorities, String merchantId) {

        List<String> requiredAuths = Arrays.stream(requiredAuthorities).toList();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        if (requiredAuths.contains(MerchantRole.MERCHANT_OWNER.name())) {
            String userRole = "MERCHANT_" + merchantId + "_" + MerchantRole.MERCHANT_OWNER.name();
            if (authorities.contains(userRole)) {
                return true;
            }
        }
        ;

        if (requiredAuths.contains(MerchantRole.MERCHANT_ADMIN.name())) {
            String userRole = "MERCHANT_" + merchantId + "_" + MerchantRole.MERCHANT_ADMIN.name();
            if (authorities.contains(userRole)) {
                return true;
            }
        }
        ;

        String userRole = "MERCHANT_" + merchantId + "_" + MerchantRole.MERCHANT_OPERATOR.name();
        if (requiredAuths.contains(MerchantRole.MERCHANT_OPERATOR.name())) {
            if (authorities.contains(userRole)) {
                return true;
            }
        }
        ;

        return false;
    }

    ;
}
