package com.whytowait.core.annotations.aspects;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.api.common.exceptions.UnauthorizedException;
import com.whytowait.config.BufferedRequestWrapper;
import com.whytowait.core.annotations.CheckMerchantManagerRole;
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
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class CheckMerchantManagerRoleAspect {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before("@annotation(checkMerchantManagerRole)")
    public void handleRequiresAuthorities(CheckMerchantManagerRole checkMerchantManagerRole) throws UnauthorizedException, BadRequestException {

        MerchantRole[] requiredAuthorities = checkMerchantManagerRole.requiredAuthorities();
        RequestSource source = checkMerchantManagerRole.source();

        String extractedFieldValue = extractFieldValue(source);

        if (extractedFieldValue == null || extractedFieldValue.isEmpty()) {
            throw new RuntimeException(new BadRequestException("MerchantId not found in request"));
        }

        if (!hasRequiredAuthorities(requiredAuthorities, extractedFieldValue)) {
            throw new RuntimeException(new UnauthorizedException("Required role not found"));
        }
    }

    private String extractFieldValue(RequestSource source) throws UnauthorizedException, BadRequestException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        try {
            BufferedRequestWrapper wrappedRequest = new BufferedRequestWrapper(request);
            if (RequestSource.PARAMS == source) {

                return wrappedRequest.getParameter(RequestSource.PARAMS.getSource());

            } else if (RequestSource.HEADER == source) {

                return wrappedRequest.getHeader(RequestSource.HEADER.getSource());

            } else if (RequestSource.BODY == source) {

                String requestBody = wrappedRequest.getBody();
                JsonNode rootNode = objectMapper.readTree(requestBody);

                return rootNode.path(RequestSource.BODY.getSource()).asText();
            }
        } catch (Exception ex) {
            throw new RuntimeException(new BadRequestException("MerchantId not found in request"));
        }
        throw new BadRequestException("MerchantId not found in request");
    }

    ;

    public boolean hasRequiredAuthorities(MerchantRole[] requiredAuthorities, String merchantId) {
        // Define priorities for roles
        Map<MerchantRole, Integer> rolePriorityMap = Map.of(
                MerchantRole.MERCHANT_OWNER, 1,
                MerchantRole.MERCHANT_ADMIN, 2,
                MerchantRole.MERCHANT_OPERATOR, 3
        );

        // Convert requiredAuthorities to a sorted list based on priority
        List<MerchantRole> sortedRequiredAuths = Arrays.stream(requiredAuthorities)
                .sorted(Comparator.comparingInt(rolePriorityMap::get))
                .toList();

        // Get the current user's authorities
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        // Check roles priority-wise
        for (MerchantRole role : sortedRequiredAuths) {
            String userRole = "MERCHANT_" + merchantId + "_" + role.name();
            if (authorities.contains(userRole)) {
                return true;
            }
        }
        return false;
    }

}

