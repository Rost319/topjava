package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.slf4j.LoggerFactory.getLogger;

public class UpdateMealServlet extends HttpServlet {
    private static final Logger log = getLogger(UpdateMealServlet.class);
    private Map<Integer, Meal> meals;

    @Override
    public void init() throws ServletException {

        final Object meals = getServletContext().getAttribute("meals");

        if (meals == null || !(meals instanceof ConcurrentHashMap)) {

            throw new IllegalStateException("You're repo does not initialize!");
        } else {

            this.meals = (ConcurrentHashMap<Integer, Meal>) meals;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        log.debug("update meal");

        final String id = req.getParameter("id");
        final String dateTime = req.getParameter("dateTime");
        final String description = req.getParameter("description");
        final String calories = req.getParameter("calories");

        final Meal meal = meals.get(Integer.parseInt(id));
        meal.setDateTime(LocalDateTime.parse(dateTime));
        meal.setDescription(description);
        meal.setCalories(Integer.valueOf(calories));

        resp.sendRedirect(req.getContextPath() + "/meals");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        final String id = req.getParameter("id");


        final Meal meal = meals.get(Integer.valueOf(id));
        req.setAttribute("meal", meal);

        req.getRequestDispatcher("update-meals.jsp")
                .forward(req, resp);
    }
}
