package com.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 120)
    private String slug;

    @Size(max = 50)
    private String icon;

    private Long parentId;
}
