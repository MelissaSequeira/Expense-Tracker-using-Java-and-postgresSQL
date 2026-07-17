package model;

public class Savings {

    private double totalIncome;
    private double totalExpense;
    private double savings;

    public Savings(double totalIncome,
                   double totalExpense,
                   double savings){

        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.savings = savings;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public double getSavings() {
        return savings;
    }
}