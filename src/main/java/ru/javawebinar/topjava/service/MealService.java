package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealService {

    public List<MealTo> getAllMealTo();

    public void saveMealTo(MealTo mealTo);

    public MealTo getMealTo(int id);

    public void deleteMealTo(int id);

    public List<Meal> getAllMeal();

}
