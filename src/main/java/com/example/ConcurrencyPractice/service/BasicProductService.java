package com.example.ConcurrencyPractice.service;

import com.example.ConcurrencyPractice.common.ProductDTO;
import com.example.ConcurrencyPractice.model.Product;
import com.example.ConcurrencyPractice.repository.LockProductRepository;
import com.example.ConcurrencyPractice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BasicProductService implements ProductService{
    @Autowired
    ProductRepository productRepository;
    @Autowired
    LockProductRepository lockProductRepository;

    public void createProduct(Product product) {
        productRepository.save(product);
    }

    // 동시성 문제가 존재하는 조회수 증가
    @Transactional
    public Product viewProductWithProblem(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("error"));

        product.increaseView();

        return product;
    }

    // 비관적 락을 사용한 조회수 증가
    @Transactional
    public Product viewProductWithPessimistic(Long id) {
        Product product = lockProductRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("error"));

        product.increaseView();

        return product;
    }

    // For Update - 베타적 락 사용한 조회수 증가
    @Transactional
    public Product viewProductWithUpdateQuery(Long id) {
        Product product = productRepository.findByIdForUpdate(id);

        product.increaseView();

        return product;
    }

    public Long getViews(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow();

        return product.getViews();
    }

}
