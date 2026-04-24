package com.example.givinghandproject.dao;

import com.example.givinghandproject.entity.Warehouse;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class WarehouseDAO {
    @PersistenceContext(unitName = "GivingHandEm")
    private EntityManager em;

    public void create(Warehouse warehouse) {
        em.persist(warehouse);
    }

    public Warehouse findById(Long id) {
        return em.find(Warehouse.class, id);
    }

    public List<Warehouse> findByOrganization(Long orgId) {
        return em.createQuery("SELECT w FROM Warehouse w WHERE w.organization.id = :orgId", Warehouse.class)
                .setParameter("orgId", orgId)
                .getResultList();
    }
}
