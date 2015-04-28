package at.tba.treasurehunt.utils;

import at.tba.treasurehunt.datastructures.treasure.Quiz;
import at.tba.treasurehunt.datastructures.treasure.Treasure;
import at.tba.treasurehunt.datastructures.user.Inventory;
import at.tba.treasurehunt.datastructures.user.User;

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
       return new User(1,"Testuser","test111","test@email",1,200, new Inventory(null));
   }

   public static Treasure getDummyTreasureData(){
       return new Treasure(1,20,getDummyTreasureHuntLocation(),getDummyQuiz(),new Treasure.Size(1,20,1),null,0);
   }

    /**
     * Returns a dummy location in Innsbruck.
     * The latitude of Innsbruck, Tyrol, Austria is 47.259659, and the longitude is 11.400375
     *
     * Uni Technik Location Bauingenieur:
     * 47.2641234/11.3451889
     * @return
     */
   public static Treasure.Location getDummyTreasureHuntLocation(){
       //Innsbruck Goldenes Dachl
       // return new Treasure.Location(1, 100, 47.259659, 11.400375);
       // Innsbruck Technik Hess Haus 47.264097, 11.343373
       return new Treasure.Location(1, 100, 47.264097, 11.343373);
   }

   public static Quiz getDummyQuiz(){
       return new Quiz(1,1, "Wo is waldo?", "KA", "ka mann", null, null, null, null, 0);
   }

}
