package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }


    public Collection<MealTo> getAll(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("getAll");
        int userId = SecurityUtil.authUserId();
        Collection<Meal> collection = service.getAll(startDate, endDate, startTime, endTime, userId);

        return MealsUtil.getTos(collection, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        int userId = SecurityUtil.authUserId();
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        int userId = SecurityUtil.authUserId();
        return service.create(meal, userId);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        int userId = SecurityUtil.authUserId();
        service.delete(id, userId);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        int userId = SecurityUtil.authUserId();
        service.update(meal, userId);
    }

}