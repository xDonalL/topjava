package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealStorage {
    public void save(Meal meal);

    public void update(Meal meal);

    public void delete(Integer id);

    public Meal get(Integer id);

    public List<Meal> getAllMeals();
}
