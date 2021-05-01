package main.controller;

import main.model.interfaces.Account;

import java.util.Vector;

public class AccountController {

    Vector<Account> vAccount;

    Account create(String email, String password, String question1, String answer1,
                   String question2, String answer2) {
        // return status for errors?
        return null;
    }

    boolean delete(Account account) {
        return false;
    }

    Account authorise(String email, String password) {
        return null;
    }

    Account recover(String email, String answer1, String answer2)
    {
        return null;
    }

    boolean makeAdmin(Account account)
    {
        return false;
    }
    boolean logout(Account account)
    {
        return false;
    }
}
