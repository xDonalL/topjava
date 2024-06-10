package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private final List<Meal> meals = MealsUtil.meals;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirecting to meal");
        Map<LocalDate, Integer> caloriesSumPerDay = new HashMap<>();
        for (Meal meal : meals) {
            caloriesSumPerDay.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }
        List<MealTo> mealsTo = meals.stream()
                .map(meal ->
                        new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                                caloriesSumPerDay.get(meal.getDateTime().toLocalDate())>MealsUtil.CALORIES_PER_DAY))
                .collect(Collectors.toList());
        request.setAttribute("formater", TimeUtil.FORMATER);
        request.setAttribute("meals", mealsTo);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
