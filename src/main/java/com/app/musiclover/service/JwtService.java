package com.app.musiclover.service;

import com.app.musiclover.data.model.Role;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private static final String BEARER = "Bearer ";
    private static final int PARTIES = 3;
    private static final String AUTHORITIES = "authorities";
    private static final String USER_CLAIM = "user";
    private static final String ROLES_CLAIM = "roles";

    private final String secret;
    private final String issuer;
    private final int expire;

    public JwtService(@Value("${music.lover.jwt.secret}") String secret,
                      @Value("${music.lover.jwt.issuer}") String issuer,
                      @Value("${music.lover.jwt.expire}") int expire) {
        this.secret = secret;
        this.issuer = issuer;
        this.expire = expire;
    }

    public String extractToken(String bearer) {
        if (bearer != null && bearer.startsWith(BEARER) && PARTIES == bearer.split("\\.").length) {
            return bearer.substring(BEARER.length());
        } else {
            return "";
        }
    }

    public String createToken(Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return JWT.create()
                .withSubject(username)
                .withIssuer(issuer)
                .withIssuedAt(new Date())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expire))
                .withClaim(AUTHORITIES, authorities)
                .withJWTId(UUID.randomUUID().toString())
                .sign(Algorithm.HMAC256(secret));
    }

    public DecodedJWT validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();
            return verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Invalid token, not Authorized.");
        }
    }

    public String extractUsername(DecodedJWT decodedJWT){
        return decodedJWT.getSubject().toString();
    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);
    }


}
