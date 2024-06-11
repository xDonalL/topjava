package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealStorage {
    public Meal create(Meal meal);

    public Meal update(Meal meal);

    public void delete(int id);

    public Meal get(int id);

    public List<Meal> getAllMeals();
}
