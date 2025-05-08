package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal save(Meal meal) {
        Integer userId = SecurityUtil.authUserId();
        log.info("save {}, for user {}", meal, userId);
        return service.save(meal, userId);
    }

    public void delete(int id) {
        Integer userId = SecurityUtil.authUserId();
        log.info("delete {}, for user {}", id, userId);
        service.delete(id, userId);
    }

    public Meal get(int id) {
        Integer userId = SecurityUtil.authUserId();
        log.info("get {}, for user {}", id, userId);
        return service.get(id, userId);
    }

    public Collection<Meal> getAll() {
        Integer userId = SecurityUtil.authUserId();
        log.info("getAll, for user {}", userId);
        return service.getAll(userId);
    }

    public void update(Meal meal) {
        Integer userId = SecurityUtil.authUserId();
        log.info("update {}, for user {}", meal, userId);
        service.update(meal, userId);
    }
}