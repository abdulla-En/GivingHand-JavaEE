package com.example.givinghandproject.service;

import com.example.givinghandproject.dao.ItemDAO;
import com.example.givinghandproject.dto.item.ItemRequestDTO;
import com.example.givinghandproject.dto.item.ItemResponseDTO;
import com.example.givinghandproject.entity.Item;
import com.example.givinghandproject.mapper.item.ItemMapper;
import com.example.givinghandproject.utilities.exceptions.BusinessException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class ItemService {

    @Inject
    private ItemDAO itemDAO;

    public Long createItem(ItemRequestDTO dto) {
        Item item = ItemMapper.toEntity(dto);
        itemDAO.create(item);
        return item.getId();
    }

    public List<ItemResponseDTO> getAllItems() {
        return itemDAO.findAll().stream()
                .map(ItemMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ItemResponseDTO getItemById(Long id) {
        Item item = itemDAO.findById(id);
        if (item == null) {
            throw new BusinessException("Item", "Item not found with ID: " + id);
        }
        return ItemMapper.toResponseDTO(item);
    }
}
