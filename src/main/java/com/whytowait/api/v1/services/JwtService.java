package com.whytowait.api.v1.services;

import com.whytowait.api.common.exceptions.AccessTokenException;
import com.whytowait.api.common.exceptions.BadTokenException;
import com.whytowait.api.common.exceptions.TokenExpiredException;
import com.whytowait.domain.dto.user.UserDetailsDTO;
import com.whytowait.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jdk.jfr.DataAmount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;


@Service
public class
JwtService {

    @Autowired
    UserRepository userRepository;

    private static final String SECRET_KEY = "YourBase64EncodedSecretKeyHere1234567890123456"; // Replace with a valid base64-encoded key

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();

        Date now = new Date();
        Date expiration = new Date(System.currentTimeMillis() + 15 * 60 * 1000);

        return Jwts.
                builder().
                claims().
                add(claims).
                subject(username).
                issuedAt(now).
                expiration(expiration).
                and().
                signWith(generateKey()).
                compact();
    }

    private SecretKey generateKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims validateTokenAndGetClaims(String token) throws TokenExpiredException, BadTokenException, AccessTokenException {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(generateKey())
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
}
