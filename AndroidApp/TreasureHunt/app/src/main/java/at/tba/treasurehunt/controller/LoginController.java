package at.tba.treasurehunt.controller;

/**
 * Created by dAmihl on 27.04.15.
 */

/**
 * Singleton
 * Handles login actions
 * Maybe GoogleServices will be used to log in
 */
public class LoginController {

    private static LoginController instance = null;

    public static LoginController getInstance(){
        if (instance == null) instance = new LoginController();
        return instance;
    }

    private LoginController(){}

}
