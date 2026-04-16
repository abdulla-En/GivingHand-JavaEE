package com.example.givinghandproject.dao;

import com.example.givinghandproject.entity.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;

@Stateless
public class UserRepo {
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

    //Get by id
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

    //delete
//    public void delete(Long id)
//    {
//        User user = getById(id).get();
//        em.remove(user);
//    }
}
