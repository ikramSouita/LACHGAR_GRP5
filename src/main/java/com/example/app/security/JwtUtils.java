package com.example.app.security;

import com.example.app.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {


    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private long jwtExpirationMs;

    // Générer le token JWT
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        try {
            System.out.println("Clé secrète utilisée pour signer : " + jwtSecret);

            return Jwts.builder()
                    .setSubject(userPrincipal.getEmail())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                    .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes(StandardCharsets.UTF_8))
                    .compact();
        } catch (Exception e) {
            System.err.println("Erreur lors de la génération du token JWT : " + e.getMessage());
            throw e;
        }
    }

    // Récupérer l'email à partir du token JWT
    public String getEmailFromJwtToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            System.err.println("Erreur lors de l'extraction de l'email du token JWT : " + e.getMessage());
            throw e;
        }
    }

    // Valider le token JWT
    public boolean validateJwtToken(String authToken) {
        try {
            System.out.println("Clé secrète utilisée pour valider : " + jwtSecret);

            Jwts.parser()
                    .setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(authToken);

            return true;
        } catch (ExpiredJwtException e) {
            System.err.println("Le token JWT a expiré : " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("Le token JWT est non supporté : " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.println("Le token JWT est mal formé : " + e.getMessage());
        } catch (SignatureException e) {
            System.err.println("La signature du token JWT est invalide : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Le token JWT est vide ou invalide : " + e.getMessage());
        }

        return false;
    }
}
