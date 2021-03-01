package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ + 2;
    public static final int NOT_FOUND_MEAL_ID = 10;

    public static final Meal meal = new Meal(100002, LocalDateTime.of(2021, Month.FEBRUARY, 24, 10, 0), "breakfast", 700);
    public static final Meal meal1 = new Meal(100003, LocalDateTime.of(2021, Month.FEBRUARY, 24, 11, 0), "snack", 500);
    public static final Meal meal2 = new Meal(100004, LocalDateTime.of(2021, Month.FEBRUARY, 24, 12, 0), "lunch", 1200);
    public static final Meal meal3 = new Meal(100005, LocalDateTime.of(2021, Month.FEBRUARY, 24, 14, 0), "dinner", 500);
    public static final Meal meal4 = new Meal(100006, LocalDateTime.of(2021, Month.FEBRUARY, 24, 16, 0), "snack", 300);
    public static final Meal meal5 = new Meal(100007, LocalDateTime.of(2021, Month.FEBRUARY, 24, 9, 0), "breakfast", 500);
    public static final Meal meal6 = new Meal(100008, LocalDateTime.of(2021, Month.FEBRUARY, 24, 11, 0), "snack", 300);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2021, Month.FEBRUARY, 24, 15, 0), "lunch", 1300);
    }


    public static Meal getUpdated() {
        Meal updated = new Meal(meal1);
        updated.setDateTime(LocalDateTime.of(2020, Month.APRIL, 25, 6, 0));
        updated.setDescription("updated meal");
        updated.setCalories(1100);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
