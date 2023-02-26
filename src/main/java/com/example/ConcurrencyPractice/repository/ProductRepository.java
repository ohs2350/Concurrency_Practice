package com.example.ConcurrencyPractice.repository;

import com.example.ConcurrencyPractice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Query(value = "UPDATE C_PRODUCT p SET p.views = p.views + 1 WHERE p.id = :id", nativeQuery = true)
    int viewProductWithUpdateQuery(Long id);
}
