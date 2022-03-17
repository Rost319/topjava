package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(100002, 100000);
        assertMatch(meal, MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        Meal meal = service.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFoundToNotUser() {
        Meal meal = service.get(100002, ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(100008, ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID), MEAL8);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(1, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFoundToNotUser() {
        service.delete(100003, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> allBetweenUser = service.getBetweenDates(LocalDate.of(2015, Month.MAY,29),
                LocalDate.of(2015, Month.MAY, 30), USER_ID);
        assertMatch(allBetweenUser, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void getAll() {
        List<Meal> allUser = service.getAll(USER_ID);
        List<Meal> allAdmin = service.getAll(ADMIN_ID);
        assertMatch(allAdmin, MEAL8, MEAL7);
        assertMatch(allUser, MEAL6, MEAL5, MEAL4, MEAL3,  MEAL2,  MEAL1);
    }

    @Test
    public void update() {
        Meal update = new Meal(MEAL1);
        update.setCalories(700);
        update.setDescription("Завтрак!!!");
        update.setDateTime(LocalDateTime.of(2015, Month.MAY, 31,15,0));
        service.update(update, USER_ID);
        assertMatch(service.get(100002, USER_ID), update);
    }

    @Test(expected = NotFoundException.class)
    public void updateToNotUser() {
        Meal update = new Meal(MEAL1);
        update.setCalories(700);
        update.setDescription("Завтрак!!!");
        update.setDateTime(LocalDateTime.of(2015, Month.MAY, 31,15,0));
        service.update(update, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2015, Month.JUNE, 1, 9, 0), "Админ завтрак", 800);
        Meal created = service.create(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(ADMIN_ID), MEAL8, MEAL7, newMeal);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDateCreate(){
        service.create(new Meal(null, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ", 800), ADMIN_ID);
    }

}