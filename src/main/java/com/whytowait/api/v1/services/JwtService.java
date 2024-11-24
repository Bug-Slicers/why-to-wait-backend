package com.whytowait.api.v1.services;

import com.whytowait.domain.dto.user.FetchUserDTO;
import com.whytowait.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;


@Service
public class JwtService {

    @Autowired
    UserRepository userRepository;

    private static final String SECRET_KEY = "YourBase64EncodedSecretKeyHere1234567890123456"; // Replace with a valid base64-encoded key

    public String generateToken(String username){
        Map<String,Object> claims = new HashMap<>();
        return Jwts.
                builder().
                claims().
                add(claims).
                subject(username).
                issuedAt(new Date(System.currentTimeMillis())).
                expiration(new Date(System.currentTimeMillis()+ 60 * 60 * 1000)).
                and().
                signWith(generateKey()).
                compact();
    }

    private SecretKey generateKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, FetchUserDTO userDetails) {
        final String userName = extractUserName(token);
        UUID userId = userDetails.getId();

        return (userName.equals(userDetails.getMobile()) && !isTokenExpired(token,userId));
    }

    private boolean isTokenExpired(String token, UUID userId) {
        Timestamp lastLogout = userRepository.findLastLogoutById(userId);

        if (lastLogout != null) {
            // Compare last_logout with token issue time
            Date tokenIssuedAt = extractIssuedAt(token);
            if (tokenIssuedAt.before(lastLogout)) {
                return true; // Token was issued before the last logout
            }
        }
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Date extractIssuedAt(String token) {
        return extractClaim(token, Claims::getIssuedAt);
    }
}
