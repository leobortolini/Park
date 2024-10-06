package br.com.park.parkCheck.service.util;

import br.com.park.parkCheck.model.TokenParams;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private static final byte[] SECRET_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2UiLCJpYXQiOjE1MTU3MTU2MjV9.4n5V9q_B3Y0-859h9b6-Q07986_03511729450401524738".getBytes();

    public byte[] generatedRandomSecret(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        return bytes;
    }

    public String generateToken(String username){
        return Jwts.builder().issuer("application")
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (30 * 60 * 1000))) //30 minutos (parâmetro da esquerda)
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY))
                .compact();
    }

    public TokenParams getTokenParams(String tokenReceived){
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY)
                .build()
                .parseSignedClaims(tokenReceived) //Faz a validação de expiração e outras automaticamente.
                .getBody();

        TokenParams tokenParams = new TokenParams(
                claims.getSubject() //username
        );

        return tokenParams;
    }
}
