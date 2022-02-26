package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealTo {
    private final int id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

//    private final Supplier<Boolean> excess;
//    private final AtomicBoolean excess;
    private final boolean excess;

    public MealTo(int id, LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

//    public Boolean getExcess() {
//        return excess.get();
//    }


    public int getId() {
        return id;
    }

    public String getDateTime() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(dateTime);
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExcess() {
        return excess;
    }

    @Override
    public String toString() {
        return "UserMealWithExcess{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
}
