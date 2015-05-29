package at.tba.treasurehunt.tasks;

/**
 * Created by dAmihl on 29.05.15.
 */
public interface ITaskCallback {

    public void onTaskCancelled();
    public void onTaskPostExecute();

}
