import java.io.IOException;
import java.sql.Connection;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) throws IOException {
        Scanner sc=new Scanner(System.in);
        ExpenseManager e=new ExpenseManager();
        Connection conn=DatabaseConnection.getConnection();
        if(conn!=null){
            System.out.println("connection established!!");
        }else {
            System.out.println("connection failed");
        }
        while(true){

            System.out.println("\n========== Expense Tracker ==========");
            System.out.println("1. Add User");
            System.out.println("2. View Users");
            System.out.println("3. Add Income");
            System.out.println("4. View Income");
            System.out.println("5. Add Expense");
            System.out.println("6. View Expenses");
            System.out.println("7. Calculate Savings");
            System.out.println("8. Calculate Today's Savings");
            System.out.println("9. Delete User");
            System.out.println("10. Delete Income");
            System.out.println("11. Delete Expense");
            System.out.println("12. Update Income");
            System.out.println("13. Update Expense");
            System.out.println("14. Find User");
            System.out.println("0. Exit");
            System.out.println("=====================================");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch(choice){

                case 1:
                    e.addUser();
                    break;

                case 2:
                    e.showUser();
                    break;

                case 3:
                    e.addIncome();
                    break;

                case 4:
                    e.viewIncome();
                    break;

                case 5:
                    e.addExpense();
                    break;

                case 6:
                    e.viewExpenses();
                    break;

                case 7:
                    System.out.print("Enter User ID: ");
                    int uid = sc.nextInt();
                    e.calSavings(uid);
                    break;

                case 8:
                    System.out.print("Enter User ID: ");
                    int uid1 = sc.nextInt();
                    e.calculateTodaySavings(uid1);
                    break;

                case 9:
                    System.out.print("Enter User ID: ");
                    int uid2 = sc.nextInt();
                    e.delUser(uid2);
                    break;

                case 10:
                    System.out.print("Enter Income ID: ");
                    int incomeId = sc.nextInt();
                    e.delIncome(incomeId);
                    break;

                case 11:
                    System.out.print("Enter Expense ID: ");
                    int expenseId = sc.nextInt();
                    e.delExpense(expenseId);
                    break;

                case 12:
                    e.updateIncome();
                    break;

                case 13:
                    System.out.print("Enter Expense ID: ");
                    int expId = sc.nextInt();
                    e.updExpense(expId);
                    break;

                case 14:
                    System.out.print("Enter User ID: ");
                    int userId = sc.nextInt();
                    e.findUserById(userId);
                    break;

                case 0:
                    System.out.println("Thank you! Visit Again.");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}
