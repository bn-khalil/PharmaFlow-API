package com.pharmaflow.demo.Security;

import com.pharmaflow.demo.Entities.User;
import com.pharmaflow.demo.Enums.Role;
import com.pharmaflow.demo.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setFirstName("admin");
            admin.setLastName("Admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            this.userRepository.save(admin);
            System.out.println("✅ Root Admin created: admin@pharmaflow.com / Admin@123");
        }
    }
}
