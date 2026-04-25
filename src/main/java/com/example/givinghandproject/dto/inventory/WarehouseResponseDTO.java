package com.example.givinghandproject.dto.inventory;

import java.util.List;

public class WarehouseResponseDTO {
    private Long id;
    private String name;
    private List<InventoryDTO> items;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<InventoryDTO> getItems() { return items; }
    public void setItems(List<InventoryDTO> items) { this.items = items; }
}
