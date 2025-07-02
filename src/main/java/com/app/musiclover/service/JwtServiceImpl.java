package com.app.musiclover.service;

import com.app.musiclover.data.model.Role;
import com.app.musiclover.domain.service.JwtService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtServiceImpl implements JwtService {

    private static final String BEARER = "Bearer ";
    private static final int PARTIES = 3;
    private static final String USERNAME_CLAIM = "username";
    private static final String ROLE_CLAIM = "role";

    private final String secret;
    private final String issuer;
    private final int expire;

    public JwtServiceImpl(@Value("${music.lover.jwt.secret}") String secret,
                          @Value("${music.lover.jwt.issuer}") String issuer,
                          @Value("${music.lover.jwt.expire}") int expire) {
        this.secret = secret;
        this.issuer = issuer;
        this.expire = expire;
    }

    @Override
    public String extractToken(String bearer) {
        if (bearer != null && bearer.startsWith(BEARER) && PARTIES == bearer.split("\\.").length) {
            return bearer.substring(BEARER.length());
        } else {
            return Strings.EMPTY;
        }
    }

    @Override
    public String createToken(UserDetails userDetails) {
        return JWT.create()
                .withIssuer(issuer)
                .withIssuedAt(new Date())
                .withNotBefore(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + this.expire * 100L))
                .withClaim(USERNAME_CLAIM, userDetails.getUsername())
                .withClaim(ROLE_CLAIM, userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .findFirst()
                        .orElse(Strings.EMPTY))
                .sign(Algorithm.HMAC256(secret));
    }

    @Override
    public String username(String authorization) {
        return verify(authorization)
                .map(jwt -> jwt.getClaim(USERNAME_CLAIM).asString())
                .orElse(Strings.EMPTY);
    }

    @Override
    public String role(String authorization) {
        return this.verify(authorization)
                .map(jwt -> jwt.getClaim(ROLE_CLAIM).asString())
                .map(Role::of)
                .map(Role::name)
                .orElse(Strings.EMPTY);
    }

    private Optional<DecodedJWT> verify(String token) {
        try {
            return Optional.of(JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer(issuer).build()
                    .verify(token));
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Invalid token, not Authorized.");
        }
    }
}
