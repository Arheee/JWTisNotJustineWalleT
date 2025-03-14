package com.m2ibank.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.m2ibank.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public static ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = null;
        try {
            token = JwtUtil.generateToken("USER");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseEntity.ok(Map.of("token", token));
    }
}
