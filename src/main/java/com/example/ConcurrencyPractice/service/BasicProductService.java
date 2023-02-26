package com.example.ConcurrencyPractice.service;

import com.example.ConcurrencyPractice.common.ProductDTO;
import com.example.ConcurrencyPractice.model.Product;
import com.example.ConcurrencyPractice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BasicProductService implements ProductService{
    @Autowired
    ProductRepository productRepository;

    public void createProduct(Product product) {
        productRepository.save(product);
    }

    @Transactional
    public Product viewProductWithProblem(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow();

        product.increaseView();

        productRepository.save(product);

        return product;
    }

    @Transactional
    public Product viewProductWithUpdateQuery(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow();

        productRepository.viewProductWithUpdateQuery(id);

        return product;
    }

    public Long getViews(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow();

        return product.getViews();
    }

}
