package main.model;

import main.Main;
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

    public Worker worker = null;

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
                    String workerUsername = resultSet.getString("email");
                    String workerPassword = resultSet.getString("password");
                    String workerQuestion1 = resultSet.getString("question1");
                    String workerAnswer1 = resultSet.getString("answer1");
                    String workerQuestion2 = resultSet.getString("question2");
                    String workerAnswer2 = resultSet.getString("answer2");
                    int uid = resultSet.getInt("id");
                    System.out.println("workerusername is :"+workerUsername);
                    worker = new Worker(workerUsername,workerPassword,workerQuestion1,workerAnswer1,workerQuestion2,workerAnswer2);
                    worker.uid = uid;

                    // set global login details
                    // yeah not very oop but works for now
                    Main.worker = worker;

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
