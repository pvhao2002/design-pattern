package com.store.service;

import com.store.dto.CategoryDTO;
import com.store.dto.CategoryRequest;
import com.store.entity.Category;
import com.store.factory.CategoryFactory;
import com.store.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Facade: API đơn giản cho quản lý category.
 */
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryFactory categoryFactory;

    public CategoryService(CategoryRepository categoryRepository, CategoryFactory categoryFactory) {
        this.categoryRepository = categoryRepository;
        this.categoryFactory = categoryFactory;
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        return categoryRepository.findByParentIsNull().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAllFlat() {
        return categoryRepository.findAll().stream()
                .map(this::toDTOFlat)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        return categoryRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + id));
    }

    @Transactional
    public CategoryDTO create(CategoryRequest req) {
        Category parent = req.getParentId() != null
                ? categoryRepository.findById(req.getParentId()).orElse(null)
                : null;
        Category entity = categoryFactory.create(req.getName(), req.getSlug(), req.getIcon());
        if (parent != null) {
            parent.addChild(entity);
            entity = categoryRepository.save(entity);
            categoryRepository.save(parent);
        } else {
            entity = categoryRepository.save(entity);
        }
        return toDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryRequest req) {
        Category entity = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + id));
        entity.setName(req.getName());
        if (req.getSlug() != null) entity.setSlug(req.getSlug());
        if (req.getIcon() != null) entity.setIcon(req.getIcon());
        if (req.getParentId() != null && !req.getParentId().equals(id)) {
            entity.setParent(categoryRepository.findById(req.getParentId()).orElse(null));
        }
        return toDTO(categoryRepository.save(entity));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id))
            throw new IllegalArgumentException("Category not found: " + id);
        categoryRepository.deleteById(id);
    }

    private CategoryDTO toDTO(Category c) {
        CategoryDTO dto = toDTOFlat(c);
        if (c.getChildren() != null && !c.getChildren().isEmpty()) {
            dto.setChildren(c.getChildren().stream().map(this::toDTO).toList());
        }
        return dto;
    }

    private CategoryDTO toDTOFlat(Category c) {
        return CategoryDTO.builder()
                .id(c.getId())
                .name(c.getName())
                .slug(c.getSlug())
                .icon(c.getIcon())
                .parentId(c.getParent() != null ? c.getParent().getId() : null)
                .build();
    }
}
