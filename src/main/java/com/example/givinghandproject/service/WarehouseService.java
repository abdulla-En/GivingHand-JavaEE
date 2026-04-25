package com.example.givinghandproject.service;

import com.example.givinghandproject.dao.CampaignItemDAO;
import com.example.givinghandproject.dao.InventoryDAO;
import com.example.givinghandproject.dao.ItemDAO;
import com.example.givinghandproject.dao.WarehouseDAO;
import com.example.givinghandproject.dto.inventory.InventoryAllocationRequest;
import com.example.givinghandproject.dto.inventory.InventoryDTO;
import com.example.givinghandproject.dto.inventory.WarehouseResponseDTO;
import com.example.givinghandproject.entity.CampaignItem;
import com.example.givinghandproject.entity.Inventory;
import com.example.givinghandproject.entity.User;
import com.example.givinghandproject.entity.Warehouse;
import com.example.givinghandproject.jms.NotificationProducer;
import com.example.givinghandproject.utilities.exceptions.BusinessException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class WarehouseService {
    @Inject private WarehouseDAO warehouseDAO;
    @Inject private InventoryDAO inventoryDAO;
    @Inject private CampaignItemDAO campaignItemDAO;
    @Inject private NotificationProducer notificationProducer;
    @Inject private ItemDAO itemDAO;

    public void setupWarehouse(String name, User org) {
        Warehouse wh = new Warehouse();
        wh.setName(name);
        wh.setOrganization(org);
        warehouseDAO.create(wh);
    }

    @Transactional
    public void allocateResources(InventoryAllocationRequest request) {
        Inventory stock = inventoryDAO.findById(request.getInventoryItemId());
        CampaignItem target = campaignItemDAO.findById(request.getCampaignItemId());

        if (stock == null || target == null) throw new BusinessException("Error", "Resources not found");

        if (stock.getQuantity() < request.getQuantity()) {
            throw new BusinessException("Integrity",
                    "Insufficient stock. Available: " + stock.getQuantity() + ", Requested: " + request.getQuantity());
        }
        int remainingNeeded = target.getTargetQuantity() - target.getReceivedQuantity();
        if (request.getQuantity() > remainingNeeded) {
            throw new BusinessException("Limit Exceeded",
                    "Campaign only needs " + remainingNeeded + " more items. You tried to allocate " + request.getQuantity());
        }

        stock.setQuantity(stock.getQuantity() - request.getQuantity());
        target.setReceivedQuantity(target.getReceivedQuantity() + request.getQuantity());

        inventoryDAO.update(stock);
        campaignItemDAO.update(target);

        if (stock.getQuantity() <= stock.getLowStockThreshold()) {
            notificationProducer.sendEvent("LOW_STOCK_ALERT",
                    "Item " + stock.getItem().getName() + " is running low!",
                    stock.getWarehouse().getOrganization().getEmail());
        }
    }

    public WarehouseResponseDTO getDashboard(Long orgId) {
        Warehouse wh = warehouseDAO.findByOrganization(orgId);
        if (wh == null) return null;

        WarehouseResponseDTO response = new WarehouseResponseDTO();
        response.setId(wh.getId());
        response.setName(wh.getName());

        List<InventoryDTO> itemDTOs = wh.getInventoryList().stream().map(i -> {
            InventoryDTO dto = new InventoryDTO();
            dto.setItemName(i.getItem().getName());
            dto.setQuantity(i.getQuantity());
            return dto;
        }).collect(Collectors.toList());

        response.setItems(itemDTOs);
        return response;
    }

    @Transactional
    public void addOrUpdateInventory(Long warehouseId, Long itemId, int quantity, int threshold) {
        Inventory existingInventory = inventoryDAO.findByWarehouseAndItem(warehouseId, itemId);

        if (existingInventory != null) {
            existingInventory.setQuantity(existingInventory.getQuantity() + quantity);
            inventoryDAO.update(existingInventory);
        } else {
            Inventory newInv = new Inventory();
            newInv.setWarehouse(warehouseDAO.findById(warehouseId));
            newInv.setItem(itemDAO.findById(itemId));
            newInv.setQuantity(quantity);
            newInv.setLowStockThreshold(threshold);
            inventoryDAO.create(newInv);
        }
    }
}