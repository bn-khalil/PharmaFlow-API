package com.pharmaflow.demo.Controllers;

import com.pharmaflow.demo.Dto.UserDto;
import com.pharmaflow.demo.Dto.UserNotificationDto;
import com.pharmaflow.demo.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(required = false) String q) {
        return ResponseEntity.ok()
                .body(this.userService.getAllUsers(q));
    }

    // get User by Id

    // list all pharmacists

    // list all admins
}
