package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.ExpenseManager;
import model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class userHandler implements HttpHandler {

    private ExpenseManager manager;

    public userHandler(ExpenseManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // Handle only GET request
        if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {

            ArrayList<User> users = manager.getAllUser();

            StringBuilder json = new StringBuilder();

            json.append("[");

            for (int i = 0; i < users.size(); i++) {

                User user = users.get(i);

                json.append("{");
                json.append("\"userId\":").append(user.getuId()).append(",");
                json.append("\"userName\":\"").append(user.getuName()).append("\"");
                json.append("}");

                if (i < users.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]");

            String response = json.toString();

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.getBytes().length);

            exchange.getResponseBody().write(response.getBytes());
            exchange.getResponseBody().close();
        }
        else if(exchange.getRequestMethod().equalsIgnoreCase("POST")){
            BufferedReader br=new BufferedReader(
                    new InputStreamReader(exchange.getRequestBody())
            );
            String userName=br.readLine();
            manager.addUser(userName);

            String response = "User Added Successfully";

            exchange.sendResponseHeaders(200,response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
            exchange.getResponseBody().close();
        }
        else if(exchange.getRequestMethod().equalsIgnoreCase("DELETE")){
            BufferedReader bf=new BufferedReader(new InputStreamReader(exchange.getRequestBody()) );
            int user_id=Integer.parseInt(bf.readLine());
            manager.delUser(user_id);
            String response = "User deleted Successfully";

            exchange.sendResponseHeaders(200,response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
            exchange.getResponseBody().close();
        }else if(exchange.getRequestMethod().equalsIgnoreCase("PUT")){
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(exchange.getRequestBody())
            );

            int userId = Integer.parseInt(br.readLine());
            String newName = br.readLine();

            manager.updateUser(userId, newName);

            String response = "User Updated Successfully";

            exchange.sendResponseHeaders(200, response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
            exchange.getResponseBody().close();
        }
    }

}