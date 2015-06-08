package at.tba.treasurehunt.servercomm;

/**
 * Created by dAmihl on 17.05.15.
 */
public interface IServerConnectionCallback {

    public void onServerConnected();

    public void onServerNotConnected();

}
