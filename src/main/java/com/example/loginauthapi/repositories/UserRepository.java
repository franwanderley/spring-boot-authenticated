package com.example.loginauthapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loginauthapi.domain.User;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {

   Optional<User> findByEmail(String email);

}
