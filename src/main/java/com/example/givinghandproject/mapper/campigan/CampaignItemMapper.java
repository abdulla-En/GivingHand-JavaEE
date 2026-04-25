package com.example.givinghandproject.mapper.campigan;

import com.example.givinghandproject.dto.campigan.CampaignItemResponseDTO;
import com.example.givinghandproject.entity.CampaignItem;
import com.example.givinghandproject.entity.Campaign;
import com.example.givinghandproject.entity.Item;

public class CampaignItemMapper {
    public static CampaignItem toEntity(int target , Item item , Campaign campaign)
    {
        CampaignItem ci = new CampaignItem();
        ci.setCampaign(campaign);
        ci.setItem(item);
        ci.setTargetQuantity(target);

        return ci;
    }

    public static CampaignItemResponseDTO toDto(CampaignItem ci)
    {
        CampaignItemResponseDTO responseDTO = new CampaignItemResponseDTO();
        responseDTO.setItemName(ci.getItem().getName());
        responseDTO.setCategory(ci.getItem().getCategory().toString());
        responseDTO.setTargetQuantity(ci.getTargetQuantity());
        responseDTO.setReceivedQuantity(ci.getReceivedQuantity());
        return responseDTO;

    }

}
