package com.project.fitness.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private String JwtSecret="bXlzZWNyZXRrZXlteXNlY3JldGtleW15c2VjcmV0a2V5MTIz";

    private int jwtExpirations=172800000;

    public String getJwtFromHeader(HttpServletRequest request){
        String Jwt= request.getHeader("Authorization");

        if(Jwt!=null&&Jwt.startsWith("Bearer ")){
            return Jwt.substring(7);
        }

        return  null;
    }

    public String generateJwtToken(String userId,String role){


        return Jwts.builder()
                .subject(userId)
                .claim("roles", List.of(role))
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime()+jwtExpirations))
                .signWith(key())
                .compact();
    }

    public boolean ValidateJwtToken(String jwtToken){
        try {
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(jwtToken);

        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JwtSecret));
    }

    public  String getUserIdFromJwtToken(String JwtToken){
        return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(JwtToken).getPayload().getSubject();
    }


    public Claims getAllClaims(String jwt) {
        return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(jwt).getPayload();
    }
}
