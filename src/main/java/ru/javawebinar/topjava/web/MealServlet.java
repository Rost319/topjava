package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private Map<Integer, Meal> meals;


    @Override
    public void init() throws ServletException {
        final Object meals = getServletContext().getAttribute("meals");

        if(meals == null || !(meals instanceof ConcurrentHashMap)){
            throw new IllegalStateException("You' re repo does not initialize!");
        }else {
            this.meals = (ConcurrentHashMap<Integer, Meal>) meals;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        List<MealTo> mealToList = MealsUtil.getAllMealTo(meals.values());

        request.setAttribute("mealToList", mealToList);

        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

}
