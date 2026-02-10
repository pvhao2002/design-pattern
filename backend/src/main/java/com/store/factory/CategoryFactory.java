package com.store.factory;

import com.store.entity.Category;
import org.springframework.stereotype.Component;

/**
 * Design pattern: Factory — tạo Category thay vì new trực tiếp.
 */
@Component
public class CategoryFactory {

    public Category create(String name) {
        return create(name, null, null);
    }

    public Category create(String name, String slug, String icon) {
        return Category.builder()
                .name(name)
                .slug(slug != null ? slug : toSlug(name))
                .icon(icon)
                .build();
    }

    public Category createChild(String name, Category parent) {
        Category child = create(name);
        parent.addChild(child);
        return child;
    }

    private static String toSlug(String name) {
        if (name == null) return "";
        return name.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");
    }
}
