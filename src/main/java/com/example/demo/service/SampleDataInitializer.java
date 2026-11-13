package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SampleDataInitializer implements ApplicationRunner {

    private final ProductRepository productRepository;

    public SampleDataInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (productRepository.count() == 0) {
            productRepository.saveAll(List.of(
                    new Product("Wireless Mouse", "Ergonomic wireless mouse with adjustable DPI."),
                    new Product("Mechanical Keyboard", "Compact mechanical keyboard with tactile switches."),
                    new Product("4K Monitor", "27-inch 4K UHD monitor with an IPS display.")
            ));
        }
    }
}
