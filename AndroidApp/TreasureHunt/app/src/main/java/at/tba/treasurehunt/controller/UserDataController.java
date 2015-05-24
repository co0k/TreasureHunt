package at.tba.treasurehunt.controller;

/**
 * Created by dAmihl on 27.04.15.
 */


import at.tba.treasurehunt.utils.DummyDataProvider;
import data_structures.user.User;

/**
 * Singleton
 * Gets user data for UserProfile Page
 */
public class UserDataController {

    private static UserDataController instance = null;

    private  User LOGGED_IN_USER = null;

    public static UserDataController getInstance(){
        if (instance == null) instance = new UserDataController();
        return instance;
    }

    private UserDataController(){}


    public User getCurrentUserData(){return getLoggedInUser();
    }

    public void setLoggedInUser(User u){
        this.LOGGED_IN_USER = u;
    }

    public User getLoggedInUser(){
        return LOGGED_IN_USER;
    }





}
