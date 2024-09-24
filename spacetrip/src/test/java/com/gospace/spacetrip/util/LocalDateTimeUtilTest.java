package com.gospace.spacetrip.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.gospace.spacetrip.util.LocalDateTimeUtil.getFormattedDateTimeDifference;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author rumidipto
 * @since 9/24/24
 */
public class LocalDateTimeUtilTest {

    @Test
    void getFormattedDateTimeDifferenceTest() {
        LocalDateTime fromDate = LocalDateTime.of(1997, 8, 24, 1, 30, 50);
        LocalDateTime toDate = LocalDateTime.of(2027, 9, 25, 2, 31, 51);

        String expectedOutput = "30 years 1 months 1 days 1 hours 1 minutes 1 seconds";
        assertEquals(expectedOutput, getFormattedDateTimeDifference(fromDate, toDate));
    }
}
