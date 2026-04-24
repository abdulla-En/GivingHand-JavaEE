package com.example.givinghandproject.dao;

import com.example.givinghandproject.entity.Donation;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class DonationDAO {
    @PersistenceContext(unitName = "GivingHandEm")
    private EntityManager em ;

    public void create(Donation donation) {
        em.persist(donation);
    }

    public Donation findById(Long id) {
        return em.find(Donation.class, id);
    }

    public void update(Donation donation) {
        em.merge(donation);
    }

    public void delete(Donation donation) {
        em.remove(em.merge(donation));
    }

    public List<Donation> findByDonor(Long donorId) {
        return em.createQuery("SELECT d FROM Donation d WHERE d.donor.id = :donorId", Donation.class)
                .setParameter("donorId", donorId)
                .getResultList();
    }
}
