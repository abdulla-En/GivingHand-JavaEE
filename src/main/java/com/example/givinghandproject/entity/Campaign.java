package com.example.givinghandproject.entity;

import com.example.givinghandproject.utilities.enums.CampaignStatus;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "campaigns")
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true , nullable = false)
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private CampaignStatus status = CampaignStatus.OPEN;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private User organization;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CampaignItem> needList = new ArrayList<>();

    // JPA have to use a default constructor
    public Campaign(){}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CampaignStatus getStatus() {
        return status;
    }

    public void setStatus(CampaignStatus status) {
        this.status = status;
    }

    public User getOrganization() {
        return organization;
    }

    public void setOrganization(User organization) {
        this.organization = organization;
    }

    public List<CampaignItem> getNeedList() {
        return needList;
    }

    public void setNeedList(List<CampaignItem> needList) {
        this.needList = needList;
    }
}
