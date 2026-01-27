package com.pharmaflow.demo.Controllers;

import com.pharmaflow.demo.Dto.AuthResponse;
import com.pharmaflow.demo.Dto.UserLogin;
import com.pharmaflow.demo.Dto.UserRegister;
import com.pharmaflow.demo.Services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    public final AuthService authService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthResponse> register(
            @Valid
            @RequestBody UserRegister userRegister) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.authService.register(userRegister));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid
            @RequestBody UserLogin userLogin) {
        return ResponseEntity.ok()
                .body(this.authService.login(userLogin));
    }

    @GetMapping("/call-back")
    public ResponseEntity<Map<String ,String>> login(
            @RequestParam String token) {
        return ResponseEntity.ok()
                .body(Map.of("token", token));
    }
}
