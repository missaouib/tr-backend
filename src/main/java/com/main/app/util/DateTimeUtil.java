package com.main.app.util;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.main.app.static_data.Messages.TIME_WRONG_FORMAT;

public class DateTimeUtil {

    public static Instant parseDate(String data) {

        Instant i = null;
        try {
            i = Instant.parse(data);
        }
        catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, TIME_WRONG_FORMAT);
        }

        return i;
    }

    // @unit - Calendar.MINUTE, Calendar.HOUR, Calendar.SECONDS
    public static Instant addTime(int unit, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(unit, amount);
        return calendar.getTime().toInstant();
    }

    public static Instant addTime(Instant start, ChronoUnit unit, int amount) {
        return start.plus(amount, unit);
    }

    public static Timestamp getTimestamp(Instant date) {
        return Timestamp.from(date);
    }

    public static Long getLongTime(Instant date) {
        return getTimestamp(date).getTime();
    }


    public static int getDifferenceBetweenTwoDates(Instant date1, Instant date2) {

        long diffInMillies = Math.abs(date2.toEpochMilli() - date1.toEpochMilli());
        return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

    }

    public static Timestamp getMonthStartTimestamp(int year, int month) {
        Calendar calendar = setCalendarToDefault();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        return getTimestamp(calendar.toInstant());
    }

    public static Timestamp getMonthEndTimestamp(int year, int month) {
        Calendar calendar = setCalendarToDefault();
        month += 1;
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        return getTimestamp(calendar.toInstant());
    }

    private static Calendar setCalendarToDefault() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static Date getDateFromInstant(Instant date) {
        return Date.from(date);
    }

    public static int getMonthFromDate(Instant date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDateFromInstant(date));
        return calendar.get(Calendar.MONTH);
    }

    public static int getYearFromDate(Instant date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDateFromInstant(date));
        return calendar.get(Calendar.YEAR);
    }

    public static Instant addMonthToDate(Instant date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDateFromInstant(date));
        int currentMonth = calendar.get(Calendar.MONTH);
        int newMonth = (currentMonth + amount) % 12;
        calendar.set(Calendar.MONTH, newMonth);

        // if newMonth != real newMonth
        if ((calendar.get(Calendar.MONTH)) != newMonth ) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, newMonth);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        }

        if ((currentMonth + amount) > 11) {
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
        }
        return calendar.toInstant();
    }

    public static Instant addYearToDate(Instant date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDateFromInstant(date));
        int currentYear = calendar.get(Calendar.YEAR);
        calendar.set(Calendar.YEAR, currentYear + amount);
        return calendar.toInstant();
    }

    public static Calendar setTimeToMidnight(Calendar calendar) {
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }

    public static long getYesterdayStart() {
        Calendar calendar = Calendar.getInstance();
        setTimeToMidnight(calendar);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        return addTime(calendar.toInstant(), ChronoUnit.DAYS, -1).toEpochMilli();
    }

    public static long getTwoDaysAgoStart() {
        Calendar calendar = Calendar.getInstance();
        setTimeToMidnight(calendar);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        return addTime(calendar.toInstant(), ChronoUnit.DAYS, -2).toEpochMilli();
    }

    public static long getTodayStart() {
        Calendar calendar = Calendar.getInstance();
        setTimeToMidnight(calendar);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        return calendar.toInstant().toEpochMilli();
    }

    public static long getThisWeekStart() {
        Calendar calendar = Calendar.getInstance();
        setTimeToMidnight(calendar);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.DAY_OF_WEEK,0);
        return calendar.toInstant().toEpochMilli();
    }

    public static long getLastWeekStart() {
        Calendar calendar = Calendar.getInstance();
        setTimeToMidnight(calendar);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.DAY_OF_WEEK,0);
        addTime(calendar.toInstant(), ChronoUnit.DAYS, -7);
        return calendar.toInstant().toEpochMilli();
    }

}
