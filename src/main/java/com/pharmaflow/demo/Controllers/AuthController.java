package com.pharmaflow.demo.Controllers;

import com.pharmaflow.demo.Dto.AuthResponse;
import com.pharmaflow.demo.Dto.UserLogin;
import com.pharmaflow.demo.Dto.UserRegister;
import com.pharmaflow.demo.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    public final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRegister userRegister) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.authService.register(userRegister));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserLogin userLogin) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.authService.login(userLogin));
    }
}
