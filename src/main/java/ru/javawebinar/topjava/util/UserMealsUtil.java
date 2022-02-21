package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );


        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(10, 0), 2000);
        mealsTo.forEach(System.out::println);

        List<UserMealWithExcess> mealsToStream = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(13, 0), 2000);
        mealsTo.forEach(System.out::println);




    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        List<UserMealWithExcess> userMealWithExcessesList = new ArrayList<>();
        Map<LocalDate, Integer> map = new HashMap<>();
        for (UserMeal userMeal : meals) {

            if (map.containsKey(userMeal.getDateTime().toLocalDate())) {
                map.put(userMeal.getDateTime().toLocalDate(),
                        map.get(userMeal.getDateTime().toLocalDate()) + userMeal.getCalories());
            } else {
                map.put(userMeal.getDateTime().toLocalDate(), userMeal.getCalories());
            }
        }

        for (UserMeal userMeal : meals) {
            if (userMeal.getDateTime().toLocalTime().compareTo(startTime) >= 0 &&
                    userMeal.getDateTime().toLocalTime().compareTo(endTime) < 0) {
                if (map.get(userMeal.getDateTime().toLocalDate()) <= caloriesPerDay) {
                    userMealWithExcessesList.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(),
                            userMeal.getCalories(), false));
                } else {
                    userMealWithExcessesList.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(),
                            userMeal.getCalories(), true));
                }
            }
        }

        return userMealWithExcessesList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams

        Map<LocalDate, Integer> map = meals
                .stream()
                .collect(Collectors.toMap(
                        k -> k.getDateTime().toLocalDate(),
                        v -> v.getCalories(),
                        (v1, v2) -> v1 + v2));

        List<UserMealWithExcess> userMealWithExcessList = meals
                .stream()
                .filter(u -> u.getDateTime().toLocalTime().compareTo(startTime) >= 0 &&
                        u.getDateTime().toLocalTime().compareTo(endTime) < 0)
                .map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(),
                        userMeal.getDescription(), userMeal.getCalories(),
                        map.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());

        return userMealWithExcessList;

    }

}


