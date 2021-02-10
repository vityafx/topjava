package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * @see <a href="http://topjava.herokuapp.com">Demo application</a>
 * @see <a href="https://github.com/JavaOPs/topjava">Initial project</a>
 */
public class Main {
    public static void main(String[] args) {
        //System.out.format("Hello TopJava Enterprise!");
        LocalDateTime d = LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0);
        //System.out.println(Meal.LDTWithoutT(d));
    }
}
