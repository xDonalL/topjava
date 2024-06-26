package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

import static junit.framework.TestCase.assertNull;
import static ru.javawebinar.topjava.UserTestData.*;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(UserTestData.meal.getId(), USER_ID);
        assertMatch(meal, UserTestData.meal);
    }

    @Test
    public void delete() {
        service.delete(meal.getId(), USER_ID);
        assertNull(service.get(meal.getId(), USER_ID));
    }

    @Test
    public void create() {
        Meal newMeal = getNewMeal();
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(created, newMeal);
        assertMatch(service.get(created.getId(), USER_ID), newMeal);
    }

    @Test
    public void update() {
        Meal updated = getUpdatedMeal();
        service.update(updated, USER_ID);
        assertMatch(service.get(meal.getId(), USER_ID), updated);
    }

    @Test
    public void getAll() {
        List<Meal> allMeals = service.getAll(USER_ID);
        assertMatch(allMeals, UserTestData.meal);
    }

    @Test
    public void getBetweenInclusive() {
        LocalDateTime startDateTime = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2020, 1, 2, 0, 0);
        List<Meal> meals = service.getBetweenInclusive(startDateTime.toLocalDate(), endDateTime.toLocalDate(), USER_ID);
        assertMatch(meals, UserTestData.meal);
    }
}