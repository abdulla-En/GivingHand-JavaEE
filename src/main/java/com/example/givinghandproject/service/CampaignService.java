package com.example.givinghandproject.service;

import com.example.givinghandproject.dao.CampaignDAO;
import com.example.givinghandproject.dao.CampaignItemDAO;
import com.example.givinghandproject.dao.ItemDAO;
import com.example.givinghandproject.dao.UserDAO;
import com.example.givinghandproject.dto.campigan.CampaignListUpdateDTO;
import com.example.givinghandproject.dto.campigan.CampaignRequestDTO;
import com.example.givinghandproject.dto.campigan.CampaignResponseDTO;
import com.example.givinghandproject.dto.campigan.CampaignItemRequest;
import com.example.givinghandproject.entity.Campaign;
import com.example.givinghandproject.entity.CampaignItem;
import com.example.givinghandproject.entity.Item;
import com.example.givinghandproject.entity.User;
import com.example.givinghandproject.mapper.campigan.CampaignItemMapper;
import com.example.givinghandproject.mapper.campigan.CampaignMapper;
import com.example.givinghandproject.utilities.enums.CampaignStatus;
import com.example.givinghandproject.utilities.enums.ItemCategory;
import com.example.givinghandproject.utilities.enums.UpdateOperation;
import com.example.givinghandproject.utilities.enums.UserType;
import com.example.givinghandproject.utilities.exceptions.BusinessException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class CampaignService {
    @Inject private CampaignDAO campaignDAO;
    @Inject private ItemDAO itemDAO;
    @Inject private UserDAO userDAO;
    @Inject private CampaignItemDAO CiDAO;

    public String create(CampaignRequestDTO request, String userMail)
    {
        // check if the campaign with this tittle already exist
        campaignDAO.finByTitle(request.getTitle()).ifPresent(c ->{
            throw new BusinessException("campaign" , "this campaign already exist before");
        });

        // get the organization
        User organization = userDAO.getByEmail(userMail)
                .orElseThrow(() -> new BusinessException("email", "This email is not registered"));

        // trans the Dto into entity
        Campaign campaign = CampaignMapper.toEntity(request,organization);
        for(CampaignItemRequest need : request.getNeeds())
        {
            Item item = itemDAO.findById(need.getItemId());
            // to make it useful when  call it with ADD Method in update function
            int target = need.getTargetQuantity();
            CampaignItem ci = CampaignItemMapper.toEntity(target , item , campaign);
            campaign.getNeedList().add(ci);
        }
        campaignDAO.create(campaign);
        return campaign.getTitle();
    }
    // for any one
    public List<CampaignResponseDTO> getCampaigns(String categoryStr) {
        ItemCategory category = (categoryStr != null) ? ItemCategory.valueOf(categoryStr.toUpperCase()) : null;
        return campaignDAO.findAllOpen(category).stream()
                .map(CampaignMapper::toCampaignDto)
                .collect(Collectors.toList());
    }

    // for admin  or owned organization

    public String updateCampaignStatus(Long id , String status , User user )
    {
        Campaign campaign = checkOwnerShip(id , user);
        performUpdate(campaign, status);
        return campaign.getTitle();
    }

    public String updateCampaignRequiredResources(Long id ,User user , CampaignListUpdateDTO dto)
    {
        Campaign campaign = checkOwnerShip(id , user);

        // Validation Logic
        validateUpdateDto(dto);

        // --- DELETE OPERATION ---
        if(dto.getUpdateOperation() == UpdateOperation.DELETE)
        {
            CampaignItem ca = CiDAO.findById(dto.getCampaignItemId());
            if(ca == null) throw new BusinessException("Not Found", "Item not in campaign list");

            if(ca.getReceivedQuantity() > 0) {
                throw new BusinessException("Safety Check", "Cannot delete an item that has already received donations!");
            }

            campaign.getNeedList().remove(ca);
            CiDAO.delete(ca);
            return "Item removed from campaign.";
        }

        // --- UPDATE OPERATION ---
        if(dto.getUpdateOperation() == UpdateOperation.UPDATE)
        {
            CampaignItem ca = CiDAO.findById(dto.getCampaignItemId());
            if(ca == null) throw new BusinessException("Not Found", "Item not in campaign list");

            if(dto.getTargetQuantity() < ca.getReceivedQuantity()) {
                throw new BusinessException("Logic Error", "New target cannot be less than already received amount: " + ca.getReceivedQuantity());
            }

            ca.setTargetQuantity(dto.getTargetQuantity());

            CiDAO.update(ca);
            return "Target quantity updated.";
        }

        // --- ADD OPERATION ---
        if(dto.getUpdateOperation() == UpdateOperation.ADD)
        {
            Item item = itemDAO.findById(dto.getItemId());
            if(item == null) throw new BusinessException("Item", "Item not found in master list");

            boolean exists = campaign.getNeedList().stream()
                    .anyMatch(ci -> ci.getItem().getId().equals(item.getId()));
            if(exists) throw new BusinessException("Duplicate", "This item is already in the campaign list");

            CampaignItem ci = CampaignItemMapper.toEntity(dto.getTargetQuantity() , item , campaign);
            campaign.getNeedList().add(ci);
            return "New item added to campaign.";
        }

        return "Operation not supported.";
    }

    // Service Utilities
    private void performUpdate(Campaign campaign, String status)
    {
        try {
            campaign.setStatus(CampaignStatus.valueOf(status.toUpperCase().replace("\"", "").trim()));
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Invalid Status", "Status not supported");
        }
        campaignDAO.update(campaign);
    }

    private Campaign checkOwnerShip(Long id , User user)
    {
        Campaign campaign = campaignDAO.findById(id);
        if (campaign == null) {
            throw new BusinessException("Not Found", "Campaign not found");
        }
        boolean isAdmin = user.getRole() == UserType.Admin;
        boolean isOwner = campaign.getOrganization().getId().equals(user.getId());

        if (!isAdmin && !isOwner) {
            throw new BusinessException("Property rights","If it ain’t yours, don’t touch it noisy!");
        }
        return campaign;
    }

    private void validateUpdateDto(CampaignListUpdateDTO dto) {
        if (dto.getUpdateOperation() == UpdateOperation.DELETE && dto.getCampaignItemId()== null) {
            throw new BusinessException("ID","itemId is required for delete");
        }
        if (dto.getUpdateOperation() == UpdateOperation.ADD && (dto.getItemId() == null || dto.getTargetQuantity() == null)){
            throw new BusinessException("Input","Item and target quantity are required for add");
        }
        if (dto.getUpdateOperation() == UpdateOperation.UPDATE && (dto.getCampaignItemId()== null || dto.getTargetQuantity()== null)) {
            throw new BusinessException("Input","Both ID and target quantity are required for update");
        }
    }

}


