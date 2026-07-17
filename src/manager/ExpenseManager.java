package manager;
import model.*;
import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ExpenseManager {
    ArrayList<User> users =new ArrayList<>();
    ArrayList<Income> income =new ArrayList<>();
    ArrayList<Expense> expense=new ArrayList<>();
    public ExpenseManager() {
        this.users=users;
        this.income=income;
        this.expense=expense;
    }
    public void addUser(String uName){

        String sql="INSERT INTO users(user_name) VALUES (?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, uName);

            int rows = ps.executeUpdate();

            if(rows > 0){
                System.out.println("model.User Added Successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<User> getAllUser(){
        String sql="SELECT * FROM users";
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                User user=new User(
                        rs.getInt("user_id"),
                        rs.getString("user_name")
                );
                users.add(user);
            }


        }catch(Exception e){
            e.printStackTrace();
        }
        return users;
    }
    public User findUserById(int userId){

        String sql="SELECT * FROM users WHERE user_id=?";
        try(Connection con= DatabaseConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)
        ){
            ps.setInt(1,userId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                System.out.println("model.User ID: " + rs.getInt("user_id"));
                System.out.println("model.User Name: " + rs.getString("user_name"));

                return new User(
                        rs.getInt("user_id"),
                        rs.getString("user_name")
                );
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
    public void addIncome(int userId,double amount, String source){

        User user = findUserById(userId);

        if(user == null){
            System.out.println("model.User not found!");
            return;
        }

        String sql="INSERT INTO income(user_id,amount,inc_source,income_date,income_time) VALUES(?,?,?,?,?)";
        try(Connection con= DatabaseConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)
        ){
            ps.setInt(1,userId);
            ps.setDouble(2,amount);
            ps.setString(3, source);
            ps.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
            ps.setTime(5, java.sql.Time.valueOf(LocalTime.now()));

            int rows = ps.executeUpdate();

            if(rows > 0){
                System.out.println("model.Income Added Successfully!");
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public ArrayList<Income> viewIncome(){
        String sql="SELECT * FROM income";
        try(Connection con= DatabaseConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ){while(rs.next()){
            Income inc=new Income(
                    rs.getInt("income_id"),
                    rs.getInt("user_id"),
                    rs.getDouble("amount"),
                    rs.getString("inc_source"),
                    rs.getDate("income_date").toLocalDate(),
                    rs.getTime("income_time").toLocalTime()
            );
            income.add(inc);

        }
        }catch(Exception e){
            e.printStackTrace();
        }
        return income;
    }
    public void addExpense(int userId, double amount, String category, String description){

        User user = findUserById(userId);

        if(user == null){
            System.out.println("model.User not found!");
            return;
        }

        String sql = "INSERT INTO expense(user_id, amount_spent, category, description, income_date, income_time) VALUES(?,?,?,?,?,?)";

        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, userId);
            ps.setDouble(2, amount);
            ps.setString(3, category);
            ps.setString(4, description);
            ps.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
            ps.setTime(6, java.sql.Time.valueOf(LocalTime.now()));

            int rows = ps.executeUpdate();

            if(rows > 0){
                System.out.println("model.Expense Added Successfully!");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public ArrayList<Expense> viewExpenses(){

        String sql = "SELECT * FROM expense";

        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ){

            while(rs.next()){
            Expense exp=new Expense(
                    rs.getInt("expense_id"),
                rs.getInt("user_id"),
                rs.getDouble("amount_spent"),
                rs.getString("category"),
                rs.getString("description"),
                rs.getDate("income_date").toLocalDate(),
                rs.getTime("income_time").toLocalTime()
            );
                expense.add(exp);
            }


        }catch(Exception e){
            e.printStackTrace();
        }
        return expense;
    }
    public void calSavings(int userId){

        double totalIncome = 0;
        double totalExpense = 0;

        String incomeSql = "SELECT COALESCE(SUM(amount), 0) AS total_income FROM income WHERE user_id = ?";
        String expenseSql = "SELECT COALESCE(SUM(amount_spent), 0) AS total_expense FROM expense WHERE user_id = ?";

        try(Connection con = DatabaseConnection.getConnection()){

            try(PreparedStatement ps = con.prepareStatement(incomeSql)){
                ps.setInt(1, userId);

                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    totalIncome = rs.getDouble("total_income");
                }
            }

            // Total model.Expense
            try(PreparedStatement ps = con.prepareStatement(expenseSql)){
                ps.setInt(1, userId);

                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    totalExpense = rs.getDouble("total_expense");
                }
            }

            double savings = totalIncome - totalExpense;

            System.out.println("\n------ Savings Report ------");
            System.out.println("model.User ID      : " + userId);
            System.out.println("Total model.Income : ₹" + totalIncome);
            System.out.println("Total model.Expense: ₹" + totalExpense);
            System.out.println("Savings      : ₹" + savings);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void delUser(int uid){

        String sql="DELETE FROM users WHERE user_id=?";
            try(Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)){

                ps.setInt(1, uid);

                int rows = ps.executeUpdate();

                if(rows > 0){
                    System.out.println("model.User deleted successfully!");
                }else{
                    System.out.println("model.User not found!");
                }

            }catch(Exception e){
                e.printStackTrace();
            }
    }
    public void delIncome(int incid){
        String sql="DELETE FROM income WHERE income_id=?";
        try(Connection con= DatabaseConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setInt(1,incid);
            int rows=ps.executeUpdate();
            if(rows > 0){
                System.out.println("model.Income deleted successfully!");
            }else{
                System.out.println("model.Income not found!");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void delExpense(int eid){
        String sql="DELETE FROM expense WHERE expense_id=?";
        try(Connection con= DatabaseConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setInt(1,eid);
            int rows=ps.executeUpdate();
            if(rows > 0){
                System.out.println("model.Expense deleted successfully!");
            }else{
                System.out.println("model.Expense not found!");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
//    public void calculateTodaySavings(int userId){
//
//        model.User user = findUserById(userId);
//
//        if(user == null){
//            System.out.println("model.User not found!");
//            return;
//        }
//
//        double totalIncome = 0;
//        double totalExpense = 0;
//
//        LocalDate today = LocalDate.now();
//
//        // Today's income
//        for(model.Income income : incomes){
//            if(income.getUserId() == userId &&
//                    income.getDate().equals(today)){
//
//                totalIncome += income.getAmount();
//            }
//        }
//
//        // Today's expense
//        for(model.Expense expense : expenses){
//            if(expense.getUserId() == userId &&
//                    expense.getDate().equals(today)){
//
//                totalExpense += expense.getSpentAmt();
//            }
//        }
//
//        double savings = totalIncome - totalExpense;
//
//        System.out.println("\n------ Today's Savings ------");
//        System.out.println("Date          : " + today);
//        System.out.println("model.User          : " + user.getuName());
//        System.out.println("Today's model.Income: ₹" + totalIncome);
//        System.out.println("Today's model.Expense: ₹" + totalExpense);
//        System.out.println("Today's Savings: ₹" + savings);
//    }

    public void updExpense(int eid, double amount,String category,String description){

        String sql="UPDATE expense SET amount_spent=?,category=?, description=? WHERE expense_id=?";
        try(Connection con= DatabaseConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setDouble(1,amount);
            ps.setString(2,category);
            ps.setString(3,description);
            ps.setInt(4,eid);
            int rows = ps.executeUpdate();

            if(rows > 0){
                System.out.println("model.Expense Updated Successfully!");
            }else{
                System.out.println("model.Expense ID not found!");
            }
        }catch (Exception e){
           e.printStackTrace();
        }
    }

    public void updateIncome(int incomeid, double amount, String source) {

        String sql="UPDATE income SET amount=?,inc_source=? WHERE income_id=?";
        try(Connection con= DatabaseConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setDouble(1,amount);
            ps.setString(2,source);
            ps.setInt(3,incomeid);
            int rows = ps.executeUpdate();

            if(rows > 0){
                System.out.println("model.Income Updated Successfully!");
            }else{
                System.out.println("model.Income ID not found!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void updateUser(int userid, String uname){
        String sql="UPDATE users SET user_name=? WHERE user_id=?";
        try(Connection con=DatabaseConnection.getConnection();
        PreparedStatement ps= con.prepareStatement(sql)){
            ps.setInt(2,userid);
            ps.setString(1,uname);
            int rows=ps.executeUpdate();
            if(rows > 0){
                System.out.println("model.Income Updated Successfully!");
            }else{
                System.out.println("model.Income ID not found!");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
