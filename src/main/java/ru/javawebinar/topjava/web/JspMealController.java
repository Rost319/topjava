package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;


@Controller
public class JspMealController {

    MealService mealService;

    @Autowired
    public JspMealController(MealService mealService) {
        this.mealService = mealService;
    }


    @GetMapping("/meals")
    public String getMeals(Model model, HttpServletRequest request){
        int userId = SecurityUtil.authUserId();
        List<MealTo> list = MealsUtil.getTos(mealService.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
        model.addAttribute("meals", list);
        return "meals";
    }

    @GetMapping("/mealsBetween")
    public String getMealsBetween(Model model, HttpServletRequest request){
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        int userId = SecurityUtil.authUserId();
        List<Meal> mealsDateFiltered = mealService.getBetweenDates(startDate, endDate, userId);
        List<MealTo> resultList = MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
        model.addAttribute("meals", resultList);
        return "meals";
    }

    @GetMapping("/deleteMeal")
    public String deleteMeal(@RequestParam("mealId") int id){
        int userId = SecurityUtil.authUserId();
        mealService.delete(id, userId);
        return "redirect:meals";
    }

    @GetMapping("/updateMeal")
    public String updateMeal(@RequestParam("mealId") int id, Model model){
        int userId = SecurityUtil.authUserId();
        Meal meal = mealService.get(id, userId);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/addNewMeal")
    public String addNewMeal(Model model){
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/saveMeal")
    public String saveMeal(@ModelAttribute("employee") Meal meal){
        int userId = SecurityUtil.authUserId();

        if (StringUtils.isEmpty(meal.getId())) {
            checkNew(meal);
            mealService.create(meal, userId);
        } else {
            assureIdConsistent(meal, meal.getId());
            mealService.update(meal, userId);
        }
        return "redirect:meals";
    }


}
