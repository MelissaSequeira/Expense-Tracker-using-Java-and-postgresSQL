package manager;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.ExpenseManager;
import model.Savings;

import java.io.IOException;

public class savingHandler implements HttpHandler {

    private ExpenseManager manager;

    public savingHandler(ExpenseManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if(exchange.getRequestMethod().equalsIgnoreCase("GET")){

            // Read userId from URL
            String query = exchange.getRequestURI().getQuery();

            int userId = Integer.parseInt(query.split("=")[1]);

            // Get Savings object
            Savings save = manager.calSavings(userId);

            // Convert to JSON
            String response =
                    "{"
                            + "\"totalIncome\":" + save.getTotalIncome() + ","
                            + "\"totalExpense\":" + save.getTotalExpense() + ","
                            + "\"savings\":" + save.getSavings()
                            + "}";

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
            exchange.getResponseBody().close();

        }else{

            String response = "Method Not Allowed";

            exchange.sendResponseHeaders(405, response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
            exchange.getResponseBody().close();
        }
    }
}