package com.nhlstenden.student.vigmo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

public class JWTProvider {
    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;
    private final UserDetailsService userDetailsService;
    private final String secretKey;
    private final long validityInMilliseconds = 600000; // 10 minutes

    public JWTProvider(UserDetailsService userDetailsService, String secretKey) {
        this.userDetailsService = userDetailsService;
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, String role, String profilePicture) {

        HashMap<String, Object> claimMap = new HashMap<>();
        claimMap.put("pfp_location", profilePicture);
        claimMap.put("role", role);

        Date now = new Date();
        Date expiration = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .addClaims(claimMap)
                .signWith(ALGORITHM, secretKey)
                .compact();
    }

    public String createScreenToken(String screenName, String role) {

        HashMap<String, Object> claimMap = new HashMap<>();
        claimMap.put("role", role);

        Date now = new Date();
        Date expiration = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setSubject(screenName)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .addClaims(claimMap)
                .signWith(ALGORITHM, secretKey)
                .compact();
    }



    public Authentication getAuthentication(String tokenString) {
        Claims claims = getClaims(tokenString);
        String user = claims.getSubject();

        //todo: split on role screen as the auth

        UserDetails userDetails = userDetailsService.loadUserByUsername(user);
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }

    public String getRefreshToken(String tokenString) {
        Claims claims = getClaims(tokenString);
        String user = claims.getSubject();

        //todo: maybe get these refreshed from the database.
        String role = claims.get("role", String.class);
        String pfp_location = claims.get("pfp_location", String.class);

        Date expiration = claims.getExpiration();
        if (new Date(new Date().getTime() + validityInMilliseconds / 10).after(expiration)) {
            return createToken(user, role, pfp_location);
        }
        return null;
    }

    public String getToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String tokenString) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(tokenString);
        return SignatureAlgorithm.forName(claims.getHeader().getAlgorithm()) == ALGORITHM;
    }

    private Claims getClaims(String tokenString) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(tokenString);
        return claims.getBody();
    }
}
