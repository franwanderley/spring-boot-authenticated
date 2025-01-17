package com.example.loginauthapi.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.loginauthapi.domain.User;

@Service
public class TokenService {

   @Value("${api.security.token.secret}")
   private String secret;
   public String generateToken(User user) {
      try {
         Algorithm algorithm = Algorithm.HMAC256(secret);
         return JWT.create()
                  .withIssuer("login-auth-api")
                  .withSubject(user.getEmail())
                  .withExpiresAt(generateExpiredDate())
                  .sign(algorithm);
      } catch (JWTCreationException e) {
         throw new RuntimeException("error while authentig");
      }
   }
   private Instant generateExpiredDate() {
      return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-3"));
   }
   public String validateToken(String token){
      try {
         Algorithm algorithm = Algorithm.HMAC256(secret);
         return JWT.require(algorithm)
                  .withIssuer("login-auth-api")
                  .build().verify(token).getSubject();
      } catch (JWTVerificationException e) {
         return null;
      }
   }
}
