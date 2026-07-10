import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static final String URL="jdbc:postgresql://localhost:5432/expense";
    private static final String USER="postgres";
    private static final String PASSWORD="postgres123";

    public static Connection getConnection(){
        try{
            return DriverManager.getConnection(URL,USER,PASSWORD);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
