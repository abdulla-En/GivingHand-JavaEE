package com.example.givinghandproject.dto.campigan;

public class CampaignItemResponseDTO {
    private String itemName;
    private String category;
    private int targetQuantity;
    private int receivedQuantity;


    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public int getTargetQuantity() { return targetQuantity; }
    public void setTargetQuantity(int targetQuantity) { this.targetQuantity = targetQuantity; }
    public int getReceivedQuantity() { return receivedQuantity; }
    public void setReceivedQuantity(int receivedQuantity) { this.receivedQuantity = receivedQuantity; }
}
