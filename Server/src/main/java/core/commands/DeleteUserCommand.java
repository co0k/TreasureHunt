package core.commands;

import core.Command;
import db.DatabaseController;

/**
 * Created by nebios on 10.06.15.
 */
public class DeleteUserCommand implements Command<Boolean>{

    private Integer userID;

    /**
     * Creates a new DeleteUserCommand
     * @param userID    id of the user, who will be deleted
     */
    public DeleteUserCommand(Integer userID) {
        this.userID = userID;
    }

    @Override
    public int getPriority() {
        return 30;
    }

    /**
     * Checks if the user exists and deletes him/her so
     * @return true if the user was successfully deleted <br />
     *          false otherwise
     * @throws InterruptedException
     */
    @Override
    public Boolean execute() throws InterruptedException {
        if(DatabaseController.getInstance().getUser(userID) != null)
            return DatabaseController.getInstance().deleteUser(userID);
        return false;
    }
}
