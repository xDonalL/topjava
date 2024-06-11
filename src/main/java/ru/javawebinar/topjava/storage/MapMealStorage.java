package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MapMealStorage implements MealStorage {
    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger();

    public MapMealStorage(List<Meal> meals) {
        meals.forEach(this::create);
    }

    @Override
    public void delete(int id) {
        meals.remove(id);
    }

    @Override
    public Meal get(int id) {
        return meals.get(id);
    }

    @Override
    public Meal update(Meal meal) {
        meals.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal create(Meal meal) {
        int id = nextId.incrementAndGet();
        meal.setId(id);
        meals.put(id, meal);
        return meal;
    }

    @Override
    public List<Meal> getAllMeals() {
        return new ArrayList<>(meals.values());
    }
}
