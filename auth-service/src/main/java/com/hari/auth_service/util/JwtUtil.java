package com.hari.auth_service.util;

import com.hari.auth_service.entity.USER_TYPE;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {


    /**
     * private final Key secretKey;
     * <p>
     * public JwtUtil(@Value("${jwt.secret}") String secret) {
     * byte[] keyBytes = Base64.getDecoder()
     * .decode(secret.getBytes(StandardCharsets.UTF_8));
     * this.secretKey = Keys.hmacShaKeyFor(keyBytes);
     * }
     **/


    public String generateToken(String username, USER_TYPE userType) {
        return Jwts.builder()
                .subject(username)
                .claim("user_type", userType)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(getKey())
                .compact();
    }


// generate secret key - dynamically

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String secretKey = "";

    public JwtUtil() {
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGenerator.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public void validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token);
        } catch (SignatureException e) {
            throw new JwtException("Invalid JWT signature");
        } catch (JwtException e) {
            throw new JwtException("Invalid JWT");
        }
    }
}
