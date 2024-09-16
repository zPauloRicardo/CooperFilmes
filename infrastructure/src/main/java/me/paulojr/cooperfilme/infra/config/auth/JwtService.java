package me.paulojr.cooperfilme.infra.config.auth;


import me.paulojr.cooperfilme.infra.contexts.user.adapters.UserAuthAdapter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "PT9iPnOAGbJhp3eZSZCYMl_RYskTPu4KmC18inJ17lAkYY0PKaGdVIoIbP3xCBDzJ7u1y" +
            "-GBqP23pdj-JLXKP3GJyjo" +
            "-KGdxqqtGca1wWT1Pe9F62Le1jGsgyARWbfrRISFt62UxUgC55LsRzY9DYwdXK1NtZPyEmBp62POm_r2UsuDdYIQDQu7qYHOQxe89_4QRrpMLk8QIl9Y_z8qNJJHAS1273nag3ha-DwreqY43TY-rq6h1-3jSAATM4sGy4BhU1Lf370a0DTgJXgGqo7_HbB0034DrZkEzrvFeQL3dNuQB633FGoi3w1SEWFP0GvY2TVKg5kSyf_j2jG6v";


    public String generateToken(Map<String, Object> extraClaims, UserAuthAdapter userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getId().getValue().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(this.getSingInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(UserAuthAdapter userDetails) {
        return this.generateToken(new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(String token, UserAuthAdapter userDetails) {
        final String uuid = this.extractUuid(token);
        return uuid.equals(userDetails.getId().getValue().toString()) && !isTokenExpired(token);
    }


    public String extractUuid(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(this.extractAllClaims(token));
    }

    private boolean isTokenExpired(String token) {
        return this.extractExpiration(token) != null
                && this.extractExpiration(token).before(Date.from(Instant.now()));
    }


    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getSingInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    private Key getSingInKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
