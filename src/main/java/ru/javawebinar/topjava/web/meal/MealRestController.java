package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public Meal get(int id) {
        Meal meal = service.get(id, authUserId());
        if (meal == null) {
            throw new NotFoundException("There is no meal with " + id);
        }
        return meal;
    }

    public Meal create(Meal meal) {
        return service.create(meal, authUserId());
    }

    public void delete(int id) {
        service.delete(id, authUserId());
    }

    public Collection<MealTo> getAll() {
        Collection<Meal> meals = service.getAll(authUserId());
        return MealsUtil.getTos(meals, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
}