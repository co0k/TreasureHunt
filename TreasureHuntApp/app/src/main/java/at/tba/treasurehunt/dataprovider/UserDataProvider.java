package at.tba.treasurehunt.dataprovider;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.tba.treasurehunt.servercomm.ServerCommunication;
import at.tba.treasurehunt.tasks.IResponseCallback;
import communication_controller.json.JsonConstructor;
import data_structures.user.User;

/**
 * Created by dAmihl on 08.06.15.
 */
public class UserDataProvider implements IResponseCallback {

    private static UserDataProvider instance = null;

    public static UserDataProvider getInstance(){
        if (instance == null) instance = new UserDataProvider();
        return instance;
    }

    private IUserLoadedCallback userCallback;

    public void getUserFromServer(int userID, IUserLoadedCallback callback){
        this.userCallback = callback;
        ServerCommunication.getInstance().getUserById(userID, this);
    }


    @Override
    public void onResponseReceived(JSONRPC2Response response) {
        JsonConstructor constr = new JsonConstructor();
        User res = constr.fromJson((String)response.getResult(), User.class);
        userCallback.onUserLoadedSuccess(res);
    }

    @Override
    public void onResponseReceiveError() {
        userCallback.onUserLoadedFailure();
    }
}
