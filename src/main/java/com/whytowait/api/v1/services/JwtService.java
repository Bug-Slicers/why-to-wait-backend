package com.whytowait.api.v1.services;

import com.whytowait.api.common.exceptions.AccessTokenException;
import com.whytowait.api.common.exceptions.ApiException;
import com.whytowait.api.common.exceptions.BadTokenException;
import com.whytowait.api.common.exceptions.TokenExpiredException;
import com.whytowait.domain.dto.user.UserDetailsDTO;
import com.whytowait.domain.dto.user.UserLoginReqDTO;
import com.whytowait.domain.dto.user.UserLoginResDTO;
import com.whytowait.domain.models.MerchantManager;
import com.whytowait.domain.models.User;
import com.whytowait.repository.MerchantManagerRepository;
import com.whytowait.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import jdk.jfr.DataAmount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class
JwtService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MerchantManagerRepository merchantManagerRepository;

    @Value("${jwt.secret_key:YourBase64EncodedSecretKeyHere1234567890123456}")
    private String SECRET_KEY;

    @Value("${jwt.refresh_token_secret_key:YourBase64EncodedSecretKeyHere1234567890123456789}")
    private String REFRESH_TOKEN_SECRET_KEY;

    private String createToken(String username, String secretKey, Date now, Date expiration, Map<String, Object> claims) {
        return Jwts.
                builder().
                claims().
                add(claims).
                subject(username).
                issuedAt(now).
                expiration(expiration).
                and().
                signWith(generateKey(secretKey)).
                compact();
    }

    public String generateToken(String username, String role, List<MerchantManager> merchantRoles) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("roles", List.of(role));
        claims.put("merchant_roles", merchantRoles.stream()
                .map(merchantRole -> Map.of("merchant_id", merchantRole.getMerchantId(), "role", merchantRole.getRole()))
                .collect(Collectors.toList()));

        Date now = new Date();
        Date expiration = new Date(System.currentTimeMillis() + 4*15 * 60 * 1000);

        return createToken(
                username,
                SECRET_KEY,
                now,
                expiration,
                claims
        );
    }

    public String generateRefreshToken(String username) {
        Date now = new Date();
        Date expiration = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);

        return createToken(
                username,
                REFRESH_TOKEN_SECRET_KEY,
                now,
                expiration,
                null
        );
    }

    private SecretKey generateKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims validateToken(String token, String secretKey) throws TokenExpiredException, BadTokenException, AccessTokenException {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(generateKey(secretKey))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String username = claims.getSubject();

            Date lastLogout = userRepository.findLastLogoutByMobile(username);

            if (lastLogout != null && claims.getIssuedAt().before(lastLogout)) {
                throw new BadTokenException();
            }

            return claims;
        } catch (ExpiredJwtException ex) {
            throw new TokenExpiredException();
        } catch (JwtException ex) {
            throw new AccessTokenException();
        }
    }

    public Claims validateTokenAndGetClaims(String token) throws TokenExpiredException, BadTokenException, AccessTokenException {
        try {
            return validateToken(token, SECRET_KEY);
        } catch (ExpiredJwtException ex) {
            throw new TokenExpiredException();
        } catch (JwtException ex) {
            throw new AccessTokenException();
        }
    }

    public Claims validateRefreshTokenAndGetClaims(String token) throws TokenExpiredException, BadTokenException, AccessTokenException {
        try {
            return validateToken(token, REFRESH_TOKEN_SECRET_KEY);
        } catch (ApiException ex) {
            throw new BadTokenException();
        }
    }

    public UserLoginResDTO validateRefreshTokenAndGenerateAccessTokenAndRefreshToken(String token) throws TokenExpiredException, AccessTokenException, BadTokenException {
        Claims claims = validateRefreshTokenAndGetClaims(token);
        User user = userRepository.findByMobile(claims.getSubject());
        List<MerchantManager> merchantRoles = merchantManagerRepository.findByUserId(user.getId());

        String generatedToken = generateToken(claims.getSubject(), user.getRole().name(), merchantRoles);
        String generateRefreshToken = generateRefreshToken(claims.getSubject());
        return UserLoginResDTO.builder()
                .token(generatedToken)
                .refreshToken(generateRefreshToken)
                .username(claims.getSubject())
                .build();
    }
}
