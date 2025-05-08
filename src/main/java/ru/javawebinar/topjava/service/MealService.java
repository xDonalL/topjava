package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal save(Meal meal, Integer userId) {
        return repository.save(meal, userId);
    }

    public void delete(int id, Integer userId) {
        checkNotFound(repository.delete(id, userId), id);
    }

    public Meal get(int id, Integer userId) {
        return checkNotFound(repository.get(id, userId), id);
    }

    public Collection<Meal> getAll(Integer userId) {
        return repository.getAll(userId);
    }

    public void update(Meal meal, Integer userId) {
        checkNotFound(repository.save(meal, userId), meal.getId());
    }
}