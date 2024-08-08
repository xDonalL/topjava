package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.*;
import static ru.javawebinar.topjava.util.MealsUtil.getFilteredTimeTos;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {

    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);
    @Autowired
    private MealService service;

    public Meal create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        checkNew(meal);
        log.debug("create meal {} user {}", meal, userId);
        return service.create(meal, userId);
    }

    public void update(Meal meal, int id) {
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(meal, id);
        log.debug("update meal {} user {}", meal, userId);
        service.update(meal, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meal {} user {}", id, userId);
        service.delete(id, userId);
    }

    public Meal get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get meal {} user {}", id, userId);
        return service.get(id, userId);
    }

    public List<MealTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("get all {}", userId);
        return MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getBetweenDateTime(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("get filter user {}", userId);
        return getFilteredTimeTos(service.getFilterDate(userId, checkStartDate(startDate), checkEndDate(endDate)),
                SecurityUtil.authUserCaloriesPerDay(), checkStartTime(startTime), checkEndTime(endTime));
    }
}