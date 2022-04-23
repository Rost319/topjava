package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;


class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Autowired
    private MealService mealService;

    @Test
    void getAll() throws Exception {
        List<Meal> list = MEALS.stream().collect(Collectors.toList());
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk());

    }

    @Test
    void get() {
    }

    @Test
    void createWithLocation() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void getBetween() {
    }
}