package com.example.givinghandproject.dto.inventory;

public class InventoryAllocationRequest {
    private Long inventoryItemId;
    private Long campaignItemId;
    private int quantity;

    public Long getInventoryItemId() { return inventoryItemId; }
    public void setInventoryItemId(Long inventoryItemId) { this.inventoryItemId = inventoryItemId; }
    public Long getCampaignItemId() { return campaignItemId; }
    public void setCampaignItemId(Long campaignItemId) { this.campaignItemId = campaignItemId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}

