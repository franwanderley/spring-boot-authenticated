package com.example.loginauthapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loginauthapi.domain.User;
import com.example.loginauthapi.repositories.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
   @Autowired
   private UserRepository userRepository;
   
   public ResponseEntity<List<User>> findAll() {
      return ResponseEntity.ok(userRepository.findAll());
   }
}
