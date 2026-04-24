package com.example.givinghandproject.dao;

import com.example.givinghandproject.entity.Inventory;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Stateless
public class InventoryDAO {
    @PersistenceContext(unitName = "GivingHandEm")
    private EntityManager em;

    public void create(Inventory inventory) {
        em.persist(inventory);
    }

    public void update(Inventory inventory) {
        em.merge(inventory);
    }

    public Inventory findById(Long id) {
        return em.find(Inventory.class, id);
    }

    // maybe make it optional<> to handle the not found case later
    public Inventory findByWarehouseAndItem(Long warehouseId, Long itemId) {
        try {
            return em.createQuery("SELECT i FROM Inventory i WHERE i.warehouse.id = :wId AND i.item.id = :iId", Inventory.class)
                    .setParameter("wId", warehouseId)
                    .setParameter("iId", itemId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
