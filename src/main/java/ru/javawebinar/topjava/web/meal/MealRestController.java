package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {

    private MealService service;

    public Meal get(int id){
        Meal meal = service.get(id, authUserId());
        if(meal == null){
            throw new NotFoundException("There is no meal with " + id);
        }
        return meal;
    }

    public Meal create(Meal meal){
        return service.create(meal, authUserId());
    }

    public void delete(int id){
        service.delete(id, authUserId());
    }

    public Collection<Meal> getAll(){
        return service.getAll(authUserId());
    }
}