package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static boolean isBetweenHalfOpen(LocalDate lt, LocalDate startDate, LocalDate endDate) {
        return lt.compareTo(startDate) >= 0 && lt.compareTo(endDate) < 0;
    }

    public static LocalDate checkStartDate(LocalDate localDate) {
        return localDate == null ? LocalDate.MIN : localDate;
    }

    public static LocalDate checkEndDate(LocalDate localDate) {
        return localDate == null ? LocalDate.MAX : localDate;
    }

    public static LocalTime checkStartTime(LocalTime localTime) {
        return localTime == null ? LocalTime.MIN : localTime;
    }

    public static LocalTime checkEndTime(LocalTime localTime) {
        return localTime == null ? LocalTime.MAX : localTime;
    }

    public static LocalDate parseDate(String date) {
        return date.isEmpty() ? null : LocalDate.parse(date);
    }

    public static LocalTime parseTime(String time) {
        return time.isEmpty() ? null : LocalTime.parse(time);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

