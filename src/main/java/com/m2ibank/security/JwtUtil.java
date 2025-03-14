package com.m2ibank.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;

import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.Date;

public class JwtUtil {
    private static final String PRIVATE_KEY_PATH = "src/main/resources/keys/private_key.pem";
    private static final String PUBLIC_KEY_PATH = "src/main/resources/keys/public_key.pem";

    private static RSAPrivateKey privateKey;
        private RSAPublicKey publicKey;
    
        public JwtUtil() throws Exception {
            privateKey = loadPrivateKey();
            publicKey = loadPublicKey();
        }
    
        private RSAPrivateKey loadPrivateKey() throws Exception {
            String key = new String(Files.readAllBytes(new File(PRIVATE_KEY_PATH).toPath()));
            key = key.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "").replaceAll("\n", "");
            byte[] encoded = java.util.Base64.getDecoder().decode(key);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(encoded));
        }
    
        private RSAPublicKey loadPublicKey() throws Exception {
            String key = new String(Files.readAllBytes(new File(PUBLIC_KEY_PATH).toPath()));
            key = key.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").replaceAll("\n", "");
            byte[] encoded = java.util.Base64.getDecoder().decode(key);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(new java.security.spec.X509EncodedKeySpec(encoded));
        }
    
        // Générer un JWT
        public static String generateToken(String subject) throws InvalidKeyException {
            return Jwts.builder()
                    .setSubject(subject)
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expiration d'une heure
                    .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    // Valider un JWT
    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Extraire le nom d'utilisateur du token
    public static String getUsernameFromToken(String token) {
        
            return token;
    }

}