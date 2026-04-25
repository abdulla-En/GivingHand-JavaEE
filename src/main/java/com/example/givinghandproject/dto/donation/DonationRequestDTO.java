package com.example.givinghandproject.dto.donation;

public class DonationRequestDTO {
    private Long campaignItemId;
    private int quantity;

    public Long getCampaignItemId() { return campaignItemId; }
    public void setCampaignItemId(Long campaignItemId) { this.campaignItemId = campaignItemId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
