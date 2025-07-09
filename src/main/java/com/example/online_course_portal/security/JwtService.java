package com.example.online_course_portal.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException; // Keep these imports for catching them
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException; // Keep these imports
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException; // Keep this import (io.jsonwebtoken.security.SignatureException)
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME;

    // ******************************************************************
    // !!! CRUCIAL CHANGE: REMOVE 'throws' CLAUSES FROM THESE METHODS !!!
    // ******************************************************************

    public String extractUsername(String token) { // REMOVED: throws ExpiredJwtException, MalformedJwtException, SignatureException
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) { // REMOVED: throws ExpiredJwtException, MalformedJwtException, SignatureException
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { // REMOVED: throws ExpiredJwtException, MalformedJwtException, SignatureException
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) { // REMOVED: throws ExpiredJwtException, MalformedJwtException, SignatureException
        // This is where the RuntimeExceptions (ExpiredJwtException, MalformedJwtException, SignatureException)
        // are actually thrown by the JJWT library. You do NOT declare them here.
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ******************************************************************
    // The rest of the class remains as it was in the correct previous examples.
    // The try-catch blocks within isTokenExpired and validateToken are for handling
    // these *runtime exceptions* when they occur during execution.
    // ******************************************************************

    private Boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (MalformedJwtException | SignatureException e) {
            return true;
        }
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired during validation for user: " + userDetails.getUsername());
            return false;
        } catch (MalformedJwtException | SignatureException e) {
            System.out.println("Invalid token format or signature during validation for user: " + userDetails.getUsername());
            return false;
        } catch (Exception e) { // Catch any other unexpected RuntimeExceptions
            System.out.println("Unexpected error during token validation: " + e.getMessage());
            return false;
        }
    }

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}