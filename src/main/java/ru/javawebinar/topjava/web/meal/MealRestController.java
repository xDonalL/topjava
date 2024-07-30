package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.*;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public void create(Meal meal) {
        service.create(meal);
    }

    public void update(Meal meal) {
        service.update(meal);
    }

    public void delete(int id) {
        service.delete(id, SecurityUtil.authUserId());
    }

    public MealTo get(int id) {
        List<MealTo> list = getAll();
        Meal meal = service.get(id, SecurityUtil.authUserId());
        return list.stream().
                filter(mealTo -> meal.getId().equals(mealTo.getId()))
                .findFirst()
                .orElse(null);
    }

    public List<MealTo> getAll() {
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> getSortDateTime(LocalDateTime start, LocalDateTime end) {
        return MealsUtil.getFilteredDateTimeTos(service.getAll(SecurityUtil.authUserId()), DEFAULT_CALORIES_PER_DAY, start, end);
    }

    public List<MealTo> getSortTime(LocalTime start, LocalTime end) {
        return MealsUtil.getFilteredTimeTos(service.getAll(SecurityUtil.authUserId()), DEFAULT_CALORIES_PER_DAY, start, end);
    }

    public List<MealTo> getBetweenDateTime(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        List<Meal> meals = getFilteredDateTimeTos(service.getAll(SecurityUtil.authUserId()), DateTimeUtil.checkStartDate(startDate), DateTimeUtil.checkEndDate(endDate));
        return getFilteredTimeTos(meals, DEFAULT_CALORIES_PER_DAY, DateTimeUtil.checkStartTime(startTime), DateTimeUtil.checkEndTime(endTime));
    }
}