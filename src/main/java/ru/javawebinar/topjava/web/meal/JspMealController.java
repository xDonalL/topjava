package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/meals")
public class JspMealController {

    @Autowired
    private MealService service;

    @GetMapping
    public String getAll(Model model) {
        int userId = SecurityUtil.authUserId();
        model.addAttribute("meals", service.getAll(userId));
        return "meals";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        int userId = SecurityUtil.authUserId();
        service.delete(id, userId);
        redirectAttributes.addFlashAttribute("message", "Meal deleted");
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String create(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") int id, Model model) {
        int userId = SecurityUtil.authUserId();
        model.addAttribute("meal", service.get(id, userId));
        return "mealForm";
    }

    @PostMapping
    public String saveOrUpdate(@RequestParam(value = "id", required = false) Integer id,
                               @RequestParam("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                               @RequestParam("description") String description,
                               @RequestParam("calories") int calories) {
        int userId = SecurityUtil.authUserId();
        Meal meal = new Meal(dateTime, description, calories);
        if (id != null) {
            service.update(meal, id);
        } else {
            service.create(meal, userId);
        }
        return "redirect:/meals";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                         @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                         @RequestParam(value = "startTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                         @RequestParam(value = "endTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
                         Model model) {
        int userId = SecurityUtil.authUserId();
        List<MealTo> meals = MealsUtil.getFilteredTos(service.getBetweenInclusive(startDate, endDate, userId),
                SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
        model.addAttribute("meals", meals);
        return "meals";
    }
}
