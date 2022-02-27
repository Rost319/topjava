package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AddMealServlet extends HttpServlet {
    private Map<Integer, Meal> meals;

    private AtomicInteger id;

    @Override
    public void init() throws ServletException {

        final Object meals = getServletContext().getAttribute("meals");

        if (meals == null || !(meals instanceof ConcurrentHashMap)) {

            throw new IllegalStateException("You're repo does not initialize!");
        } else {

            this.meals = (ConcurrentHashMap<Integer, Meal>) meals;
        }

        id = new AtomicInteger(7);

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");


        final String dateTime = req.getParameter("dateTime");
        final String description = req.getParameter("description");
        final String calories = req.getParameter("calories");

        final Meal meal = new Meal(LocalDateTime.parse(dateTime), description, Integer.valueOf(calories));
        final int id = this.id.getAndIncrement();

        meals.put(id, meal);


        resp.sendRedirect(req.getContextPath() + "/meals");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("add-meals.jsp")
                .forward(req, resp);
    }
}
