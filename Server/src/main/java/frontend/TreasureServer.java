package frontend;

import data_structures.treasure.Achievement;
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

            // TODO: this is thought for test purpose only! get rid of it!
            // Activate all treasures
            List<Integer> treasurechests = DatabaseController.getInstance().getallTreasureID(false);
            if (treasurechests == null) {
                Quiz quiz1 = new Quiz(10,"Aus was für einem Gebäude entstand das Landestheater?", "Ballspielhaus", "Rathaus", "Bank", "Konzerthaus", null, null);
                Quiz quiz2 = new Quiz(10,"Wo ist der Rechnerraum 15?", "Uni Innsbruck", "dein zuhause", "Bank", "Konzerthaus", null, null);
                Quiz quiz3 = new Quiz(10,"In welchen Gebäude befindet sich Frau Weber?", "ICT Gebäude", "Rathaus", "Bauingenieurgebäude", "Mensa", "Sowi", null);
                Quiz quiz4 = new Quiz(10,"Wo bekommt man den besten Kaffee am Campus?", "Jollys", "ICT Gebäude", "Bauingenieurgebäude", "Mensa", null, null);
                Quiz quiz5 = new Quiz(25,"Wofür ist der Alpenzoo bekannt?", "höchstgelegener Zoo Europas", "artenreichster Zoo Europas", "sauberster Zoo Europas", "was ist ein Zoo?", null, null);
                Quiz quiz6 = new Quiz(20,"Welches bekannte Snowboardevent findet alljährlich in Innsbruck statt?", "Air+Style", "PipetoPipe", "AlpinFreeze", "Rail Jam", null, null);
                Quiz quiz7 = new Quiz(15,"Wie viele vergoldete Kupferschindeln wurden beim goldenen Dachl verlegt?", "2.657", "1.529", "403", "86", null, null);
                Quiz quiz8 = new Quiz(20,"Wie viele Bäcker Ruetz gibt es in Innsbruck?", "16", "7", "pro Einwohner einen", "8", null, null);
                Quiz quiz9 = new Quiz(10,"Wann fanden die ersten Youth Olympic Games in Innsbruck statt?", "2012", "2011", "2008", "2001", null, null);
                Quiz quiz10 = new Quiz(20,"In welchem Jahr wurde Innsbruck Landeshauptstadt von Tirol?", "1849", "1912", "1731", "1818", null, null);
                Quiz quiz11 = new Quiz(30,"Wie viele unterschiedliche Fakultäten besitzt die Universität Innsbruck?", "16", "8", "10", "78", null, null);
                Quiz quiz12 = new Quiz(40,"Was ist das älteste bekannte Gemüse?", "Erbsen", "Karotten", "Birnen", "Äpfel", null, null);
                Quiz quiz13 = new Quiz(20,"Welche Gebirgskette war Schauplatz weltbekannter Filme, wie 'Geiger Wally' oder 'Sissi die Kaiserin'?", "Nordkette", "Silvretta", "Verwall", "Kaisergebirge", null, null);
                Quiz quiz14 = new Quiz(30,"Wieviele verschiedene Tierarten leben im Alpenzoo Innsbruck?", "150", "80", "15", "34", null, null);
                Quiz quiz15 = new Quiz(10,"Was ist auf den Wappen von Innsbruck zu sehen?", "Brücke", "Adler", "Krone", "Sichel", null, null);
                Quiz quiz16 = new Quiz(15,"Wie hoch liegt die Seegrube?", "1905m", "2003m", "1530m", "960m", null, null);
                Quiz quiz17 = new Quiz(35,"Wieviele Passagiere werden durchschnittlich pro Jahr am Innsbrucker Flughafen transportiert?","991.356", "421.758", "125.091", "12.000", null, null);
                Quiz quiz18 = new Quiz(35,"Wieviele Gemeinden grenzen an Innsbruck?", "16", "5", "10", "1000", null, null);
                Quiz quiz19 = new Quiz(25,"Auf welcher bekannten Münze wurde Schloss Ambras verewigt?", "silberne Euro-Gedenkmünze", "goldene Euro-Gedenkmünze", "Wiener Philharmoniker", "American Eagle", null, null);
                Quiz quiz20 = new Quiz(45,"Aus welchen Anlass wurde die Triumphpforte erbaut?", "Hochzeit von Erzherzog Leopold", "Hochzeit von Kaiserin Maria Theresia", "weil es gut aussieht", "Befehl von  Kaiser Franz Joseph", null, null);
                Quiz quiz21 = new Quiz(30,"Wieviele Brunnen stehen in der Innsbrucker Altstadt?", "12", "10", "6", "2", null, null);
                Quiz quiz22 = new Quiz(5,"Wie wurde der Inn früher genannt?", "Innanna", "Innanam", "Rudolf", "Innum", null, null);
                Quiz quiz23 = new Quiz(10,"Die wievielten Olympischen Winterspiele fanden im Jahre 1964 in Innsbruck statt?", "9", "7", "15", "4", null, null);
                Quiz quiz24 = new Quiz(15,"Wieviele Heilige schmücken den Podest der Annasäule?", "4", "5", "2", "3", null, null);
                Quiz quiz25 = new Quiz(25,"Wie wird das Tiroler Landesmuseum sonst noch genannt?", "Ferdinandeum", "Humboldtianum", "Mumok", "Albertina", null, null);
                Quiz quiz26 = new Quiz(20,"Wieviele Stufen musst du gehen um die oberste Plattform des Stadtturms zu erreichen", "148", "2503", "257", "1102", null, null);
                Quiz quiz27 = new Quiz(30,"Was versteht man O-Dorf?", "Olympisches Dorf", "Ossiacher Dorf", "Ober-Dorf", "Ochsen-Dorf", null, null);
                Quiz quiz28 = new Quiz(35,"Wieviele Bezirkshauptmänner besitzt Tirol?", "8", "7", "3","9", null, null);
                Quiz quiz29 = new Quiz(40,"Wann wurde Stift Wilten gegründet?", "880", "1012", "1203", "1621", null, null);
                Quiz quiz30 = new Quiz(45,"Wann wurde die Leopold-Franzens-Universität gegründet?", "1669", "1578", "1861", "1747", null, null);
                Quiz quiz31 = new Quiz(10,"Mit wievielen Fakultäten wurde die Leopold-Franzens-Universität gegründet?", "4", "10", "7", "17", null, null);
                Quiz quiz32 = new Quiz(25,"Wieviele Fakultäten befinden sich auf den Technik Campus?", "4", "5", "7", "2", null, null);
                Quiz quiz33 = new Quiz(10,"Wieviele Fakultäten befinden sich auf den Innrain Campus?", "8", "10", "7", "4", null, null);
                Quiz quiz34 = new Quiz(25,"Wieviele Sitzplätze fasst das Tiroler Landestheater?", "1050", "700", "259", "150", null, null);
                Quiz quiz35 = new Quiz(5,"Wie hieß der Architekt des Tiroler Landestheaters?", "Christoph Gumpp", "Josef Weichenberger", "Dietmar Feichtinger", "Heinz Tesar", null, null);
                Quiz quiz36 = new Quiz(30,"Wo befindet sich das Grabmal von Kaiser Maximilians I.?", "Hofkirche", "Stift Wilten", "Friedhof Maria-Hilf", "Hofgarten", null, null);
                Quiz quiz37 = new Quiz(35,"Wie wird die Hofkirche sonst noch genannt?", "Schwarzmander-Kirche", "Abteikirche", "Westminster Abbey", "Marienkirche", null, null);
                Quiz quiz38 = new Quiz(40,"Aus welchen Material bestehen die Schindeln des goldenen Dachl?", "Kupfer", "Eisen", "Magnesium", "Ton", null, null);
                Quiz quiz39 = new Quiz(35,"Wieviele Bezirke hat Tirol?", "9", "7", "3","9", null, null);
                Quiz quiz40 = new Quiz(35,"Wer entwarf das Design der neuen Hungerburgbahn/Bergiselschanze?", "Zaha Hadid", "Le Corbusier", "Friedensreich Hundertwasser","Tom Walek", null, null);


                List<Integer> exampleTreasuresID = new ArrayList<Integer>();
                List<Treasure> exampleTreasures = new ArrayList<Treasure>();

                exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.26952, 11.39570), quiz1, new Treasure.Size(-1, 20, 1), new Coupon(10, "SuperDuperMarket", 10.50)));
                exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.263372, 11.345269), quiz2, new Treasure.Size(-1, 20, 1), new Coupon(10, "SuperDuperMarket", 20.50)));
                exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.263567, 11.345916), quiz3, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.264659,11.3445717), quiz4, new Treasure.Size(-1, 20, 1), new Coupon(10, "Hofer", 10.50)));
                exampleTreasures.add(new Treasure(new Treasure.Location(25, 47.263567, 11.345916), quiz5, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(20, 47.249058,11.399484), quiz6, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.2686516, 11.393286), quiz7, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.2675584, 11.3923194), quiz7, new Treasure.Size(-1, 20, 1), new Coupon(10, "Hofer", 20.50)));
                exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.267785, 11.390727), quiz7, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(30, 47.2635802, 11.3945087), quiz8, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(30, 47.2654258,11.3936075), quiz8, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.2634968,11.3996253), quiz9, new Treasure.Size(-1, 20, 1), new Coupon(10, "SPAR", 10.00)));
                exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.2617463,11.4057584), quiz10, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(30, 47.2639078,11.3858713), quiz11, new Treasure.Size(-1, 20, 1), new Coupon(10, "SPAR", 10.00)));
                exampleTreasures.add(new Treasure(new Treasure.Location(20, 47.2675885,11.3916753), quiz12, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(25, 47.2687439,11.39429), quiz13, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.2743348,11.4126041), quiz14, new Treasure.Size(-1, 20, 1), new Coupon(10, "SPAR", 20.00)));
                exampleTreasures.add(new Treasure(new Treasure.Location(5, 47.2719325,11.405051), quiz15, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(5, 47.2678976,11.3937696), quiz15, new Treasure.Size(-1, 20, 1), new Coupon(10, "Billa", 5.00)));
                exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2644267,11.3896575), quiz15, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(20, 47.3046995,11.3795509), quiz16, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2580885,11.3885631), quiz17, new Treasure.Size(-1, 20, 1), new Coupon(10, "SPAR", 5.00)));
                exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2578809,11.3538982), quiz17, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2578809,11.3538982), quiz18, new Treasure.Size(-1, 20, 1), new Coupon(10, "Hofer", 20.50)));
                exampleTreasures.add(new Treasure(new Treasure.Location(30, 47.2635993,11.3457473), quiz18, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.256583,11.433619), quiz19, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(30, 47.2657582,11.3991417), quiz20, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(20, 47.2683515,11.393286), quiz21, new Treasure.Size(-1, 20, 1), new Coupon(10, "Hofer", 20.50)));
                exampleTreasures.add(new Treasure(new Treasure.Location(25, 47.2681768,11.3932941), quiz21, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.2684325,11.3921717), quiz21, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.2685103,11.3911598), quiz22, new Treasure.Size(-1, 20, 1), new Coupon(10, "Hofer", 15.00)));
                exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.2688161,11.3906951), quiz22, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(20, 47.265382,11.3862387), quiz22, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2579115,11.4013664), quiz23, new Treasure.Size(-1, 20, 1), new Coupon(10, "Hofer", 15.00)));
                exampleTreasures.add(new Treasure(new Treasure.Location(40, 47.2658625,11.396989), quiz24, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(45, 47.2658625,11.400272), quiz25, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(50, 47.2679908,11.3942032), quiz26, new Treasure.Size(-1, 20, 1), new Coupon(10, "Billa", 5.00)));
                exampleTreasures.add(new Treasure(new Treasure.Location(20, 47.2676414,11.3914834), quiz27, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.2662797,11.3902968), quiz28, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.2626123,11.3886235), quiz28, new Treasure.Size(-1, 20, 1), new Coupon(10, "Billa", 5.00)));
                exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2642062,11.3446889), quiz28, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(40, 47.253718,11.400588), quiz29, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(45, 47.2622315,11.3843836), quiz30, new Treasure.Size(-1, 20, 1), new Achievement(50, "University History Master", "Wow you knew the date when the university of Innsbruck was opened, you earned 50 extra points")));
                exampleTreasures.add(new Treasure(new Treasure.Location(50, 47.2622315,11.3843836), quiz31, new Treasure.Size(-1, 20, 1), new Coupon(10, "SPAR", 50.00)));
                exampleTreasures.add(new Treasure(new Treasure.Location(55, 47.2648144,11.34629), quiz32, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(30, 47.2632581,11.3857194), quiz33, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2645359,11.3859286), quiz33, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(20, 47.269503,11.396033), quiz34, new Treasure.Size(-1, 20, 1), new Coupon(10, "Billa", 5.00)));
                exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.269503,11.396033), quiz35, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.268511,11.39544), quiz36, new Treasure.Size(-1, 20, 1), new Coupon(10, "Billa", 5.00)));
                exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.268511,11.39544), quiz37, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(5, 47.268511,11.39544), quiz38, new Treasure.Size(-1, 20, 1), null));
                exampleTreasures.add(new Treasure(new Treasure.Location(5, 47.2733084,11.3977661), quiz39, new Treasure.Size(-1, 20, 1), new Coupon(10, "Billa", 5.00)));
                exampleTreasures.add(new Treasure(new Treasure.Location(5, 47.2693877,11.4025977), quiz40, new Treasure.Size(-1, 20, 1), null));

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
