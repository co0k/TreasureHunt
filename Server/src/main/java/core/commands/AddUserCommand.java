package core.commands;

import core.Command;
import core.CoreModel;
import data_structures.user.Inventory;
import data_structures.user.User;
import db.DatabaseController;

/**
 * Created by nebios on 01.06.15.
 */
public class AddUserCommand implements Command<Integer> {

    private User user;

    /**
     * This Command <br />
     * Creates a new User with an empty Inventory <br />
     * mandatory informations are the email address, username, and a hashed password
     * @param email
     * @param username
     * @param pwHash
     */
    public AddUserCommand(String email, String username, String pwHash) {
        this.user = new User(username, pwHash, email, 0, 0, new Inventory(null));
    }

    @Override
    public int getPriority() {
        return 80;
    }

    /**
     * Checks if the Username is used already and if not
     * asks the DatabaseController to insert the new User
     * @return  a token of type Interger if successful <br />
     *          null else
     * @throws InterruptedException
     */
    @Override
    public Integer execute() throws InterruptedException {
        if( DatabaseController.getInstance().getUser(user.getName()) != null )
            return null;
        int token = DatabaseController.getInstance().addUser(user);
		if( token != -1) {
			CoreModel.getInstance().setActive(token);
			return token;
		}
		return null;
    }
}
