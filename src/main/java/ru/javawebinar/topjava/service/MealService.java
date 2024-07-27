package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal) {
        ValidationUtil.checkAuthUser(meal.getUserId());
        return repository.save(meal, meal.getUserId());
    }

    public void delete(int id, int userId) {
        ValidationUtil.checkAuthUser(userId);
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        ValidationUtil.checkAuthUser(userId);
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<Meal> getAll(int userId) {
        ValidationUtil.checkAuthUser(userId);
        return repository.getAll(userId);
    }

    public void update(Meal meal) {
        ValidationUtil.checkAuthUser(meal.getUserId());
        checkNotFoundWithId(repository.save(meal, meal.getUserId()), meal.getId());
    }
}