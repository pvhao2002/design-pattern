package com.store.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Entity sản phẩm.
 * Design pattern: Builder (Lombok), Prototype (clone).
 */
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(length = 50)
    private String sku;

    @Column(nullable = false)
    @Builder.Default
    private Integer stock = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    /**
     * Prototype pattern: tạo bản sao (không copy id, dùng cho biến thể/template).
     */
    public Product copy() {
        return Product.builder()
                .name(this.name)
                .description(this.description)
                .price(this.price)
                .sku(this.sku)
                .stock(this.stock)
                .category(this.category)
                .build();
    }
}
