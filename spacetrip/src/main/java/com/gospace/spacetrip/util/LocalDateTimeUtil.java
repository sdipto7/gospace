package com.gospace.spacetrip.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author rumidipto
 * @since 3/18/24
 */
public class LocalDateTimeUtil {

    public static String getFormattedDateTimeDifference(LocalDateTime fromDate, LocalDateTime toDate) {
        LocalDateTime localDateTime = LocalDateTime.from(fromDate);

        long years = localDateTime.until(toDate, ChronoUnit.YEARS);
        localDateTime = localDateTime.plusYears(years);

        long months = localDateTime.until(toDate, ChronoUnit.MONTHS);
        localDateTime = localDateTime.plusMonths(months);

        long days = localDateTime.until(toDate, ChronoUnit.DAYS);
        localDateTime = localDateTime.plusDays(days);

        long hours = localDateTime.until(toDate, ChronoUnit.HOURS);
        localDateTime = localDateTime.plusHours(hours);

        long minutes = localDateTime.until(toDate, ChronoUnit.MINUTES);
        localDateTime = localDateTime.plusMinutes(minutes);

        long seconds = localDateTime.until(toDate, ChronoUnit.SECONDS);

        return new StringBuilder()
                .append(String.format("%s years ", years))
                .append(String.format("%s months ", months))
                .append(String.format("%s days ", days))
                .append(String.format("%s hours ", hours))
                .append(String.format("%s minutes ", minutes))
                .append(String.format("%s seconds", seconds))
                .toString();
    }
}