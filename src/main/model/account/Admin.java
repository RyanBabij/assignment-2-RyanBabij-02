package main.model.account;

import main.model.LoginModel;
import main.model.booking.Booking;
import main.model.interfaces.Account;

import java.util.Vector;

public class Admin implements Account {

    private String email;
    private String password;
    private String question1;
    private String question2;
    private String answer1;
    private String answer2;

    public Admin(String email, String password, String question1, String answer1,
                   String question2, String answer2) {
        this.email = email;
        this.password = password;
        this.question1 = question1;
        this.question2 = question2;
        this.answer1 = answer1;
        this.answer2 = answer2;
    }

    public boolean validate (String user, String password)
    {
        /*
        try {
            LoginModel lm = new LoginModel();
            if (lm.isLogin(email, password)) {
                return true;

            }
        }
        catch(Exception e)
        {
            // return false by default
        }
        return false;
         */
        return true;
    }
    public boolean recover(String answer1, String answer2)
    {
        if (answer1.equals(this.answer1) && answer2.equals(this.answer2))
        {
            return true;
        }
        return false;
    }

    public String getEmail()
    {
        return email;
    }

    String getReport()
    {
        return null;
    }
    boolean alterBooking()
    {
        return false;
    }
    boolean deleteBooking(Booking booking)
    {
        return false;
    }
    boolean deleteAccount(Account account)
    {
        return false;
    }

    Vector<Booking> getAllBookings()
    {
        return null;
    }
    Vector<Account> getAllAccounts()
    {
        return null;
    }
}
