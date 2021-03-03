package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional (readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User ref = entityManager.getReference(User.class, userId);
        if(meal.isNew()){
            meal.setUser(ref);
            entityManager.persist(meal);
            return meal;
        }else {
            Integer mealUserId = entityManager.getReference(Meal.class, meal.id()).getUser().getId();
            if (mealUserId == null || mealUserId != userId){
                throw new NotFoundException("This food is not yours!");
            }
            meal.setUser(ref);
            return entityManager.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return entityManager
                .createQuery("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Query query = entityManager.createQuery("SELECT m FROM Meal m WHERE m.id=:id AND m.user.id=:userId", Meal.class);
        query.setParameter("id", id);
        query.setParameter("userId", userId);
        List<Meal> meals = query.getResultList();
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return new ArrayList<Meal>(entityManager.createQuery("SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC", Meal.class)
                .setParameter("userId", userId)
                .getResultList());
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        Query query = entityManager.createQuery("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime >=:startDateTime AND m.dateTime<:endDateTime ORDER BY m.dateTime DESC");
        query.setParameter("userId", userId);
        query.setParameter("startDateTime", startDateTime);
        query.setParameter("endDateTime", endDateTime);
        return new ArrayList<Meal>(query.getResultList());
    }
}