package at.tba.treasurehunt.servercomm;

/**
 * Created by dAmihl on 11.05.15.
 */
public interface ServerComm {

    public boolean connectServer(ServerConnectionCallback callback);

    public boolean disconnectServer();

    public boolean sendPackage();

    public boolean isConnected();

}
