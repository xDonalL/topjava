package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(2500, 1, 1);

    private static final LocalTime MIN_TIME = LocalTime.of(0, 0, 0);
    private static final LocalTime MAX_TIME = LocalTime.of(23, 59, 59);

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static boolean isBetweenHalfOpen(LocalDateTime lt, LocalDateTime startTime, LocalDateTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static boolean isBetweenHalfOpen(LocalDate lt, LocalDate startDate, LocalDate endDate) {
        return lt.compareTo(startDate) >= 0 && lt.compareTo(endDate) <= 0;
    }

    public static LocalDateTime parseDateTime(String date, String time) {
        return LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(time));
    }

    public static LocalDate checkStartDate(LocalDate localDate) {
        return localDate == null ? MIN_DATE : localDate;
    }

    public static LocalDate checkEndDate(LocalDate localDate) {
        return localDate == null ? MAX_DATE : localDate;
    }

    public static LocalTime checkStartTime(LocalTime localTime) {
        return localTime == null ? MIN_TIME : localTime;
    }

    public static LocalTime checkEndTime(LocalTime localTime) {
        return localTime == null ? MAX_TIME : localTime;
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

