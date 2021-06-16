package com.buster.backend.controllers;

import com.buster.backend.dto.AuthenticationResponse;
import com.buster.backend.dto.LoginRequest;
import com.buster.backend.dto.RegisterRequest;
import com.buster.backend.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody RegisterRequest registerRequest) {
        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "User registration successfully");
        authService.signup(registerRequest);

        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<?> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        Map<String, Object> resp = new HashMap<>();

        resp.put("message", "Account activated successfully");

        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}