package model;

import java.time.*;

public class Expense {

    private int expenseId;
    private int userId;
    private double spentAmt;
    private String category;
    private String description;
    private LocalDate date;
    private LocalTime time;
    Expense(int expenseId,int userId, double spentAmt, String category,String description,LocalDate date, LocalTime time){
        this.expenseId=expenseId;
        this.userId=userId;
        this.spentAmt=spentAmt;
        this.category=category;
        this.description=description;
        this.date=date;
        this.time=time;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public int getUserId() {
        return userId;
    }


    public void show(){
        System.out.println("model.Expense id: "+getExpenseId());
        System.out.println("model.User id: "+getUserId());
        System.out.println("Spent: "+getSpentAmt());
        System.out.println("Category: "+getCategory());
        System.out.println("Description: "+getDescription());
        System.out.println("Date: "+getDate());
        System.out.println("Time: "+getTime());
    }

    public double getSpentAmt() {
        return spentAmt;
    }

    public void setSpentAmt(double spentAmt) {
        this.spentAmt = spentAmt;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}