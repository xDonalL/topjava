package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseTime;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    //   private MealRepository repository;
    private ConfigurableApplicationContext appCtx;
    private MealRestController mealController;

    @Override
    public void init() {
        //        repository = new InMemoryMealRepository();
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealController = appCtx.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        appCtx.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")), SecurityUtil.authUserId());

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
//        repository.save(meal, SecurityUtil.authUserId());
        mealController.create(meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete id={}", id);
                mealController.delete(id);
//                repository.delete(id, SecurityUtil.authUserId());
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal;
                if ("create".equals(action)) {
                    meal = new Meal(null, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, SecurityUtil.authUserId());
                } else {
                    final MealTo mealTo = (mealController.get(getId(request)));
                    meal = new Meal(mealTo.getId(), mealTo.getDateTime(), mealTo.getDescription(), mealTo.getCalories(), SecurityUtil.authUserId());
                }
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "sort":
                log.info("sort");
                LocalDate startDate = parseDate(request.getParameter("fromDate"));
                LocalTime startTime = parseTime(request.getParameter("fromTime"));
                LocalDate endDate = parseDate(request.getParameter("toDate"));
                LocalTime endTime = parseTime(request.getParameter("toTime"));
                request.setAttribute("meals", mealController.getBetweenDateTime(startDate, startTime, endDate , endTime));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals",
                        mealController.getAll());
//                        MealsUtil.getTos(repository.getAll(SecurityUtil.authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
