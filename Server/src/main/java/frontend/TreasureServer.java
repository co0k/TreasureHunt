package frontend;

import frontend.TreasureServerEndpoint;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class TreasureServer {
    public static void main(String[] args) throws Exception {

        Server server = new Server(7666);

        ServletContextHandler servletContext = new ServletContextHandler();
        servletContext.setContextPath("/loot");

        server.setHandler(servletContext);

        try {
            // Initialize javax.websocket layer
            ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(servletContext);

            // Add WebSocket endpoint to javax.websocket layer
            wscontainer.addEndpoint(TreasureServerSocket.class);

            server.start();
            server.dump(System.err);
            server.join();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
