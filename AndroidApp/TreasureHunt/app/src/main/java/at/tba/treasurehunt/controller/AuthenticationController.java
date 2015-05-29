package at.tba.treasurehunt.controller;

/**
 * Created by dAmihl on 27.04.15.
 */

import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

import at.tba.treasurehunt.servercomm.ServerCommunication;
import at.tba.treasurehunt.tasks.IResponseCallback;
import at.tba.treasurehunt.utils.DummyDataProvider;

/**
 * Singleton
 * Handles login actions
 * Maybe GoogleServices will be used to log in
 */
public class AuthenticationController implements IResponseCallback {

    private static AuthenticationController instance = null;

    public static AuthenticationController getInstance(){
        if (instance == null) instance = new AuthenticationController();
        return instance;
    }

    private AuthenticationController(){}
    private IAuthenticationCallback callback;

    public void authenticateUser(String uName, String password, IAuthenticationCallback callback){
        this.callback = callback;
        ServerCommunication.getInstance().logInToServer(uName, password, this);
    }

    public void registerNewUser(String uName, String email, String password, String passwordRetype, IAuthenticationCallback callback){
        this.callback = callback;
        if (!password.equals(passwordRetype)){
            callback.onRegistrationError(AuthenticationError.REGISTRATION_WRONG_PASSWORD_RETYPE);
        }
        ServerCommunication.getInstance().registerUserOnServer(uName, email, password, this);
    }

    @Override
    public void onResponseReceived(JSONRPC2Response response) {
        if (response.toJSONObject().get("id").equals("0")){
            UserDataController.getInstance().setLoggedInUser(DummyDataProvider.getDummyUserData());
            callback.onAuthenticationSuccess();
        }else if (response.toJSONObject().get("id").equals("-1")){
            callback.onRegistrationSuccess();
        }else{
            callback.onRegistrationError(AuthenticationError.UNKNOWN_ERROR);
            callback.onAuthenticationFailure(AuthenticationError.UNKNOWN_ERROR);
        }
    }

    @Override
    public void onResponseReceiveError() {
        callback.onAuthenticationFailure(AuthenticationError.UNKNOWN_ERROR);
    }
}
