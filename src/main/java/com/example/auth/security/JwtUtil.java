package com.example.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    // Chave forte (mínimo 256 bits = 32 caracteres pra HS256)
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            "minha-chave-secreta-super-foda-e-longa-12345678901234567890".getBytes(StandardCharsets.UTF_8)
    );

    private final long EXPIRACAO = 1000L * 60 * 60 * 8; // 8 horas

    // GERA O TOKEN
    public String gerarToken(String cpf) {
        return Jwts.builder()
                .setSubject(cpf)  // ← ERA .subject() ANTES, AGORA .setSubject()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRACAO))
                .signWith(SECRET_KEY)
                .compact();
    }

    // VALIDA E PEGA O CPF
    public String extrairCpf(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    // SÓ VALIDA
    public boolean isTokenValido(String token) {
        try {
            Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}