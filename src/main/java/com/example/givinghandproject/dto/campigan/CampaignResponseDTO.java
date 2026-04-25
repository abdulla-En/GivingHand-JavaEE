package com.example.givinghandproject.dto.campigan;

import com.example.givinghandproject.utilities.enums.CampaignStatus;

import java.util.List;

public class CampaignResponseDTO {
    private Long id;
    private String title;
    private String description;
    private CampaignStatus status;
    private String organizationName;
    private List<CampaignItemResponseDTO> items;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public CampaignStatus getStatus() { return status; }
    public void setStatus(CampaignStatus status) { this.status = status; }
    public String getOrganizationName() { return organizationName; }
    public void setOrganizationName(String organizationName) { this.organizationName = organizationName; }
    public List<CampaignItemResponseDTO> getItems() { return items; }
    public void setItems(List<CampaignItemResponseDTO> items) { this.items = items; }
}
