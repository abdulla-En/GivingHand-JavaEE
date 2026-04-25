package com.example.givinghandproject.dto.campigan;

public class CampaignItemRequest {
    private Long itemId;
    private int targetQuantity;

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }
    public int getTargetQuantity() { return targetQuantity; }
    public void setTargetQuantity(int targetQuantity) { this.targetQuantity = targetQuantity; }
}
