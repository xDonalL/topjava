package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private final Map<Integer, Map<Integer, Meal>> mealsMap = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        log.info("save {}, with user id {}", meal, userId);
        Map<Integer, Meal> meals = mealsMap.computeIfAbsent(userId, uId -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        }
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

        @Override
        public boolean delete ( int id, Integer userId){
            log.info("delete {}, with user id {}", id, userId);
            Map<Integer, Meal> meals = mealsMap.get(userId);
            return meals != null && meals.remove(id) != null;
        }

        @Override
        public Meal get ( int id, Integer userId){
            log.info("get {}, with user id {}", id, userId);
            return mealsMap.get(userId).values().stream()
                    .filter(meal -> meal.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public Collection<Meal> getAll (Integer userId){
            log.info("getAll, with user id {}", userId);
            Map<Integer, Meal> meals = mealsMap.get(userId);
            return CollectionUtils.isEmpty(meals) ? Collections.emptyList() :
                    meals.values().stream()
                            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                            .collect(Collectors.toList());
        }
    }

