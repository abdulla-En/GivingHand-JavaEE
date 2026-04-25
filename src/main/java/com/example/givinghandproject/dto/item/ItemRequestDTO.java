package com.example.givinghandproject.dto.item;

import com.example.givinghandproject.utilities.enums.ItemCategory;
import jakarta.validation.constraints.NotBlank;

public class ItemRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Category is required")
    private ItemCategory category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemCategory getCategory() {
        return category;
    }

    public void setCategory(ItemCategory category) {
        this.category = category;
    }
}
