package com.pharmaflow.demo.Scheduler;

import com.pharmaflow.demo.Services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductScheduler {
    private final ProductService productService;

    @Scheduled(cron = "0 0 0 * * *")
    void runCheckForExpiredProduct() {
        this.productService.NotifyExpiredProducts();
    }

    @Scheduled(cron = "0 0 0 * * *")
    void runCheckForNearExpiredProduct() {
        this.productService.NotifyNearExpireProducts();
    }
}
