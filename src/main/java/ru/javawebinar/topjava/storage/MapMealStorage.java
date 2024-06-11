package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.*;

public class MapMealStorage implements MealStorage {
    private final Map<Integer, Meal> meals = Collections.synchronizedMap(new HashMap<>());
    private static Integer id = 0;

    public MapMealStorage(List<Meal> meals) {
        for (Meal meal : meals) {
            save(meal);
        }
    }

    public MapMealStorage() {
    }

    @Override
    public void delete(Integer id) {
        meals.remove(id);
    }

    @Override
    public Meal get(Integer id) {
        return meals.get(id);
    }

    @Override
    public void update(Meal meal) {
        Meal existingMeal = get(meal.getId());
        if (existingMeal != null) {
            existingMeal.setDateTime(meal.getDateTime());
            existingMeal.setDescription(meal.getDescription());
            existingMeal.setCalories(meal.getCalories());
        }
    }

    @Override
    public void save(Meal meal) {
        meal.setId(id);
        meals.put(id++, meal);
    }

    @Override
    public List<Meal> getAllMeals() {
        return new ArrayList<>(meals.values());
    }
}
