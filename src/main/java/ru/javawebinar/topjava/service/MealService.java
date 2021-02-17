package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class MealService {

    @Autowired
    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal get(int id, int userId){
        Meal meal = repository.get(id, userId);
        return ValidationUtil.checkNotFoundWithId(meal, id);
    }

    public Meal create(Meal meal, int userId){
        return repository.save(meal, userId);
    }

    public Meal update(Meal meal, int userId){
        return repository.save(meal, userId);
    }

    public void delete(int id, int userId){
        ValidationUtil.checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Collection<Meal> getAll(int userId){
        Collection<Meal> collect = repository.getAll(userId);
        if(collect.isEmpty()){
            throw new NotFoundException("No meals for this user");
        }
        return collect;
    }



}