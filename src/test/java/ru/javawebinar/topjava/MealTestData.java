package ru.javawebinar.topjava;

import org.assertj.core.api.Assertions;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int USER_MEAL_ID = START_SEQ + 3;

    public static final Meal meal1 = new Meal(USER_MEAL_ID, LocalDateTime.of(2020, 6, 12, 7, 0), "Завтрак", 500);
    public static final Meal meal2 = new Meal(START_SEQ + 4, LocalDateTime.of(2020, 6, 12, 13, 0), "Обед", 1000);
    public static final Meal meal3 = new Meal(START_SEQ + 5, LocalDateTime.of(2020, 6, 12, 18, 0), "Ужин", 600);
    public static final Meal adminMeal1 = new Meal(START_SEQ + 6, LocalDateTime.of(2020, 6, 12, 8, 0), "Админ завтрак", 500);
    public static final Meal adminMeal2 = new Meal(START_SEQ + 7, LocalDateTime.of(2020, 6, 12, 13, 0), "Админ обед", 700);


    public static final List<Meal> meals = Arrays.asList(meal3, meal2, meal1);


    public static Meal getNewMeal() {
        return new Meal(LocalDateTime.of(2020, 1, 1, 7, 0), "newMeal", 1000);
    }

    public static Meal getUpdatedMealUser() {
        Meal updated = new Meal(meal1);
        updated.setDateTime(LocalDateTime.of(2020, 2, 1, 7, 5));
        updated.setDescription("UpdatedMeal");
        updated.setCalories(1000);
        return updated;
    }

    public static Meal getUpdatedMealAdmin() {
        Meal updated = new Meal();
        updated.setDescription("UpdatedMeal");
        updated.setCalories(1000);
        return updated;
    }

    public static void assertMatchMeal(Meal actual, Meal expected) {
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getDateTime()).isEqualTo(expected.getDateTime());
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
        assertThat(actual.getCalories()).isEqualTo(expected.getCalories());
    }

    public static void assertMatchMeal(Iterable<Meal> actual, Meal... expected) {
        assertMatchMeal(actual, Arrays.asList(expected));
    }

    public static void assertMatchMeal(Iterable<Meal> actual, Iterable<Meal> expected) {
        Assertions.assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
