package frontend;

import frontend.TreasureServerEndpoint;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class TreasureServer{
    public static void main(String[] args) throws Exception {
        Server server = new Server(8887);

        ServletContextHandler sch = new ServletContextHandler();
        sch.setContextPath("/");
        sch.addServlet(TreasureServerEndpointServlet.class, "/loot");

        server.setHandler(sch);

        server.start();
        server.join();
    }

    public static class TreasureServerEndpointServlet extends WebSocketServlet {

        @Override
        public void configure(WebSocketServletFactory factory) {
            factory.register(TreasureServerEndpoint.class);
        }
    }
}
