package com.example.givinghandproject.mapper.item;

import com.example.givinghandproject.dto.item.ItemRequestDTO;
import com.example.givinghandproject.dto.item.ItemResponseDTO;
import com.example.givinghandproject.entity.Item;

public class ItemMapper {

    public static Item toEntity(ItemRequestDTO dto) {
        Item item = new Item();
        item.setName(dto.getName());
        item.setCategory(dto.getCategory());
        return item;
    }

    public static ItemResponseDTO toResponseDTO(Item entity) {
        ItemResponseDTO dto = new ItemResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCategory(entity.getCategory());
        return dto;
    }
}
