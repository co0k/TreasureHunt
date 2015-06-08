import at.tba.treasurehunt.utils.HotColdManager;
import at.tba.treasurehunt.utils.MapLocationHelper;
import data_structures.treasure.Quiz;
import data_structures.treasure.Treasure;
import data_structures.user.Inventory;
import data_structures.user.User;
import data_structures.*;
import com.google.android.gms.maps.model.LatLng;

import junit.framework.TestCase;

import org.junit.Test;

public class FirstTest extends TestCase {

    public FirstTest() {super();}

    @Test
    public void testUser(){
        User testUser = new User(1, "TestUser", "test111", "test@email", 1, 200, new Inventory(null));
        assertEquals(testUser.getId(), 1);
    }

    @Test
    public void testQuiz(){
        Quiz testQuiz = new Quiz(1, "Name the first album of Nirvana", "Bleach", "In Utero", "Nevermind", "In Bloom", "From The Muddy Banks of Wishkah", "Heartshaped Box");
        Treasure testTreasure = new Treasure(new Treasure.Location(5, 100, 47.232164, 11.441617), testQuiz, new Treasure.Size(1, 20, 1), null);
        assertEquals(testQuiz.getXP(), 1);
        assertEquals(testTreasure.getLocation(), new Treasure.Location(5, 100, 47.232164, 11.441617));
    }

    @Test
    public void testHotCold(){
        HotColdManager.HOT_COLD_STATE testCase = HotColdManager.HOT_COLD_STATE.HOT;
        assertEquals(testCase, HotColdManager.determineHotCold(175));
        assertNotSame(testCase, HotColdManager.determineHotCold(800));
    }


    @Test
    public void testMapLocation(){
        MapLocationHelper testMapLocation = new MapLocationHelper();
        LatLng testLatLng = new LatLng(47.232164, 11.441617);
        assertEquals(0, testMapLocation.isInRange(testLatLng, testLatLng, 0));
        assertEquals(true, testMapLocation.distanceBetween(testLatLng, testLatLng));
    }
}