package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MapMealStorage;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealStorage mealStorage;
    @Override
    public void init() throws ServletException {
        super.init();
        mealStorage = new MapMealStorage(MealsUtil.meals);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirecting to meal");
        String action = request.getParameter("action");
        if (action == null) {
            Map<LocalDate, Integer> caloriesSumPerDay = new HashMap<>();
            for (Meal meal : mealStorage.getAllMeals()) {
                caloriesSumPerDay.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
            }
            List<MealTo> mealsTo = mealStorage.getAllMeals().stream()
                    .map(meal ->
                            new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                                    caloriesSumPerDay.get(meal.getDateTime().toLocalDate()) > MealsUtil.CALORIES_PER_DAY))
                    .collect(Collectors.toList());
            request.setAttribute("formatter", TimeUtil.FORMATTER);
            request.setAttribute("meals", mealsTo);
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
        Meal meal = null;
        String id = request.getParameter("id");
        if (action != null) {
            switch (action) {
                case "delete":
                    mealStorage.delete(Integer.parseInt(id));
                    response.sendRedirect("meals");
                    break;
                case "add":
                    request.setAttribute("meal", meal);
                    request.setAttribute("title", "Add Meal");
                    request.getRequestDispatcher("edit.jsp").forward(request, response);
                    break;
                case "update":
                    meal = mealStorage.get(Integer.parseInt(id));
                    request.setAttribute("meal", meal);
                    request.setAttribute("title", "Edit Meal");
                    request.getRequestDispatcher("edit.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String localDate = request.getParameter("localDate");
        String id = request.getParameter("id");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        if (id.isEmpty()) {
            mealStorage.save(new Meal(LocalDateTime.parse(localDate), description, Integer.parseInt(calories)));
        } else {
            Meal meal = new Meal(LocalDateTime.parse(localDate), description, Integer.parseInt(calories));
            meal.setId(Integer.parseInt(id));
            mealStorage.update(meal);
        }
        response.sendRedirect("meals");
    }
}
