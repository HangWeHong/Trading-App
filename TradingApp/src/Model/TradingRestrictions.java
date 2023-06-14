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

    // Check if a user is allowed to place an order based on trading restrictions
    // public boolean canPlaceOrder(User user, Stock stock, Order.Type type, int quantity, LocalDateTime dateTime) {
    //     boolean withinTradingHours = isWithinTradingHours(dateTime);
    //     boolean notTradingOnMargin = !isTradingOnMargin(user, quantity, stock.getCurrentPrice());
    //     boolean notShortSelling = !isShortSelling(user, stock, quantity, type);
    //     boolean notDisqualified = !isParticipantDisqualified(user, dateTime.toLocalTime());

    //     return withinTradingHours && notTradingOnMargin && notShortSelling && notDisqualified;
    // }


    // Check if a user is trading on margin (insufficient account balance)
    // public boolean isTradingOnMargin(User user, int quantity, double price) {
    //     double requiredAccountBalance = quantity * price;
    //     return requiredAccountBalance > user.getAccountBalance();
    // }

    // // Check if a user is short selling (selling more stocks than owned)
    // public boolean isShortSelling(User user, Stock stock, int quantity, Order.Type type) {
    //     if (type == Order.Type.SELL) {
    //         int ownedQuantity = user.getPortfolio().getStockQuantity(stock);
    //         return ownedQuantity < quantity;
    //     }
    //     return false;
    // }

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

    // Check if a participant is disqualified based on their account balance
    // public boolean isParticipantDisqualified(User user, LocalTime currentTime) {
    //     double accountBalance = user.getAccountBalance();
    //     double initialBalance = user.getInitialFunds();

    //     if (currentTime.isBefore(LocalTime.of(17, 0))) {
    //         return false; // Not disqualified (balance check not performed yet)
    //     }
    //     // Account balance must be less than or equal to 50% of initial balance
    //     if (accountBalance >= (0.5 * initialBalance)) {
    //         return true; // Disqualified
    //     }
    //     // Account balance must be greater than 0
    //     if (accountBalance < 0) {
    //         return true; // Disqualified (negative balance)
    //     }
    //     return false; // Not disqualified
    // }

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