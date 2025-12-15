package com.example.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    // Lê a chave do application.properties ou do Render
    @Value("${jwt.secret}")
    private String jwtSecret;

    private final long EXPIRACAO = 1000L * 60 * 60 * 8; // 8 horas

    // Método que cria a chave a partir da string que vem do properties
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    // GERA O TOKEN
    public String gerarToken(String cpf) {
        return Jwts.builder()
                .setSubject(cpf)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRACAO))
                .signWith(getSigningKey())  // ← agora usa a chave do properties
                .compact();
    }

    // VALIDA E PEGA O CPF
    public String extrairCpf(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    // SÓ VALIDA
    public boolean isTokenValido(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}