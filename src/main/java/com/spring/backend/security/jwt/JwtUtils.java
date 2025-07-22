package com.spring.backend.security.jwt;

import com.spring.backend.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${my.app.jwtSecret}")
    private String jwtSecret;

    @Value("${my.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${my.app.jwtAutoRefreshMs}")
    private int jwtAutoRefreshMs;

    public String generateJwtToken(Authentication authentication) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        System.out.println(authorities);


        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
//                .setSubject((userPrincipal.getUsername()))
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public Claims parseJwtToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token);
            return claimsJws.getBody();
        } catch (JwtException e) {
            logger.error("Failed to parse JWT token: {}", e.getMessage());
            return null;
        }
    }

    public String earlyRefreshToken(String token) {
        String refreshToken = "";
        Claims claims = parseJwtToken(token);
        if(claims == null) return refreshToken;
        Date expireDate = claims.getExpiration();
        Date earlyExpireDate = new Date(expireDate.getTime() - jwtAutoRefreshMs);
        Date nowDate = new Date();
        // jwtAutoRefreshMs 보다 만료 시간이 적게 남은 경우 토큰을 갱신한다.
        if(earlyExpireDate.before(nowDate)){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            refreshToken = generateJwtToken(authentication);
        }
        return refreshToken;
    }
}
