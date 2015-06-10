package at.tba.treasurehunt.servercomm;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;

/**
 * Created by dAmihl on 11.05.15.
 */
public interface IServerConnection {

    boolean connectServer(IServerConnectionCallback callback);

    boolean disconnectServer();

    boolean sendPackage();

    boolean isConnected();

    boolean sendMessage(String s);

    boolean sendJSONRequest(JSONRPC2Request req);
}
