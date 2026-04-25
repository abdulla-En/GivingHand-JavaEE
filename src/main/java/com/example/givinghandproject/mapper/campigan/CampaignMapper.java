package com.example.givinghandproject.mapper.campigan;

import com.example.givinghandproject.dto.campigan.CampaignRequestDTO;
import com.example.givinghandproject.dto.campigan.CampaignResponseDTO;
import com.example.givinghandproject.entity.Campaign;
import com.example.givinghandproject.entity.User;
import com.example.givinghandproject.utilities.enums.CampaignStatus;

import java.util.stream.Collectors;

// intelligent Mapper
public class CampaignMapper {
    public static Campaign toEntity(CampaignRequestDTO requestDTO , User organization )
    {
        Campaign campaign = new Campaign();
        campaign.setTitle(requestDTO.getTitle());
        campaign.setDescription(requestDTO.getDescription());
        campaign.setOrganization(organization);
        campaign.setStatus(CampaignStatus.OPEN);
        return campaign;
    }

    public static CampaignResponseDTO toCampaignDto(Campaign entity)
    {
        CampaignResponseDTO dto = new CampaignResponseDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setOrganizationName(entity.getOrganization().getFullName().trim().split(" ")[0]);
        // the important part
        dto.setItems(entity.getNeedList()
                           .stream()
                           .map(CampaignItemMapper::toDto)
                           .collect(Collectors.toList()));
        return dto ;
    }
}
