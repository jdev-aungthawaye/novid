package jdev.novid.component.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_AUDIENCE = "audience";
    private static final String CLAIM_KEY_CREATED = "created";

    private static String generateToken(Map<String, Object> claims, String secret, Date expiredAt) {
        return Jwts.builder().setClaims(claims).setExpiration(expiredAt).signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public static String generateToken(String identity, String secret, Date expiredAt, String audience) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, identity);
        claims.put(CLAIM_KEY_AUDIENCE, audience);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims, secret, expiredAt);
    }

    public static String getAudienceFromToken(String token, String secret) {
        String audience;
        try {
            final Claims claims = getClaimsFromToken(token, secret);
            audience = (String) claims.get(CLAIM_KEY_AUDIENCE);
            return audience;
        } catch (Exception e) {
            audience = null;
        }
        return null;
    }

    private static Claims getClaimsFromToken(String token, String secret) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public static Date getCreatedDateFromToken(String token, String secret) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token, secret);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public static Date getExpirationDateFromToken(String token, String secret) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token, secret);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public static String getUsernameFromToken(String token, String secret) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token, secret);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    private JwtTokenUtil() {

    }
}
