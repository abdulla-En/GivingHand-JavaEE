package com.example.givinghandproject.dao;

import com.example.givinghandproject.entity.Campaign;
import com.example.givinghandproject.utilities.enums.CampaignStatus;
import com.example.givinghandproject.utilities.enums.ItemCategory;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

@Stateless
public class CampaignDAO {
    @PersistenceContext(unitName = "GivingHandEm")
    private EntityManager em;

    public void create(Campaign campaign) {
        em.persist(campaign);
    }

    public Campaign findById(Long id) {
        return em.find(Campaign.class, id);
    }
    public Optional<Campaign> finByTitle(String title){
        try {
            Campaign campaign = em.createQuery("SELECT c FROM Campaign c WHERE c.title = :title", Campaign.class)
                    .setParameter("title", title)
                    .getSingleResult();
            return Optional.of(campaign);
        }catch (NoResultException e){
            return Optional.empty();
        }
    }

    public void update(Campaign campaign) {
        em.merge(campaign);
    }

    public List<Campaign> findAllOpen(ItemCategory category) {
        StringBuilder jpql = new StringBuilder("SELECT c FROM Campaign c WHERE c.status = :status");
        if (category != null) {
            jpql.append(" AND EXISTS (SELECT ci FROM CampaignItem ci WHERE ci.campaign = c AND ci.item.category = :category)");
        }
        TypedQuery<Campaign> query = em.createQuery(jpql.toString(), Campaign.class);
        query.setParameter("status", CampaignStatus.OPEN);
        if (category != null) {
            query.setParameter("category", category);
        }
        return query.getResultList();
    }
}
