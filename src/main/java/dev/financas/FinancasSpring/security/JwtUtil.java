package dev.financas.FinancasSpring.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private final Key secretKey;
    private final long expirationMs;

    public JwtUtil(
            @Value("${JWT_SECRET}") String secret,
            @Value("${jwt.expiration}") long expirationMs) {
        if (secret.length() < 32) {
            throw new IllegalArgumentException("A chave JWT deve ter pelo menos 32 caracteres");
        }
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMs = expirationMs;
    }

    public String generateToken(Long id, String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("id", id)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Long extractUserId(String token) {
        return extractAllClaims(token).get("id", Long.class);
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.warn("Token expirado: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.warn("Token não suportado: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.warn("Token malformado: {}", e.getMessage());
        } catch (SecurityException e) {
            logger.warn("Assinatura inválida: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.warn("Token vazio: {}", e.getMessage());
        }
        return false;
    }
}
