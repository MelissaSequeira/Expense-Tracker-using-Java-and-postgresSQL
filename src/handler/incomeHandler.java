package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.ExpenseManager;
import model.Income;
import model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;


public class incomeHandler implements HttpHandler {
    private ExpenseManager manager;
    public incomeHandler(ExpenseManager manager){this.manager=manager;}

    @Override
    public void handle (HttpExchange exchange) throws IOException{
    if((exchange.getRequestMethod().equalsIgnoreCase("GET"))){
        ArrayList<Income> inc=manager.viewIncome();
        StringBuilder json= new StringBuilder();
        json.append("[");
            for (int i = 0; i < inc.size(); i++) {

                Income income= inc.get(i);

                json.append("{");
                json.append("\"Income Id\":").append(income.getIncomeId()).append(",");
                json.append("\"userId\":").append(income.getUserId()).append(",");
                json.append("\"Amount\":").append(income.getAmount()).append(",");
                json.append("\"Income Date\":").append(income.getDate()).append(",");
                json.append("\"Income Time\":\"").append(income.getTime()).append("\"");
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
            String inc_source= bf.readLine();
            manager.addIncome(user_id,amount,inc_source);
        String response = "Income Added Successfully";
        exchange.sendResponseHeaders(200, response.getBytes().length);

        exchange.getResponseBody().write(response.getBytes());
        exchange.getResponseBody().close();
        }else if(exchange.getRequestMethod().equalsIgnoreCase("DELETE")){
        BufferedReader bf=new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        int income_id=Integer.parseInt(bf.readLine());
        manager.delIncome(income_id);
        String response = "Income Deleted Successfully";
        exchange.sendResponseHeaders(200, response.getBytes().length);

        exchange.getResponseBody().write(response.getBytes());
        exchange.getResponseBody().close();

    }else if(exchange.getRequestMethod().equalsIgnoreCase("PUT")){
        BufferedReader bf=new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        int income_id=Integer.parseInt(bf.readLine());
        double amount=Double.parseDouble(bf.readLine());
        String inc_source= bf.readLine();
        manager.updateIncome(income_id,amount,inc_source);
        String response = "Income Updated Successfully";
        exchange.sendResponseHeaders(200, response.getBytes().length);

        exchange.getResponseBody().write(response.getBytes());
        exchange.getResponseBody().close();

    }
    }
}
