package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.slf4j.LoggerFactory.getLogger;

public class DeleteMealServlet extends HttpServlet {
    private static final Logger log = getLogger(DeleteMealServlet.class);
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

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        log.debug("delete meal");


            meals.remove(Integer.valueOf(req.getParameter("id")));


        resp.sendRedirect(req.getContextPath() + "/meals");
    }
}
