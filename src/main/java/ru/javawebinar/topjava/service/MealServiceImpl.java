package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

public class MealServiceImpl implements MealService {

    private MealsUtil mealsUtil = new MealsUtil();

    @Override
    public List<MealTo> getAllMealTo() {
        return mealsUtil.getAllMealTo();
    }

    @Override
    public void saveMealTo(MealTo mealTo) {

    }

    @Override
    public MealTo getMealTo(int id) {
        return null;
    }

    @Override
    public void deleteMealTo(int id) {

    }
}
