import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TicketsDataBase extends DataBase{
    TicketsDataBase(){
        super();
    }

    public int generateTicketID() throws SQLException {
        String query = "select max(id) as max_id from sql_ticketbooking.tickets";
        ResultSet max_id = statement.executeQuery(query);
        if(!max_id.next()) return 1;
        return (max_id.getInt(1) + 1);
    }

    public boolean ticketExists(String destination, String tod, String toh) throws SQLException {
        String query = "select destination, takeOffDate, takeOffHour from sql_ticketbooking.tickets";
        ResultSet results = statement.executeQuery(query);
        if(!results.next()) return false;
        if(destination.compareTo(results.getString(1)) != 0) return false;
        if(tod.compareTo(results.getString(2)) != 0) return false;
        if(toh.compareTo(results.getString(3)) != 0) return false;
        return true;
    }

    public void addTicketToDatabase(int id, String destination, String takeOffDate, String takeOffHour, int price, int availableCopies) throws SQLException {
        String query = "insert into sql_ticketbooking.tickets values (" + id + ", '" + destination + "', '" + takeOffDate + "', '" + takeOffHour +
                "', " + price + ", " + availableCopies + ")";
        statement.executeUpdate(query);
    }

    public ArrayList<String> searchTicketByDestination(String destination) throws SQLException {
        String query = "select * from sql_ticketbooking.tickets where destination = '" + destination + "'";
        return getStrings(query);
    }

    private ArrayList<String> getStrings(String query) throws SQLException {
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> tableContent = new ArrayList<>();

        while(resultSet.next()){
            for(int i = 1; i <= 6; i++){
                if(i == 5) tableContent.add(resultSet.getString(i) + "$");
                else tableContent.add(resultSet.getString(i));
            }
        }

        return tableContent;
    }

    public ArrayList<String> searchTicketByFlightDate(String date) throws SQLException {
        if(date.length() !=  10 || date.charAt(2) != '-' || date.charAt(5) != '-'){
            JOptionPane.showMessageDialog(null, "Invalid date format\nShould be DD-MM-YYYY");
            return new ArrayList<>();
        }
        String query = "select * from sql_ticketbooking.tickets where takeOffDate = '" + date + "'";
        return getStrings(query);
    }

    public ArrayList<String> combinedSearch(String destination, String date) throws SQLException {
        if(date.length() !=  10 || date.charAt(2) != '-' || date.charAt(5) != '-'){
            JOptionPane.showMessageDialog(null, "Invalid date format\nShould be DD-MM-YYYY");
            return new ArrayList<>();
        }
        String query = "select * from sql_ticketbooking.tickets where destination = '" + destination + "' and takeOffDate = '" + date + "'";
        return getStrings(query);
    }

    public boolean isTicketValid(int id) throws SQLException {
        String query = "select id from sql_ticketbooking.tickets where id = '" + id + "'";
        ResultSet resultSet = statement.executeQuery(query);
        if(!resultSet.next()) return false;
        else return true;
    }

    public void bookTicket(int id) throws SQLException {
        String searchQuery = "select availableCopies from sql_ticketbooking.tickets where id = " + id;
        ResultSet copies = statement.executeQuery(searchQuery);
        copies.next();
        int updatedCopies = copies.getInt(1);
        updatedCopies--;
        String updateQuery = "update sql_ticketbooking.tickets set availableCopies = '" + updatedCopies + "' where id = " + id;
        if(updatedCopies >= 0) statement.executeUpdate(updateQuery);
    }

    public int getTicketPrice(int id) throws SQLException {
        String query = "select price from sql_ticketbooking.tickets where id = " + id;
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        return resultSet.getInt(1);
    }
}
