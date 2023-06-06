import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CheckInDataBase extends DataBase{
    CheckInDataBase(){
        super();
    }

    public void addToDatabase(String ownerUsername, String ticketCode, String nationality, String passportID, String expDate, String dateOfIssue, String email, String phonenumber)
            throws SQLException
    {
        String searchQuery = "select checkInStatus from sql_ticketbooking.bookings where ticketCode = '" + ticketCode + "'";
        ResultSet resultSet = statement.executeQuery(searchQuery);
        resultSet.next();
        if(resultSet.getInt(1) == 0){
            String query = "insert into sql_ticketbooking.`check-in` values ('" + ownerUsername + "', '" + ticketCode + "', '" + nationality + "', '" + passportID +
                    "', '" + expDate + "', '" + dateOfIssue + "', '" + email + "', '" + phonenumber + "')";
            statement.executeUpdate(query);
        }else{
            JOptionPane.showMessageDialog(null, "Check-in has already been completed");
        }
    }

    public ArrayList<String> getTableCodes(String loggedUser) throws SQLException {
        String query = "select ticketCode from sql_ticketbooking.`check-in` where ownerUsername = '" + loggedUser + "'";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> list = new ArrayList<>();

        while(resultSet.next()){
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public ResultSet getTicketCheckInInfo(String ticketCode) throws SQLException {
        String query = "select * from sql_ticketbooking.`check-in` where ticketCode = '" + ticketCode + "'";
        return statement.executeQuery(query);
    }
}
