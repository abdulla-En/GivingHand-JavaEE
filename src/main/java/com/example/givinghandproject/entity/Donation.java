package com.example.givinghandproject.entity;

import com.example.givinghandproject.utilities.enums.DonationStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "donations")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private User donor;

    @ManyToOne
    @JoinColumn(name = "campaign_item_id")
    private CampaignItem campaignItem;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private DonationStatus status = DonationStatus.COMMITTED;

    // JPA have to use a default constructor
    public Donation(){}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getDonor() {
        return donor;
    }

    public void setDonor(User donor) {
        this.donor = donor;
    }

    public CampaignItem getCampaignItem() {
        return campaignItem;
    }

    public void setCampaignItem(CampaignItem campaignItem) {
        this.campaignItem = campaignItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public DonationStatus getStatus() {
        return status;
    }

    public void setStatus(DonationStatus status) {
        this.status = status;
    }
}
