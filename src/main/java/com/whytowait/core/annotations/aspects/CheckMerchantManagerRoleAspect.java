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

import java.util.List;
import java.util.Map;

@Aspect
@Component
public class CheckMerchantManagerRoleAspect {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before("@annotation(checkMerchantManagerRole)")
    public void handleRequiresAuthorities(CheckMerchantManagerRole checkMerchantManagerRole) throws UnauthorizedException, BadRequestException {

        MerchantRole requiredRole = checkMerchantManagerRole.requiredAuthority();
        RequestSource source = checkMerchantManagerRole.source();
        String sourceField = checkMerchantManagerRole.fieldName();

        String extractedFieldValue = extractFieldValue(source, sourceField);

        if (extractedFieldValue == null || extractedFieldValue.isEmpty()) {
            throw new RuntimeException(new BadRequestException("MerchantId not found in request"));
        }

        if (!hasRequiredAuthorities(requiredRole, extractedFieldValue)) {
            throw new RuntimeException(new UnauthorizedException("Unauthorized to perform action"));
        }
    }

    private String extractFieldValue(RequestSource source, String sourceField) throws UnauthorizedException, BadRequestException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        try {
            BufferedRequestWrapper wrappedRequest = new BufferedRequestWrapper(request);
            if (RequestSource.PARAM == source) {

                return wrappedRequest.getParameter(sourceField);

            } else if (RequestSource.HEADER == source) {

                return wrappedRequest.getHeader(sourceField);

            } else if (RequestSource.BODY == source) {

                String requestBody = wrappedRequest.getBody();
                JsonNode rootNode = objectMapper.readTree(requestBody);

                return rootNode.path(sourceField).asText();
            }
        } catch (Exception ex) {
            throw new RuntimeException(new BadRequestException("MerchantId not found in request"));
        }
        throw new BadRequestException("MerchantId not found in request");
    }

    ;

    public boolean hasRequiredAuthorities(MerchantRole requiredAuthority, String merchantId) {
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

    ;
}

