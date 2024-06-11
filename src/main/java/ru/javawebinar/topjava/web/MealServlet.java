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
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.LocalDateTime.*;
import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealStorage mealStorage;

    @Override
    public void init() throws ServletException {
        mealStorage = new MapMealStorage(MealsUtil.meals);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            log.debug("redirecting to meals");
            List<MealTo> mealsTo = MealsUtil.filteredByStreams(mealStorage.getAllMeals());
            request.setAttribute("formatter", TimeUtil.FORMATTER);
            request.setAttribute("meals", mealsTo);
            request.getRequestDispatcher("meals.jsp").forward(request, response);
            log.debug("redirect to meals");
        }
        Meal meal;
        String id = request.getParameter("id");
        if (action != null) {
            switch (action) {
                case "delete":
                    log.debug("redirecting to delete meal");
                    mealStorage.delete(Integer.parseInt(id));
                    response.sendRedirect("meals");
                    log.debug("meal removed");
                    break;
                case "add":
                    log.debug("redirecting to add meal");
                    meal = new Meal();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                    String formattedDateTime = LocalDateTime.now().format(formatter);
                    meal.setDateTime(LocalDateTime.parse(formattedDateTime, formatter));
                    request.setAttribute("meal", meal);
                    request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
                    break;
                case "update":
                    log.debug("redirecting to update meal");
                    meal = mealStorage.get(Integer.parseInt(id));
                    request.setAttribute("meal", meal);
                    request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
                    break;
                default:
                    log.debug("redirecting to meals");
                    response.sendRedirect("meals");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirecting doPost");
        request.setCharacterEncoding("UTF-8");
        String localDate = request.getParameter("localDate");
        String id = request.getParameter("id");
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(parse(localDate), description, calories);
        if (id.isEmpty()) {
            mealStorage.create(meal);
            log.debug("saved to meal");
        } else {
            meal.setId(Integer.parseInt(id));
            mealStorage.update(meal);
            log.debug("update to meal");
        }
        response.sendRedirect("meals");
    }
}
