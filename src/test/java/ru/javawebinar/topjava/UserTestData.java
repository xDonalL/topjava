package ru.javawebinar.topjava;

import org.assertj.core.api.Assertions;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int GUEST_ID = START_SEQ + 2;
    public static final int NOT_FOUND = 10;

    public static final User user = new User(USER_ID, "User", "user@yandex.ru", "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN);
    public static final User guest = new User(GUEST_ID, "Guest", "guest@gmail.com", "guest");

    public static final Meal meal = new Meal(LocalDateTime.of(2020, 6,6,12,20),"meal", 500);

    public static User getNewUser() {
        return new User(null, "New", "new@gmail.com", "newPass", 1555, false, new Date(), Collections.singleton(Role.USER));
    }

    public static Meal getNewMeal() {
        return new Meal(LocalDateTime.of(2020, 10,10,7,30), "Завтрак", 500);
    }

    public static User getUpdated() {
        User updated = new User(user);
        updated.setEmail("update@gmail.com");
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(330);
        updated.setPassword("newPass");
        updated.setEnabled(false);
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }

    public static Meal getUpdatedMeal() {
        Meal updated = new Meal(meal);
        updated.setDescription("UpdatedMeal");
        updated.setCalories(1000);
        return updated;
    }

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("registered", "roles").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatchUser(actual, Arrays.asList(expected));
    }

    public static void assertMatchUser(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getDateTime()).isEqualTo(expected.getDateTime());
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
        assertThat(actual.getCalories()).isEqualTo(expected.getCalories());
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatchMeal(actual, Arrays.asList(expected));
    }

    public static void assertMatchMeal(Iterable<Meal> actual, Iterable<Meal> expected) {
        Assertions.assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
