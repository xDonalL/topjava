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
import java.util.stream.Collectors;

import static java.time.LocalDateTime.*;
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
            List<MealTo> mealsTo = MealsUtil.filteredByStreams(mealStorage.getAllMeals(), null, null, 2000);
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
                    log.debug("meal removed");
                    break;
                case "add":
                    request.setAttribute("meal", meal);
                    request.setAttribute("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
                    request.setAttribute("title", "Add Meal");
                    request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
                    break;
                case "update":
                    meal = mealStorage.get(Integer.parseInt(id));
                    request.setAttribute("meal", meal);
                    request.setAttribute("time", meal.getDateTime());
                    request.setAttribute("title", "Edit Meal");
                    request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
                    break;
                default:
                    response.sendRedirect("meals");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String localDate = request.getParameter("localDate");
        String id = request.getParameter("id");
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        if (id.isEmpty()) {
            mealStorage.create(new Meal(parse(localDate), description, calories));
            log.debug("saved to meal");
        } else {
            Meal meal = new Meal(parse(localDate), description, calories);
            meal.setId(Integer.parseInt(id));
            mealStorage.update(meal);
            log.debug("update to meal");
        }
        response.sendRedirect("meals");
    }
}
