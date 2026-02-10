package com.store.flyweight;

import com.store.entity.Category;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Design pattern: Flyweight — chia sẻ metadata category (slug, icon) để giảm bộ nhớ.
 */
@Component
public class CategoryMetadataCache {

    private final Map<Long, CategoryMetadata> cache = new ConcurrentHashMap<>();

    public CategoryMetadata getOrPut(Category category) {
        if (category == null) return null;
        return cache.computeIfAbsent(category.getId(), id -> new CategoryMetadata(
                category.getSlug(),
                category.getIcon(),
                category.getName()));
    }

    public void evict(Long categoryId) {
        cache.remove(categoryId);
    }

    public record CategoryMetadata(String slug, String icon, String name) {}
}
