package com.store.service;

import com.store.dto.ProductDTO;
import com.store.dto.ProductRequest;
import com.store.entity.Category;
import com.store.entity.Product;
import com.store.factory.ProductFactory;
import com.store.repository.CategoryRepository;
import com.store.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Facade: API đơn giản cho quản lý sản phẩm.
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductFactory productFactory;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,
                          ProductFactory productFactory) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productFactory = productFactory;
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findByCategoryId(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        return productRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
    }

    @Transactional
    public ProductDTO create(ProductRequest req) {
        Category category = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + req.getCategoryId()));
        Product entity = productFactory.create(
                req.getName(), req.getDescription(), req.getPrice(),
                req.getSku(), req.getStock() != null ? req.getStock() : 0, category);
        return toDTO(productRepository.save(entity));
    }

    @Transactional
    public ProductDTO update(Long id, ProductRequest req) {
        Product entity = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
        Category category = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + req.getCategoryId()));
        entity.setName(req.getName());
        entity.setDescription(req.getDescription());
        entity.setPrice(req.getPrice());
        entity.setSku(req.getSku());
        entity.setStock(req.getStock() != null ? req.getStock() : 0);
        entity.setCategory(category);
        return toDTO(productRepository.save(entity));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!productRepository.existsById(id))
            throw new IllegalArgumentException("Product not found: " + id);
        productRepository.deleteById(id);
    }

    private ProductDTO toDTO(Product p) {
        return ProductDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .sku(p.getSku())
                .stock(p.getStock())
                .categoryId(p.getCategory() != null ? p.getCategory().getId() : null)
                .categoryName(p.getCategory() != null ? p.getCategory().getName() : null)
                .build();
    }
}
