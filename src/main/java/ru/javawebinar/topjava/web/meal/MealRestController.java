package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public Meal get(int id) {
        return service.get(id, authUserId());
    }

    public Meal create(Meal meal) {
        ValidationUtil.checkNew(meal);
        return service.create(meal, authUserId());
    }

    public Meal update(Meal meal, int id) {
        assureIdConsistent(meal, id);
        return service.update(meal, authUserId());
    }

    public void delete(int id) {
        service.delete(id, authUserId());
    }

    public Collection<MealTo> getAll() {
        Collection<Meal> meals = service.getAll(authUserId());
        return MealsUtil.getTos(meals, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Collection<MealTo> getFiltered(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return getAll().stream().filter(mealTo -> {
            return mealTo.getDateTime().isBefore(LocalDateTime.of(endDate, endTime)) && mealTo.getDateTime().isAfter(LocalDateTime.of(startDate, startTime));
        }).collect(Collectors.toCollection(ArrayList::new));
    }
}