package com.example.givinghandproject.dao;

import com.example.givinghandproject.entity.CampaignItem;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

@Stateless
public class CampaignItemDAO {
    @PersistenceContext(unitName = "GivingHandEm")
    private EntityManager em ;

    public CampaignItem findById(Long id) {
        return em.find(CampaignItem.class, id);
    }

    public void update(CampaignItem campaignItem) {
        em.merge(campaignItem);
    }
    public boolean delete (CampaignItem campaignItem) {
        try {
            em.remove(campaignItem);
            return true;
        } catch (PersistenceException e) {
            return false;
        }
    }
}
