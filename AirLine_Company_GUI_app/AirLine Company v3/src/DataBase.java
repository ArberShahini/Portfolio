import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
    protected final String url = "jdbc:mysql://localhost:3306/sql_ticketbooking";
    protected final String username = "root";
    protected final String password = "password";
    protected Connection connection;
    protected Statement statement;


    DataBase(){
        try{
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Couldn't connect to database due to it being LOCAL");
            System.exit(1);
        }
    }
}
