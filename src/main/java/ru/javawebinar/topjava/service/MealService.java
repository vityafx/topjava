package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal get(int id, int userId){
        Meal meal = repository.get(id);
        ValidationUtil.checkCurrentUser(meal, userId);
        return ValidationUtil.checkNotFoundWithId(meal, id);
    }

    public Meal create(Meal meal, int userId){
        ValidationUtil.checkCurrentUser(meal, userId);
        return repository.save(meal);
    }

    public void delete(int id, int userId){
        Meal meal = repository.get(id);
        ValidationUtil.checkCurrentUser(meal, userId);
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    public Collection<Meal> getAll(int userId){
        Collection<Meal> collection = repository.getAll();
        ArrayList<Meal> collect = collection.stream()
                .filter(meal -> meal.getUserId() == userId)
                .collect(Collectors.toCollection(ArrayList::new));
        if(collect.isEmpty()){
            throw new NotFoundException("No meals for this user");
        }
        return collect;
    }



}