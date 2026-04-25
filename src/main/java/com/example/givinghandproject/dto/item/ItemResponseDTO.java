package com.example.givinghandproject.dto.item;

import com.example.givinghandproject.utilities.enums.ItemCategory;

public class ItemResponseDTO {
    private Long id;
    private String name;
    private ItemCategory category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
