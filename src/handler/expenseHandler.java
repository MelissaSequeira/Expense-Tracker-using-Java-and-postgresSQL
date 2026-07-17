package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.ExpenseManager;
import model.Expense;
import model.Income;
import model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;


public class expenseHandler implements HttpHandler {
    private ExpenseManager manager;
    public expenseHandler(ExpenseManager manager){this.manager=manager;}

    @Override
    public void handle (HttpExchange exchange) throws IOException{
        if((exchange.getRequestMethod().equalsIgnoreCase("GET"))){
            ArrayList<Expense> inc=manager.viewExpenses();
            StringBuilder json= new StringBuilder();
            json.append("[");
            for (int i = 0; i < inc.size(); i++) {

                Expense exp= inc.get(i);

                json.append("{");
                json.append("\"Expense Id\":").append(exp.getExpenseId()).append(",");
                json.append("\"userId\":").append(exp.getUserId()).append(",");
                json.append("\"Amount spent\":").append(exp.getSpentAmt()).append(",");
                json.append("\"Spent category\":").append(exp.getCategory()).append(",");
                json.append("\"description\":").append(exp.getDescription()).append(",");
                json.append("\"Spent Date\":").append(exp.getDate()).append(",");
                json.append("\"Spent Time\":\"").append(exp.getTime()).append("\"");
                json.append("}");

                if (i < inc.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]");
            String response = json.toString();

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.getBytes().length);

            exchange.getResponseBody().write(response.getBytes());
            exchange.getResponseBody().close();
        } else if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            BufferedReader bf=new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
            int user_id=Integer.parseInt(bf.readLine());
            double amount=Double.parseDouble(bf.readLine());
            String category= bf.readLine();
            String description= bf.readLine();
            manager.addExpense(user_id,amount,category,description);
            String response = "Expense Added Successfully";
            exchange.sendResponseHeaders(200, response.getBytes().length);

            exchange.getResponseBody().write(response.getBytes());
            exchange.getResponseBody().close();
        }else if(exchange.getRequestMethod().equalsIgnoreCase("DELETE")){
            BufferedReader bf=new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
            int expense_id=Integer.parseInt(bf.readLine());
            manager.delExpense(expense_id);
            String response = "Expense Deleted Successfully";
            exchange.sendResponseHeaders(200, response.getBytes().length);

            exchange.getResponseBody().write(response.getBytes());
            exchange.getResponseBody().close();

        }else if(exchange.getRequestMethod().equalsIgnoreCase("PUT")){
            BufferedReader bf=new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
            int exp_id=Integer.parseInt(bf.readLine());
            double amount=Double.parseDouble(bf.readLine());
            String category= bf.readLine();
            String description= bf.readLine();
            manager.updExpense(exp_id,amount,category,description);
            String response = "Expense Updated Successfully";
            exchange.sendResponseHeaders(200, response.getBytes().length);

            exchange.getResponseBody().write(response.getBytes());
            exchange.getResponseBody().close();

        }
    }
}
