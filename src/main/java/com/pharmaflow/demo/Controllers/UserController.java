package com.pharmaflow.demo.Controllers;

import com.pharmaflow.demo.Dto.EditUser;
import com.pharmaflow.demo.Dto.UserDto;
import com.pharmaflow.demo.Services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String role
    ) {
        return ResponseEntity.ok()
                .body(this.userService.getAllUsers(q, role));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> getAllUsers(
            @PathVariable("userId") UUID userId
    ) {
        return ResponseEntity.ok()
                .body(this.userService.getUserById(userId));
    }

    @PatchMapping
    public ResponseEntity<UserDto> editUser(
            @Valid
            @RequestBody EditUser editUser
            ) {
        return ResponseEntity.ok()
                .body(this.userService.editUser(editUser));
    }

    @PatchMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> enabledUser(
            @PathVariable("userId") UUID userId
    ) {
        this.userService.changeUserStatus(userId);
        return ResponseEntity.noContent().build();
    }
}
