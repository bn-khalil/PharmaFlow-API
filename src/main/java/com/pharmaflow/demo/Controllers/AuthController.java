package com.pharmaflow.demo.Controllers;

import com.pharmaflow.demo.Dto.AuthResponse;
import com.pharmaflow.demo.Dto.UserLogin;
import com.pharmaflow.demo.Dto.UserRegister;
import com.pharmaflow.demo.Services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Sing In Management", description = "endpoints for entre and access system resources")
public class AuthController {

    public final AuthService authService;

    @Operation(
            summary = "Create User",
            description = "Creating new user in system by admin"
    )
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthResponse> register(
            @Valid
            @RequestBody UserRegister userRegister) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.authService.register(userRegister));
    }

    @Operation(summary = "Sing in User")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid
            @RequestBody UserLogin userLogin) {
        return ResponseEntity.ok()
                .body(this.authService.login(userLogin));
    }

    @Operation(
            summary = "Get token",
            description = "Get token generated after authentication with oauth"
    )
    @GetMapping("/call-back")
    public ResponseEntity<Map<String ,String>> login(
            @RequestParam String token) {
        return ResponseEntity.ok()
                .body(Map.of("token", token));
    }

    @Operation(
            summary = "Logout user",
            description = "sing out of program and put oldest tokens in black list in redis"
    )
    @GetMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> logout() {
        this.authService.logout();
        return ResponseEntity.noContent().build();
    }
}
