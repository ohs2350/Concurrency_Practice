package com.example.ConcurrencyPractice.common;

import com.example.ConcurrencyPractice.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private Long id;

    private String name;

    private Long quantity;

    private Long views;

    public Product toEntity() {
        return Product.builder()
                .id(this.id)
                .name(this.name)
                .quantity(this.quantity)
                .views(0L)
                .build();
    }
}
