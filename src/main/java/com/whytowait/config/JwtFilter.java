package com.whytowait.config;

import com.whytowait.api.common.exceptions.AccessTokenException;
import com.whytowait.api.common.exceptions.ApiException;
import com.whytowait.api.common.responses.ApiResponse;
import com.whytowait.api.v1.services.JwtService;
import com.whytowait.api.v1.services.UserService;
import com.whytowait.domain.dto.user.UserDetailsDTO;
import com.whytowait.domain.models.User;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/auth/login",
            "/auth/signup",
            "/auth/refresh-token"
    );

    @Autowired
    private JwtService jwtService;

    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String requestUri = request.getRequestURI();

            if (isPublicEndpoint(requestUri)) {
                filterChain.doFilter(request, response);
                return;
            }

            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new AccessTokenException("Invalid or missing Authorization header");
            }

            String token = authHeader.substring(7);

            Claims claims = jwtService.validateTokenAndGetClaims(token);
            String username = claims.getSubject();
            User user = userService.loadUserByUsername(username);
            UserDetailsDTO userDetails = UserDetailsDTO.fromUser(user);

            List<String> roles = claims.get("roles", List.class);
            List<Map<String, String>> merchantRoles = claims.get("merchant_roles", List.class);

            List<GrantedAuthority> authorities = new ArrayList<>();
            roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
            merchantRoles.forEach(role -> {
                authorities.add(new SimpleGrantedAuthority("MERCHANT_" + role.get("merchant_id") + "_" + role.get("role")));
            });

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
            authToken.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        } catch (ApiException ex) {
            ApiResponse res = ApiException.handle(ex);
            res.writeToResponse(response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(String requestUri) {
        return PUBLIC_ENDPOINTS.contains(requestUri);
    }
}
