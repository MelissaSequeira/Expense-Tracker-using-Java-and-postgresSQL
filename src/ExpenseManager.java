import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ExpenseManager {
    private ArrayList<User> users;
    private ArrayList<Expense> expenses;
    private ArrayList<Income> incomes;
    public ExpenseManager() {
        users = new ArrayList<>();
        expenses = new ArrayList<>();
        incomes = new ArrayList<>();
    }
    Scanner sc=new Scanner(System.in);
    public void addUser(){
        System.out.println("User Name: ");
        String uName= sc.next();
//        User u= new User(uName);
//        users.add(u);
//        System.out.println("user added!!");
        String sql="INSERT INTO users(user_name) VALUES (?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, uName);

            int rows = ps.executeUpdate();

            if(rows > 0){
                System.out.println("User Added Successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showUser(){
//        for(User us:users){
//            us.show();
//            System.out.println("============================");
//        }
        String sql="SELECT * FROM users";
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){

                System.out.println("User ID : " + rs.getInt("user_id"));
                System.out.println("User Name : " + rs.getString("user_name"));
                System.out.println("------------------------");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public User findUserById(int userId){

        String sql="SELECT * FROM users WHERE user_id=?";
        try(Connection con= DatabaseConnection.getConnection();
        PreparedStatement ps=con.prepareStatement(sql)
        ){
            ps.setInt(1,userId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                System.out.println("User ID: " + rs.getInt("user_id"));
                System.out.println("User Name: " + rs.getString("user_name"));

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
    public void addIncome(){

        System.out.print("Enter User ID: ");
        int userId = sc.nextInt();

        User user = findUserById(userId);

        if(user == null){
            System.out.println("User not found!");
            return;
        }

        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();

        sc.nextLine();

        System.out.print("Enter Source: ");
        String source = sc.nextLine();
        String sql="INSERT INTO income(user_id,amount,inc_source,income_date,income_time) VALUES(?,?,?,?,?)";
        try(Connection con=DatabaseConnection.getConnection();
        PreparedStatement ps=con.prepareStatement(sql)
        ){
            ps.setInt(1,userId);
            ps.setDouble(2,amount);
            ps.setString(3, source);
            ps.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
            ps.setTime(5, java.sql.Time.valueOf(LocalTime.now()));

            int rows = ps.executeUpdate();

            if(rows > 0){
                System.out.println("Income Added Successfully!");
            }

        }catch(Exception e){
            e.printStackTrace();
        }

//        Income income = new Income(
//                userId,
//                amount,
//                source,
//                LocalDate.now(),
//                LocalTime.now()
//        );
//
//        incomes.add(income);

    }

    public void viewIncome(){
//
//        if(incomes.isEmpty()){
//            System.out.println("No income found!");
//            return;
//        }
//
//        for(Income income : incomes){
//            income.show();
//            System.out.println("----------------");
//        }
        String sql="SELECT * FROM income";
        try(Connection con= DatabaseConnection.getConnection();
        PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ){while(rs.next()){
            System.out.println("Income id: "+rs.getInt("income_id"));
            System.out.println("User id: "+rs.getInt("user_id"));
            System.out.println("Amount: "+rs.getDouble("amount"));
            System.out.println("Income source: "+rs.getString("inc_source"));
            System.out.println("Income date: "+rs.getDate("income_date"));
            System.out.println("Income time: "+rs.getTime("income_time"));

        }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void addExpense(){

        System.out.print("Enter User ID: ");
        int userId = sc.nextInt();

        User user = findUserById(userId);

        if(user == null){
            System.out.println("User not found!");
            return;
        }

        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter Category: ");
        String category = sc.nextLine();

        System.out.print("Enter Description: ");
        String description = sc.nextLine();

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
                System.out.println("Expense Added Successfully!");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void viewExpenses(){

        String sql = "SELECT * FROM expense";

        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ){

            while(rs.next()){

                System.out.println("Expense ID : " + rs.getInt("expense_id"));
                System.out.println("User ID    : " + rs.getInt("user_id"));
                System.out.println("Amount     : " + rs.getDouble("spent_amt"));
                System.out.println("Category   : " + rs.getString("category"));
                System.out.println("Description: " + rs.getString("description"));
                System.out.println("Date       : " + rs.getDate("expense_date"));
                System.out.println("Time       : " + rs.getTime("expense_time"));
                System.out.println("---------------------------");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void calSavings(int userId){
        double totalIncome=0;
        double totalExpense=0;
        for(Income inc:incomes){
            if(inc.getUserId()==userId){
                totalIncome=totalIncome+inc.getAmount();
            }
        }
        for(Expense exp:expenses){
            if(exp.getUserId()==userId){
                totalExpense=totalExpense+exp.getSpentAmt();
            }
        }
        double savings = totalIncome - totalExpense;

        System.out.println("\n------ Savings Report ------");
        System.out.println("User ID      : " + userId);
        System.out.println("Total Income : ₹" + totalIncome);
        System.out.println("Total Expense: ₹" + totalExpense);
        System.out.println("Savings      : ₹" + savings);
    }
    public void delUser(int uid){
        boolean found=false;
        for(User u:users){
            if(u.getuId()==uid){
                users.remove(u);
                System.out.println("User deleted!!");
                found=true;
            }
        }
        if(!found){
            System.out.println("user not found!!");
        }
    }
    public void delIncome(int uid){
        boolean found=false;
        for(Income inc:incomes){
            if(inc.getIncomeId()==uid){
                users.remove(inc);
                System.out.println("Income deleted!!");
                found=true;
            }
        }
        if(!found){
            System.out.println("User income not found!!");
        }
    }
    public void delExpense(int uid){
        boolean found=false;
        for(Expense exp:expenses){
            if(exp.getExpenseId()==uid){
                users.remove(exp);
                System.out.println("User Expense deleted!!");
                found=true;
            }
        }
        if(!found){
            System.out.println("user expense not found!!");
        }
    }
    public void calculateTodaySavings(int userId){

        User user = findUserById(userId);

        if(user == null){
            System.out.println("User not found!");
            return;
        }

        double totalIncome = 0;
        double totalExpense = 0;

        LocalDate today = LocalDate.now();

        // Today's income
        for(Income income : incomes){
            if(income.getUserId() == userId &&
                    income.getDate().equals(today)){

                totalIncome += income.getAmount();
            }
        }

        // Today's expense
        for(Expense expense : expenses){
            if(expense.getUserId() == userId &&
                    expense.getDate().equals(today)){

                totalExpense += expense.getSpentAmt();
            }
        }

        double savings = totalIncome - totalExpense;

        System.out.println("\n------ Today's Savings ------");
        System.out.println("Date          : " + today);
        System.out.println("User          : " + user.getuName());
        System.out.println("Today's Income: ₹" + totalIncome);
        System.out.println("Today's Expense: ₹" + totalExpense);
        System.out.println("Today's Savings: ₹" + savings);
    }

    public void updExpense(int uid){
        for (Expense expense : expenses) {

            if (expense.getExpenseId() == uid) {

                System.out.print("Enter New Amount: ");
                double amount = sc.nextDouble();
                sc.nextLine();

                System.out.print("Enter New Category: ");
                String category = sc.nextLine();

                System.out.print("Enter New Description: ");
                String description = sc.nextLine();

                expense.setSpentAmt(amount);
                expense.setCategory(category);
                expense.setDescription(description);

                System.out.println("Expense Updated Successfully!");
                return;
            }
        }

        System.out.println("Expense not found!");
    }

    public void updateIncome() {

        System.out.print("Enter Income ID: ");
        int incomeId = sc.nextInt();
        sc.nextLine();

        for (Income income : incomes) {

            if (income.getIncomeId() == incomeId) {

                System.out.print("Enter New Amount: ");
                double amount = sc.nextDouble();
                sc.nextLine();

                System.out.print("Enter New Source: ");
                String source = sc.nextLine();

                income.setAmount(amount);
                income.setSource(source);

                System.out.println("Income Updated Successfully!");
                return;
            }
        }

        System.out.println("Income not found!");
    }
}
