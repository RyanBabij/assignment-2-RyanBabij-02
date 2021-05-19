package main.model.interfaces;

import main.model.LoginModel;

public interface Account {

    public boolean validate(String email, String password);
    boolean recover(String answer1, String answer2);

}