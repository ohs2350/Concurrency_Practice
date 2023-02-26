package com.example.ConcurrencyPractice.controller;

import com.example.ConcurrencyPractice.common.ProductDTO;
import com.example.ConcurrencyPractice.model.Product;
import com.example.ConcurrencyPractice.service.BasicProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    BasicProductService basicProductService;

    /** 동시성 문제가 존재하는 api */
    @GetMapping("/view/{id}")
    public ResponseEntity<?> viewProductWithProblem(@PathVariable Long id) {
        Product product = basicProductService.viewProductWithProblem(id);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product product = productDTO.toEntity();
        basicProductService.createProduct(product);

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    /**
     * 동시성 처리 학습
     * 1. 낙관적 락
     * 2. 비관적 락
     * 3. Update 쿼리
     * 4. Redis
     * 5. 스레드풀
     * 6. (ConcurrentHashMap, Atomic 클래스, Synchronized, Executor, CompletableFuture)
     * */
}
