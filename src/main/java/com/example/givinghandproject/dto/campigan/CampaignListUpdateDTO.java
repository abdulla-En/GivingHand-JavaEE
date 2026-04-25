package com.example.givinghandproject.dto.campigan;


import com.example.givinghandproject.utilities.enums.UpdateOperation;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public class CampaignListUpdateDTO {
    @NotBlank(message = "You should add an operation")
    private UpdateOperation updateOperation; // ADD , Delete , Update
    @Nullable
    private Long campaignItemId; // Update , Delete
    @Nullable
    private Long ItemId; // ADD
    @Nullable
    private Integer targetQuantity; // ADD , Update


    public void setCampaignItemId(@Nullable Long campaignItemId) {
        this.campaignItemId = campaignItemId;
    }

    @Nullable
    public Long getCampaignItemId() {
        return campaignItemId;
    }

    public void setUpdateOperation(UpdateOperation updateOperation) {
        this.updateOperation = updateOperation;
    }

    public UpdateOperation getUpdateOperation() {
        return updateOperation;
    }

    public void setTargetQuantity(@Nullable Integer targetQuantity) {
        this.targetQuantity = targetQuantity;
    }

    @Nullable
    public Integer getTargetQuantity() {
        return targetQuantity;
    }

    public void setItemId(@Nullable Long itemId) {
        ItemId = itemId;
    }

    @Nullable
    public Long getItemId() {
        return ItemId;
    }
}
