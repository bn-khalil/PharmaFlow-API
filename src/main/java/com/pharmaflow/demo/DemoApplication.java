package com.pharmaflow.demo;

import com.github.javafaker.Faker;
import com.pharmaflow.demo.Dto.MedicineDto;
import com.pharmaflow.demo.Dto.ProductDto;
import com.pharmaflow.demo.Entities.Category;
import com.pharmaflow.demo.Entities.Medicine;
import com.pharmaflow.demo.Entities.Product;
import com.pharmaflow.demo.Repositories.CategoryRepository;
import com.pharmaflow.demo.Repositories.ProductRepository;
import com.pharmaflow.demo.Services.ProductService;
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
	public CommandLineRunner commandLineRuner(
			ProductRepository productRepository,
			CategoryRepository categoryRepository,
			ProductService productService ) {

		List<String> items = List.of (
				"Pain & Fever",
				"Cold, Flu & Allergy",
				"Chronic Diseases",
				"Digestive System",
				"Vitamins & Supplements"
		);

		return args -> {
//			Faker faker = new Faker();
//			for (int i = 0; i < 20; i++) {//
//				MedicineDto product = new MedicineDto();
//				product.setName(faker.medical().medicineName());
//				product.setImage("photo_" + i);
//				product.setCategory(items.get(faker.random().nextInt(items.size())));
//				product.setBarcode("bar__" + i);
//				product.setDosageUnit(2);
//				product.setPrescription(faker.bool().bool());
//
//				product.setPrice(BigDecimal.valueOf(
//						faker.number().randomDouble(2, 5, 100)
//				));
//
//				product.setQuantity(faker.number().numberBetween(0, 200));
//
//				product.setExpiryDate(
//						LocalDateTime.now().plusDays(
//								faker.number().numberBetween(30, 730)
//						)
//				);
//
//				productService.createProduct(product);
//			}
		};
	}
}
