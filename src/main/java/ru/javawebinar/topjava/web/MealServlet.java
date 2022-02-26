package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private MealService mealService = new MealServiceImpl();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        List<MealTo> mealToList = mealService.getAllMealTo();

        request.setAttribute("mealToList", mealToList);

        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("update to meals");

        req.setCharacterEncoding("UTF8");

        final String id = req.getParameter("id");
        final String dateTime = req.getParameter("dateTime");
        final String description = req.getParameter("description");
        final String calories = req.getParameter("calories");


        for (Meal meal : mealService.getAllMeal()){
            if(meal.getId() == Integer.valueOf(id)){
                meal.setDateTime(LocalDateTime.parse(dateTime));
                meal.setDescription(description);
                meal.setCalories(Integer.valueOf(calories));
            }
        }

        doGet(req, resp);
    }
}
