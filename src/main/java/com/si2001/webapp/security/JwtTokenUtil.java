package com.si2001.webapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    @Value("${sicurezza.secret}")
    private String secret;

    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; // 5 ore

    // Genera un token JWT
    public String generateToken(String username, String ruolo) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("ruolo", ruolo);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }

    // Estrai il nome utente dal token JWT
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Estrai il ruolo dal token JWT
    public String getRoleFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("ruolo", String.class));
    }

    // Valida il token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Estrai tutti i claims dal token
    protected Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())) // Utilizza la stessa chiave segreta per verificare
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Estrai un claim specifico dal token
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // Controlla se il token è scaduto
    private boolean isTokenExpired(String token) {
        final Date expiration = getClaimFromToken(token, Claims::getExpiration);
        return expiration.before(new Date());
    }


}
