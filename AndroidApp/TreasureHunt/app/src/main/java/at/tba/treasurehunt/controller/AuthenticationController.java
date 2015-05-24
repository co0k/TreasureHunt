package at.tba.treasurehunt.controller;

/**
 * Created by dAmihl on 27.04.15.
 */

import at.tba.treasurehunt.servercomm.ServerCommunication;
import at.tba.treasurehunt.servercomm.ServerConnection;
import at.tba.treasurehunt.utils.DummyDataProvider;

/**
 * Singleton
 * Handles login actions
 * Maybe GoogleServices will be used to log in
 */
public class AuthenticationController {

    private static AuthenticationController instance = null;

    public static AuthenticationController getInstance(){
        if (instance == null) instance = new AuthenticationController();
        return instance;
    }

    private AuthenticationController(){}

    public boolean authenticateUser(String uName, String password, IAuthenticationCallback callback){
        boolean result = ServerCommunication.getInstance().logInToServer(uName, password);

        if (result)
            UserDataController.getInstance().setLoggedInUser(DummyDataProvider.getDummyUserData());

        return result;
    }

    public boolean registerNewUser(String uName, String email, String password, String passwordRetype, IAuthenticationCallback callback){
        if (!password.equals(passwordRetype)){
            callback.onRegistrationError(AuthenticationError.REGISTRATION_WRONG_PASSWORD_RETYPE);
            return false;
        }
        callback.onRegistrationError(AuthenticationError.UNKNOWN_ERROR);
        return false;
    }

}
