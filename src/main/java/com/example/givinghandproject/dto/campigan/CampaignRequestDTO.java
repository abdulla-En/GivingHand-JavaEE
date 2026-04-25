package com.example.givinghandproject.dto.campigan;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class CampaignRequestDTO {
    @NotBlank
    private String title;
    private String description;
    private List<CampaignItemRequest> needs;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<CampaignItemRequest> getNeeds() { return needs; }
    public void setNeeds(List<CampaignItemRequest> needs) { this.needs = needs; }
}
