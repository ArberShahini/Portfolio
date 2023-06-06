import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDataBase extends DataBase{
    private String tableName;
    private boolean accountLoginState = false;
    AccountDataBase(String tableName){
        super();
        this.tableName = tableName;
    }

    public void authenticate(String username, String password) throws SQLException {
        String q = "select * from sql_ticketbooking." + tableName + " where binary username like '%" + username + "%';";
        ResultSet user = statement.executeQuery(q);
        if(!user.next()){
            JOptionPane.showMessageDialog(null, "User does not exist");
        }else{
            if(user.getString(2).compareTo(password) != 0)
                JOptionPane.showMessageDialog(null, "Password and user do not match");
            else{
                JOptionPane.showMessageDialog(null, "Login successful");
                accountLoginState = true;
            }
        }
    }

    public void register(String username, String password, String passwordDupe) throws SQLException {
        String existCheckQuery = "select * from sql_ticketbooking." + tableName + " where binary username like '%" + username + "%';";
        String registerUserQuery = "insert into sql_ticketbooking." + tableName + " values ('" + username + "', '" + password + "');";
        ResultSet existenceCheck = statement.executeQuery(existCheckQuery);
        if(!existenceCheck.next()){
            if(password.compareTo(passwordDupe) == 0){
                statement.executeUpdate(registerUserQuery);
                JOptionPane.showMessageDialog(null, "Registration Successful");
            }else{
                JOptionPane.showMessageDialog(null, "Passwords do not match");
            }
        }else{
            JOptionPane.showMessageDialog(null, "User already exists");
        }
    }

    public boolean isLoggedIn(){
        return accountLoginState;
    }

    public void setAccountLoginStatenStateFalse(){
        accountLoginState = false;
    }

    public void changePassword(String username, String oldPassword, String newPassword, String newPasswordDupe) throws SQLException {
        String changeQuery = "update sql_ticketbooking." + tableName + " set password = '" + newPassword + "' " +
                "where binary username like '%" + username + "%';";
        String passwordCheckQuery = "select password from sql_ticketbooking." + tableName + " where binary username like '%" + username + "%'";
        ResultSet passwordCheck = statement.executeQuery(passwordCheckQuery);
        if(newPassword.compareTo(newPasswordDupe) != 0){
            JOptionPane.showMessageDialog(null, "Passwords do not match");
            return;
        }
        if(passwordCheck.next()){
            if(passwordCheck.getString(1).compareTo(oldPassword) == 0){
                statement.executeUpdate(changeQuery);
                JOptionPane.showMessageDialog(null, "Password changed successfully");
                return;
            }else{
                JOptionPane.showMessageDialog(null, "Incorrect credentials, try again");
                return;
            }
        }
    }
}
