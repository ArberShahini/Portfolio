import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;

public class BookingsDataBase extends DataBase{
    BookingsDataBase(){
        super();
    }

    public void addToBookingsDatabase(int id, String ownerUsername, String firstname, String lastname, String gender, int baggageAmount, String ticketCode) throws SQLException {
        String query = "insert into sql_ticketbooking.bookings values (" + id + ", '" + ownerUsername + "', '" + firstname + "', '" + lastname + "', '" + gender +
                "', " + baggageAmount + ", " + 0 + ", '" + ticketCode + "')";
        statement.executeUpdate(query);
    }

    public boolean bookingAlreadyExists(int id, String ownerUsername, String firstname, String lastname, String gender) throws SQLException {
        String query = "select * from sql_ticketbooking.bookings where id = " + id + " and ownerUsername = '" + ownerUsername + "' and firstname = '" + firstname + "'" +
                " and lastname = '" + lastname + "' and gender = '" + gender + "'";
        ResultSet resultSet = statement.executeQuery(query);
        if(!resultSet.next()) return false;
        else return true;
    }

    public boolean ticketCodeExists(String ticketCode) throws SQLException {
        String query = "select id from sql_ticketbooking.bookings where ticketCode = '" + ticketCode + "'";
        ResultSet resultSet = statement.executeQuery(query);
        if(!resultSet.next()) return false;
        else return true;
    }

    public String generateTicketCode() throws SQLException {
        GregorianCalendar seed = new GregorianCalendar();
        Random random = new Random(seed.getTimeInMillis());
        char [] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        int [] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        StringBuilder code = new StringBuilder();
        boolean newCodeFound = false;

        while(!newCodeFound){
            for(int i = 0; i < 8; i++){
                if(i == 0 || i == 7) code.append(letters[random.nextInt(0,25)]);
                else code.append(numbers[random.nextInt(0, 9)]);
            }
            if(!ticketCodeExists(code.toString())) newCodeFound = true;
        }
        return code.toString();
    }

    public ArrayList<String> getTableData(String loggedUser) throws SQLException {
        String query = "select id, firstname, lastname, gender, baggageAmount, checkInStatus, ticketCode from sql_ticketbooking.bookings where ownerUsername = '" + loggedUser + "'";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> list = new ArrayList<>();

        while(resultSet.next()){
            list.add(resultSet.getString(1));
            list.add(resultSet.getString(7));
            for(int i = 2; i <= 6; i++){
                list.add(resultSet.getString(i));
            }
        }
        return list;
    }

    public void checkInComplete(String ticketCode) throws SQLException {
        String query = "update sql_ticketbooking.bookings set checkInStatus = 1 where ticketCode = '" + ticketCode + "'";
        statement.executeUpdate(query);
    }
}
