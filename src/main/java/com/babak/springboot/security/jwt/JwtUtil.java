package com.babak.springboot.security.jwt;

import com.babak.springboot.security.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Author: Babak Behzadi
 * Email: behzadi.babak@gmail.com
 **/
@Component
public final class JwtUtil {

    private final static String CLAIM_AUTH = "auth";
    private final static String IP = "ip";

    @Value("${base.security.jwt.secret}")
    private String secret;
    @Value("${base.security.jwt.expiry}")
    private Integer expiry;

    private SecretKey secretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(User user, String ip) {
        Date now = new Date();
        return Jwts
                .builder()
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiry))
                .subject(user.getUsername())
                .claim(JwtUtil.CLAIM_AUTH, user.getAuthorities()
                        .stream().map(userAuthority -> userAuthority.getAuthority()).toList())
                .claim(JwtUtil.IP, ip)
                .signWith(secretKey(), Jwts.SIG.HS256)
                .compact();
    }

    private Claims extractAll(String token) {
        return (Claims) Jwts
                .parser()
                .verifyWith(secretKey())
                .build()
                .parse(token.substring(7))
                .getPayload();
    }

    public boolean validate(String token) {
        Claims claims = extractAll(token);
        if (claims.getExpiration().after(new Date())) {
            return true;
        }
        return false;
    }
}
