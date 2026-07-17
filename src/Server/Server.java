package Server;

import com.sun.net.httpserver.HttpServer;
import manager.ExpenseManager;
import handler.*;
import manager.savingHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Server {

    public void start() throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/", exchange -> {

            String response = "Expense Tracker Backend Running";

            exchange.sendResponseHeaders(200, response.getBytes().length);

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });
        ExpenseManager manager = new ExpenseManager();

        server.createContext("/users", new userHandler(manager));
        server.createContext("/income",new incomeHandler(manager));
        server.createContext("/savings", new savingHandler(manager));
        server.setExecutor(null);

        server.start();

        System.out.println("Server started at http://localhost:8080");
    }
}