package at.tba.treasurehunt.servercomm;

/**
 * Created by dAmihl on 11.05.15.
 */
public interface IServerConnection {

    public boolean connectServer(IServerConnectionCallback callback);

    public boolean disconnectServer();

    public boolean sendPackage();

    public boolean isConnected();

    public boolean sendMessage(String s);

}
