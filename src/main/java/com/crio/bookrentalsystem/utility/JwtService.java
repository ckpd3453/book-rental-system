package com.crio.bookrentalsystem.utility;

import com.crio.bookrentalsystem.model.User;
import com.crio.bookrentalsystem.repository.UserRepo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtService {

    @Autowired
    UserRepo userRepo;

    private static final String SECRET_KEY = "YourSuperSecretKeyForJWTGeneration";
//    private static final long EXPIRATION_TIME = 86400000; // 1 day

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String getToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getEmailFromToken(String token) {
        return extractClaims(token).getSubject();
    }

    public String getRoleFromToken(String token) {
        return extractClaims(token).get("role", String.class);
    }

    public boolean validateToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getUserIdFromToken(String token) {
        // Remove "Bearer " prefix
        token = token.substring(7);

        // Extract email from JWT token
        String email = getEmailFromToken(token);

        // Fetch user from database using email
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new IllegalStateException("User not found");
        }

        return user.getUserId(); // Return user ID
    }
}
