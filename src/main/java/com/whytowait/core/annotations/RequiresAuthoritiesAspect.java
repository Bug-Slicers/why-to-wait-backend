package com.whytowait.core.annotations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whytowait.api.common.exceptions.BadRequestException;
import com.whytowait.api.common.exceptions.UnauthorizedException;
import com.whytowait.domain.models.enums.MerchantRole;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Aspect
@Component
public class RequiresAuthoritiesAspect {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before("@annotation(requiresAuthorities)")
    public void handleRequiresAuthorities(RequiresAuthorities requiresAuthorities) throws IOException {

        String[] requiredAuthorities = requiresAuthorities.requiredAuthorities();
        String source = requiresAuthorities.source();

        String activeMerchantClaim = GetActiveMerchantClaim(source);

        if (activeMerchantClaim == null || activeMerchantClaim.isEmpty()) {
            throw new RuntimeException(new BadRequestException("MerchantId not found in request header or param"));
        }

        // Perform custom authorization logic
        if (!hasRequiredAuthorities(requiredAuthorities, activeMerchantClaim)) {
            throw new RuntimeException(new UnauthorizedException("No Role not found"));
        }
    }

    private String GetActiveMerchantClaim(String source) throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();


        try {
            if ("Param".equalsIgnoreCase(source)) {
                return request.getParameter("MerchantId");
            }

            if ("Body".equalsIgnoreCase(source)) {

                // Read the request body
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("line" + line);
                    stringBuilder.append(line);
                }

                String requestBody = stringBuilder.toString();
                System.out.println(requestBody);
                // Parse JSON body and extract the specific field
                JsonNode rootNode = objectMapper.readTree(requestBody);

                // Extract the field value
                return rootNode.path("restaurantName").asText();
            }
        } catch (Exception ex) {
            throw new RuntimeException(new BadRequestException("MerchantId not found in request"));
        }
//        throw new RuntimeException(new BadRequestException("MerchantId not found in request"));
        return "";
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
