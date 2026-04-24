package com.example.givinghandproject.dao;

import com.example.givinghandproject.entity.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.util.List;
import java.util.Optional;

@Stateless
public class UserDAO {
    @PersistenceContext(unitName = "GivingHandEm")
    EntityManager em;

    // insert
    public void save(User user)
    {
        em.persist(user);
    }

    // update
    public void update(User user)
    {
        em.merge(user);
    }

    //Get by id -> no usage after Jaas at all
    public Optional<User> getById(Long id)
    {

        return Optional.ofNullable(em.find(User.class, id));
    }

    //Get by mail
    public Optional<User> getByEmail(String email)
    {
        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.email = :email" , User.class)
                    .setParameter("email",email)
                    .getSingleResult(); // if empty returns NoResultException
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    // delete user by email
    public boolean delete (User user ) {
        try {
            em.remove(user);
            return true;
        } catch (PersistenceException e) {
            return false;
        }
    }

    // Get all users
    public List<User> getAllUsers()
    {
        return em.createQuery("SELECT u from User u" , User.class)
                .getResultList();
    }
}


// getSingleResult() -> if no result throw NoResultException [handle it with optional]
// and in service we use [ifPresent] or [orElseThrow] depend on you search for result or no result

// getResultList() -> more flexible no need optional [return empty if no result] \/_\/

