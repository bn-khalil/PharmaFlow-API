package com.pharmaflow.demo;

import com.pharmaflow.demo.Mappers.SaleItemMapper;
import com.pharmaflow.demo.Mappers.SaleMapper;
import com.pharmaflow.demo.Repositories.*;
import com.pharmaflow.demo.Services.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class PharmaFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(PharmaFlowApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRuner(
			ProductRepository productRepository,
			CategoryRepository categoryRepository,
			ProductService productService,
			SaleRepository saleRepository,
			SaleItemRepository saleItemRepository,
			SaleMapper saleMapper,
			SaleItemMapper saleItemMapper,
			UserRepository userRepository) {

//		User user = userRepository.findUserByEmail("bn32@gmail.com").get();
//		Sale sale = new Sale();
//		sale.setTotalAmount(new BigDecimal(12));
//		sale.setSaler(user);
//		sale.setSaleItems(List.of(new SaleItem()));
//


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
