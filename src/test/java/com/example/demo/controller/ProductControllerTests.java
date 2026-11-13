package com.example.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ProductControllerTests {

    @Autowired
    private WebApplicationContext applicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    @Test
    void supportsProductCrudOperations() throws Exception {
        var creation = mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content("""
                                {"name":"Notebook","description":"A lined notebook"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.productId", greaterThan(0)))
                .andExpect(jsonPath("$.name", is("Notebook")))
                .andExpect(jsonPath("$.description", is("A lined notebook")))
                .andReturn();
        String productPath = URI.create(creation.getResponse().getHeader("Location")).getPath();

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Wireless Mouse")));

        mockMvc.perform(get(productPath))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Notebook")));

        mockMvc.perform(put(productPath)
                        .contentType("application/json")
                        .content("""
                                {"name":"Updated notebook","description":"A dotted notebook"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated notebook")))
                .andExpect(jsonPath("$.description", is("A dotted notebook")));

        mockMvc.perform(delete(productPath))
                .andExpect(status().isNoContent());

        String productId = productPath.substring(productPath.lastIndexOf('/') + 1);
        mockMvc.perform(get(productPath))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.detail", is("Product with ID " + productId + " was not found.")));
    }

    @Test
    void loadsSampleProductsAtStartup() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("Wireless Mouse")))
                .andExpect(jsonPath("$[1].name", is("Mechanical Keyboard")))
                .andExpect(jsonPath("$[2].name", is("4K Monitor")));
    }

    @Test
    void returnsNotFoundForUnknownProducts() throws Exception {
        mockMvc.perform(get("/products/42"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.title", is("Not Found")));

        mockMvc.perform(put("/products/42")
                        .contentType("application/json")
                        .content("""
                                {"name":"Unknown","description":"Does not exist"}
                                """))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/products/42"))
                .andExpect(status().isNotFound());
    }

    @Test
    void returnsProblemDetailsForMalformedRequests() throws Exception {
        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content("{invalid json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.detail",
                        is("Request body is missing or contains invalid JSON.")));

        mockMvc.perform(get("/products/not-a-number"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", is("Bad Request")));
    }
}
