package at.tba.treasurehunt.tasks;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

/**
 * Created by dAmihl on 29.05.15.
 */
public interface IResponseCallback {

    public void onResponseReceived(JSONRPC2Response response);

    public void onResponseReceiveError();

}
