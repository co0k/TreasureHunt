package at.tba.treasurehunt.utils;


import java.util.ArrayList;

import data_structures.treasure.Coupon;
import data_structures.treasure.Quiz;
import data_structures.treasure.Treasure;
import data_structures.user.HighscoreList;
import data_structures.user.Inventory;
import data_structures.user.User;

/**
 * Created by dAmihl on 28.04.15.
 *
 * This class is only for simple test purposes and will not be used in later development.
 * A Mocking framework (like JMockit) will be used.
 */


public class DummyDataProvider {

    /**
     * Provides Dummy user data
     * @return User object
     */
   public static User getDummyUserData(){
       return new User(1,"Testuser","test111","test@email",1,200, getDummyInventory());
   }

   public static Treasure getDummyTreasureData(int pos){
       return new Treasure(getDummyTreasureHuntLocation(pos),getDummyQuiz(),new Treasure.Size(1,20,1),null);
   }

    /**
     * Returns a dummy location in Innsbruck.
     * The latitude of Innsbruck, Tyrol, Austria is 47.259659, and the longitude is 11.400375
     *
     * Uni Technik Location Bauingenieur:
     * 47.2641234/11.3451889
     * @return
     */
   public static Treasure.Location getDummyTreasureHuntLocation(int pos){
       switch (pos){
           case 0: return new Treasure.Location(1, 100, 47.259659, 11.400375);
           case 1: return new Treasure.Location(2, 100, 47.264097, 11.343373);
           case 2: return new Treasure.Location(3, 100, 47.319443, 11.073080);
           case 3: return new Treasure.Location(4, 100, 47.2641234,11.3451889);
           case 4: return new Treasure.Location(5, 100, 47.232164, 11.441617);
       }
       //Innsbruck Goldenes Dachl
       // return new Treasure.Location(1, 100, 47.259659, 11.400375);
       // Innsbruck Technik Hess Haus 47.264097, 11.343373
       //return new Treasure.Location(1, 100, 47.264097, 11.343373);
       // Telfs 47.319443, 11.073080
       return new Treasure.Location(1, 100, 47.319443, 11.073080);
   }

   public static Quiz getDummyQuiz(){
       return new Quiz(10,"Wo is waldo?", "KA", "ka mann", "hinterm baum?", "da hinten", "da oben", "gar nit da");
   }

    public static HighscoreList getDummyHighscoreList(){
        ArrayList<HighscoreList.Entry> list = new ArrayList<>();

        for (int i = 1; i <= 10; i++)
            list.add(new HighscoreList.Entry(i, "Player "+i, i, 1000/i));

        HighscoreList score = new HighscoreList(0,list);
        return score;
    }

    public static Inventory getDummyInventory(){
        Inventory inventory = new Inventory();
        inventory.addEntry(new Inventory.Entry(1, new Coupon(12,"dummy",100) {
            @Override
            public String getType() {
                return "Dingens1";
            }

            @Override
            public int getXP() {
                return 10;
            }

            @Override
            public int getId() {
                return 1;
            }
        }));
        inventory.addEntry(new Inventory.Entry(1, new Coupon(12,"dummy",100) {
            @Override
            public String getType() {
                return "Dingens2";
            }

            @Override
            public int getXP() {
                return 10;
            }

            @Override
            public int getId() {
                return 1;
            }
        }));
        return inventory;
    }

}
