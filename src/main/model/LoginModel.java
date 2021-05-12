package main.model;

import main.SQLConnection;
import main.model.account.Admin;
import main.model.account.Worker;
import main.model.interfaces.Account;
import org.sqlite.SQLiteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {

    Connection connection;

    public boolean isAdmin;

    public LoginModel(){

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    public boolean isDbConnected(){
        try {
            return !connection.isClosed();
        }
        catch(Exception e){
            return false;
        }
    }

    public boolean isLogin(String user, String pass) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from user where email = ? and password= ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) // if there's a hit
            {
                boolean isAdmin = resultSet.getBoolean("isAdmin");

                // build the account object

                if (isAdmin)
                {
                    Admin admin = new Admin("a","a","a","a","a","a");

                    System.out.println("ADMIN");
                    this.isAdmin=true;
                }
                else
                {
                    Worker worker = new Worker("a","a","a","a","a","a");

                    System.out.println("Not admin");
                    this.isAdmin=false;
                }
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }finally {
           preparedStatement.close();
           resultSet.close();
        }

    }

}
