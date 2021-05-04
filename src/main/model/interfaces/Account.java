package main.model.interfaces;

import main.model.LoginModel;

public class Account {

    private String email;
    private String password;
    private String question1;
    private String question2;
    private String answer1;
    private String answer2;

    public Account(String email, String password, String question1, String answer1,
                   String question2, String answer2) {
        this.email = email;
        this.password = password;
        this.question1 = question1;
        this.question2 = question2;
        this.answer1 = answer1;
        this.answer2 = answer2;
    }

    boolean validate(String email, String password)
    {
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
    }
    boolean recover(String answer1, String answer2)
    {
        return false;
    }

}