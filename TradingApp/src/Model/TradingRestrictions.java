package Model;

import java.time.*;
import java.util.*;

public class TradingRestrictions {
    private static LocalDate startDate;
    private LocalDate endDate;
    private int initialTradingHoursDays;
    
    // Set the trading period with start date, end date, and initial trading hours duration
    public void setTradingPeriod(LocalDate startDate, LocalDate endDate, int initialTradingHoursDays) {
        TradingRestrictions.startDate = startDate;
        this.endDate = endDate;
        this.initialTradingHoursDays = initialTradingHoursDays;
    }



    // Check if a given date and time is within the trading hours
    public boolean isWithinTradingHours(LocalDateTime dateTime) {
        LocalDate currentDate = dateTime.toLocalDate();

        if (currentDate.isBefore(startDate) || currentDate.isAfter(endDate)) {
            return false; // Trading period has not started or has ended
        } else {
            return isRegularTradingPeriod(dateTime); // Check if within regular trading period
        }
    }

    // Check if a given date and time is within the regular trading hours
    private boolean isRegularTradingPeriod(LocalDateTime dateTime) {
        // Check if within regular trading period (Monday to Friday, regular market hours)
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        LocalTime time = dateTime.toLocalTime();

        // Regular market hours: 9:00 AM - 12:30 PM and 2:30 PM - 5:00 PM MST
        LocalTime marketStartTime1 = LocalTime.of(9, 0);
        LocalTime marketEndTime1 = LocalTime.of(12, 30);
        LocalTime marketStartTime2 = LocalTime.of(14, 30);
        LocalTime marketEndTime2 = LocalTime.of(17, 0);

        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY &&
                ((time.isAfter(marketStartTime1) && time.isBefore(marketEndTime1)) ||
                        (time.isAfter(marketStartTime2) && time.isBefore(marketEndTime2)));
    }



    public static LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        TradingRestrictions.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getInitialTradingHoursDays() {
        return initialTradingHoursDays;
    }

    public void setInitialTradingHoursDays(int initialTradingHoursDays) {
        this.initialTradingHoursDays = initialTradingHoursDays;
    }

}