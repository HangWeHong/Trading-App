package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.scene.chart.PieChart.Data;

public class test {
    public static void main(String[] args) {
        DatabaseHandler dbh=new DatabaseHandler();
        TradingRestrictions TR=new TradingRestrictions();
        String strStartDate = "2023-06-08";
        String strEndDate = "2023-06-20";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(strStartDate, dateFormatter);
        LocalDate endDate = LocalDate.parse(strEndDate, dateFormatter);


        TR.setTradingPeriod(startDate, endDate, 3);
        DatabaseHandler.scheduleLotPoolReplenishment();
        DatabaseHandler.schedulePLPointsUpdate();
        DatabaseHandler.scheduleUserBalanceCheck();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // String strNow="2023-06-10 17:00:00";
        // LocalDateTime now = LocalDateTime.parse(strNow, dateTimeFormatter);
        LocalDateTime currentDateTime = LocalDateTime.now();

        // LocalDateTime now = LocalDateTime.now();
        System.out.println(TR.isWithinTradingHours(currentDateTime));
        OrderHandler oh=new OrderHandler();
       
        // oh.handleSellScenario("Anwar Ibrahim", "ACO", 0.20, 500);
        //   oh.handleBuyScenario("baba", "3A", 0.78, 1000);
        oh.cancelOrder("baba", "Buy");

    }
}
