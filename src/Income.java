import java.time.*;

public class Income {
    private int incomeId;
    private int userId;
    private double amount;
    private String source;
    private LocalDate date;
    private LocalTime time;
    private static int nextId = 1;
    Income( int userId, double amount, String source, LocalDate date, LocalTime time){
        this.incomeId=nextId++;
        this.userId=userId;
        this.amount=amount;
        this.source=source;
        this.date=date;
        this.time=time;
    }
    public int getIncomeId() {
        return incomeId;
    }

    public int getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }

    public String getSource() {
        return source;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void show() {
        System.out.println("Income ID: " + incomeId);
        System.out.println("User ID: " + userId);
        System.out.println("Amount: " + amount);
        System.out.println("Source: " + source);
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
    }
}
