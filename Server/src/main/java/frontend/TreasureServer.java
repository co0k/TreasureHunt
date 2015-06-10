package frontend;

import data_structures.treasure.Coupon;
import data_structures.treasure.Quiz;
import data_structures.treasure.Treasure;
import db.DatabaseController;
import db.manager.DatabaseManager;
import frontend.TreasureServerEndpoint;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

            // Activate all treasures
            List<Integer> treasurechests = DatabaseController.getInstance().getallTreasureID(false);
            if (treasurechests == null) {
                Quiz quiz1 = new Quiz(10, "Aus was für einem Gebäude entstand das Landestheater?", "Ballspielhaus", "Rathaus", "Bank", "Konzerthaus", null, null);
                Quiz quiz2 = new Quiz(10, "Wo ist der Rechnerraum 15?", "Uni Innsbruck", "dein zuhause", "Bank", "Konzerthaus", null, null);
                Quiz quiz3 = new Quiz(10, "In welchen Gebäude befindet sich Frau Webber?", "ICT Gebäude", "Rathaus", "Bauingenieurgebäude", "Mensa", "Bei dir zuhause", null);
                Quiz quiz4 = new Quiz(10, "Wo bekommt man den besten Kaffee am Campus?", "Jollys", "ICT Gebäude", "Bauingenieurgebäude", "Mensa", null, null);
                Quiz quiz5 = new Quiz(25, "Wofür ist der Alpenzoo bekannt?", "höchstgelegener Zoo Europas", "artenreichster Zoo Europas", "sauberster Zoo Europas", "was ist ein Zoo?", null, null);
                Quiz quiz6 = new Quiz(20, "Welches bekannte Snowboardevent findet alljährlich in Innsbruck statt?", "Air+Style", "PipetoPipe", "AlpinFreeze", "Rail Jam", null, null);
                Quiz quiz7 = new Quiz(15, "Wie viele vergoldete Kupferschindeln wurden beim goldenen Dachl verlegt?", "2.657", "1.529", "403", "86", null, null);
                Quiz quiz8 = new Quiz(20, "Wie viele Bäcker Ruetz gibt es in Innsbruck?", "16", "7", "pro Einwohner einen", "8", null, null);
                Quiz quiz9 = new Quiz(10, "Wann fanden die ersten Youth Olympic Games in Innsbruck statt?", "2012", "2011", "2008", "2001", null, null);
                Quiz quiz10 = new Quiz(20, "In welchem Jahr wurde Innsbruck Landeshaupt von Tirol?", "1849", "1912", "1731", "1818", null, null);
                Quiz quiz11 = new Quiz(30, "Wie viele unterschiedliche Fakultäten besitzt die Universität Innsbruck?", "16", "8", "10", "78", null, null);
                Quiz quiz12 = new Quiz(40, "Was ist das älteste bekannte Gemüse?", "Erbsen", "Karotten", "Birnen", "Äpfel", null, null);
                Quiz quiz13 = new Quiz(20, "Welche Gebirgskette war Schauplatz weltbekannter Filme, wie 'Geiger Wally' oder 'Sissi die Kaiserin'?", "Nordkette", "Silvretta", "Verwall", "Kaisergebirge", null, null);
                Quiz quiz14 = new Quiz(30, "Wieviele verschiedene Tierarten leben im Alpenzoo Innsbruck?", "150", "80", "15", "34", null, null);
                Quiz quiz15 = new Quiz(10, "Was ist auf den Wappen von Innsbruck zu sehen?", "Brücke", "Adler", "Krone", "Sichel", null, null);
                Quiz quiz16 = new Quiz(15, "Wie hoch liegt die Seegrube?", "1905m", "2003m", "1530m", "960m", null, null);
                Quiz quiz17 = new Quiz(35, "Wieviele Passagiere werden durchschnittlich pro Jahr am Innsbrucker Flughafen transportiert?", "16", "5", "10", "1000", null, null);
                Quiz quiz18 = new Quiz(35, "Wieviele Gemeinden grenzen an Innsbruck?", "16", "5", "10", "1000", null, null);
                Quiz quiz19 = new Quiz(35, "Auf welcher bekannten Münze wurde Schloss Ambras verewigt?", "silberne Euro-Gedenkmünze", "goldene Euro-Gedenkmünze", "Wiener Philharmoniker", "American Eagle", null, null);
                Quiz quiz20 = new Quiz(35, "Aus welchen Anlass wurde die Triumphpforte erbaut?", "Hochzeit von Erzherzog Leopold", "Hochzeit von Kaiserin Maria Theresia", "weil es gut aussieht", "Befehl von  Kaiser Franz Joseph", null, null);


                List<Integer> exampleTreasuresID = new ArrayList<Integer>();
                List<Treasure> exampleTreasures = new ArrayList<Treasure>();

                exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.26952, 11.39570), quiz1, new Treasure.Size(-1, 20, 1), new Coupon(10, "SuperDuperMarket", 10.50)));
                exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.263372, 11.345269), quiz2, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.263567, 11.345916), quiz3, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.264659, 11.3445717), quiz4, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(25, 47.263567, 11.345916), quiz5, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(20, 47.249058, 11.399484), quiz6, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.2686516, 11.393286), quiz7, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.2675584, 11.3923194), quiz7, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.267785, 11.390727), quiz7, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(30, 47.2635802, 11.3945087), quiz8, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(30, 47.2654258, 11.3936075), quiz8, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.2634968, 11.3996253), quiz9, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.2617463, 11.4057584), quiz10, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(30, 47.2639078, 11.3858713), quiz11, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(20, 47.2675885, 11.3916753), quiz12, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(25, 47.2687439, 11.39429), quiz13, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.2743348, 11.4126041), quiz14, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(5, 47.2719325, 11.405051), quiz15, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(5, 47.2678976, 11.3937696), quiz15, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2644267, 11.3896575), quiz15, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(20, 47.3046995, 11.3795509), quiz16, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2580885, 11.3885631), quiz17, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2578809, 11.3538982), quiz17, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2578809, 11.3538982), quiz18, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(30, 47.2635993, 11.3457473), quiz18, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.256583, 11.433619), quiz19, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2657582, 11.3991417), quiz20, new Treasure.Size(-1, 20, 1), null));

                Collections.sort(exampleTreasures);
                // add all the treasures
                for (Treasure t : exampleTreasures)
                    exampleTreasuresID.add(DatabaseController.getInstance().saveTreasure(t));
                for (Integer tmp : exampleTreasuresID) {
                    DatabaseController.getInstance().activateTreasure(tmp);
                }
            } else {
                for (Integer treasurechest : treasurechests)
                    DatabaseController.getInstance().activateTreasure(treasurechest);
            }

            server.start();
            server.dump(System.err);
            server.join();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
