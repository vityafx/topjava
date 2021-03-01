package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal actual = service.get(MEAL_ID, USER_ID);
        MealTestData.assertMatch(actual, MealTestData.meal);
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_MEAL_ID, NOT_FOUND));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> meals = service.getBetweenInclusive(LocalDate.of(2021, 2, 24), LocalDate.of(2021, 2, 24), USER_ID);
        assertMatch(meals, MealTestData.meal, MealTestData.meal1, MealTestData.meal2, MealTestData.meal3, MealTestData.meal4);
        List<Meal> meals_admin = service.getBetweenInclusive(LocalDate.of(2021, 2, 24), LocalDate.of(2021, 2, 24), ADMIN_ID);
        assertMatch(meals_admin, MealTestData.meal5, MealTestData.meal6);
        List<Meal> meal_not_found = service.getBetweenInclusive(LocalDate.of(2021, 2, 24), LocalDate.of(2021, 2, 24), NOT_FOUND);
        assertTrue(meal_not_found.isEmpty());
    }

    @Test
    public void getAllUser() {
        List<Meal> meals = service.getAll(USER_ID);
        MealTestData.assertMatch(meals, MealTestData.meal, MealTestData.meal1, MealTestData.meal2, MealTestData.meal3, MealTestData.meal4);
    }

    @Test
    public void getAllAdmin() {
        List<Meal> meals = service.getAll(ADMIN_ID);
        MealTestData.assertMatch(meals, MealTestData.meal5, MealTestData.meal6);
    }

    @Test
    public void getAll_NonExistentUser() {
        List<Meal> meals = service.getAll(NOT_FOUND);
        assertTrue(meals.isEmpty());
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(100003, USER_ID), MealTestData.getUpdated());
    }

    @Test
    public void updateOthersMeal() {
        Meal updated = MealTestData.getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updated, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(MealTestData.getNew(), ADMIN_ID);
        Integer newId = created.getId();
        Meal newMeal = MealTestData.getNew();
        newMeal.setId(newId);
        MealTestData.assertMatch(created, newMeal);
        MealTestData.assertMatch(service.get(newId, ADMIN_ID), newMeal);
    }
}