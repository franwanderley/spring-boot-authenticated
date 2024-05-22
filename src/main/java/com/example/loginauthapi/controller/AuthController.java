package com.example.loginauthapi.controller;

import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loginauthapi.domain.User;
import com.example.loginauthapi.dto.LoginRequestDTO;
import com.example.loginauthapi.dto.RegisterRequestDTO;
import com.example.loginauthapi.repositories.UserRepository;
import com.example.loginauthapi.security.TokenService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
   private UserRepository repository;
   private PasswordEncoder passwordEncoder;
   private TokenService tokenService;

   @PostMapping("/login")
   public ResponseEntity<String> login(@RequestBody LoginRequestDTO body) {
      User user = repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("user not found"));
      if(passwordEncoder.matches(user.getPassword(), body.password())) {
         String token = tokenService.generateToken(user);
         return ResponseEntity.ok(token);
      }
      return ResponseEntity.badRequest().build();
   }

   @PostMapping("/register")
   public ResponseEntity<String> register(@RequestBody RegisterRequestDTO body) {
      Optional<User> optional = repository.findByEmail(body.email());
      if(!optional.isPresent()) {
         User user = new User(null, body.name(), body.email(), passwordEncoder.encode(body.password()));
         repository.save(user);
         return ResponseEntity.ok(tokenService.generateToken(user));
      }
      return ResponseEntity.badRequest().build();
   }
}
