package com.pharmaflow.demo;

import com.pharmaflow.demo.Entities.Category;
import com.pharmaflow.demo.Entities.Medicine;
import com.pharmaflow.demo.Entities.Product;
import com.pharmaflow.demo.Repositories.CategoryRepository;
import com.pharmaflow.demo.Repositories.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.spi.ClassTransformer;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRuner(ProductRepository productRepository, CategoryRepository categoryRepository) {

		return args -> {
			Category category = new Category("A", "some drugs");
			categoryRepository.save(category);

			Medicine product = new Medicine();
			product.setName("dolipran");
			product.setImage("emage");
			product.setCategory(category);
			product.setBarcode("bar");
			product.setDosageUnit(2);
			product.setPrescription(true);
			product.setPrice(new BigDecimal(1337));
			product.setQuantity(0);
			product.setExpiryDate(LocalDateTime.now());

			productRepository.save(product);
		};
	}
}
