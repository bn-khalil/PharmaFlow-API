package com.pharmaflow.demo.Security;

import com.pharmaflow.demo.Entities.Category;
import com.pharmaflow.demo.Entities.User;
import com.pharmaflow.demo.Enums.Role;
import com.pharmaflow.demo.Repositories.CategoryRepository;
import com.pharmaflow.demo.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // in case database is empty not user at all and the program should have an admin to access
        // i will create a default admin
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setFirstName("admin");
            admin.setLastName("Admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            admin.setActive(true);
            admin.setFirstAdmin(true);
            this.userRepository.save(admin);
            System.out.println("✅ Root Admin created: admin@pharmaflow.com / Admin@123");
        }
        // as well in categories some products should be under general category in case
        // category deleted

        if (categoryRepository.findByName("generalCategory").isEmpty()) {
            Category category = new Category("generalCategory", "Default category for products");
            this.categoryRepository.save(category);
        }
    }
}
