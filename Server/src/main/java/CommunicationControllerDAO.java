import java.util.concurrent.Future;

/**
 * only one method is needed, since the rest is implemented as Commands in the Core Model
 */
public interface CommunicationControllerDAO<V> {
	/**
	 * this method adds a command to the command queue in the Core Model, which is executing those commands
	 * in it's order(can be FIFO or Priority Queue(preferred choice), etc.)
	 *
	 * @param command an instance of command, for a full list see in core.commands
	 * @return a Future of the value the command is implementing
	 */
	Future<V> addCommand(core.Command<V> command);
}
