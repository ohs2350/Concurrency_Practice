package com.example.ConcurrencyPractice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "C_PRODUCT")
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Long quantity;

    private Long views;


    public void increaseView() {
        this.views++;
    }

    public void decreaseQuantity(Long quantity) {
        if (this.quantity - quantity < 0) {
            throw new RuntimeException("재고 마이너스");
        }
        this.quantity = this.quantity - quantity;
    }
}
