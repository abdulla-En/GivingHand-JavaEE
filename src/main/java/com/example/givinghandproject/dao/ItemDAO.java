package com.example.givinghandproject.dao;

import com.example.givinghandproject.entity.Item;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class ItemDAO {
    @PersistenceContext (unitName = "GivingHandEm")
    private EntityManager em;

    public void create(Item item) {
        em.persist(item);
    }

    public Item findById(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("SELECT i FROM Item i", Item.class).getResultList();
    }

}
