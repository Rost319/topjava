package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but not present in storage
        if(meal.getUserId() != userId){
            return null;
        }
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);

    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);
        if(repository.get(id).getUserId() != userId){
            return false;
        }
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {}", id);
        if(repository.get(id).getUserId() != userId){
            return null;
        }
        return repository.get(id);
    }


    @Override
    public Collection<Meal> getAll(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId) {
        log.info("getAll");
        if(startDate == null) startDate = LocalDate.MIN;
        if(endDate == null) endDate = LocalDate.MAX;
        if(startTime == null) startTime = LocalTime.MIN;
        if(endTime == null) endTime = LocalTime.MAX;

        LocalDate finalStartDate = startDate;
        LocalDate finalEndDate = endDate;
        LocalTime finalStartTime = startTime;
        LocalTime finalEndTime = endTime;

        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), finalStartDate, finalEndDate))
                .filter(meal -> DateTimeUtil.isBetween(meal.getTime(), finalStartTime, finalEndTime))
                .sorted((m1,m2) -> m2.getDate().compareTo(m1.getDate()))
                .collect(Collectors.toList());
    }
}

