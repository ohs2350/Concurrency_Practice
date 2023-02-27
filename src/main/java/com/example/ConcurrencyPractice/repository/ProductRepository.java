package com.example.ConcurrencyPractice.repository;

import com.example.ConcurrencyPractice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM c_product WHERE id = :id FOR UPDATE", nativeQuery = true)
    Product findByIdForUpdate(@Param("id") Long id);

//    @Modifying
//    @Query(value = "UPDATE c_product p SET p.views = p.views + 1 WHERE p.id = :id", nativeQuery = true)
//    int increaseViews(@Param("id") Long id);
}
