package com.pharmaflow.demo.Services;

import com.pharmaflow.demo.Dto.AuthResponse;
import com.pharmaflow.demo.Dto.UserLogin;
import com.pharmaflow.demo.Dto.UserRegister;

public interface AuthService {
    AuthResponse register(UserRegister userRegister);
    AuthResponse login(UserLogin userLogin);
}
