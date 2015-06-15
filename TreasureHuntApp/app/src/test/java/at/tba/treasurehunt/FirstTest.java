package at.tba.treasurehunt;

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
        assertEquals(testUser.getName(), "TestUser");
        assertEquals(testUser.getPasswordHash(), "test111");
        assertEquals(testUser.getEmail(), "test@email");
        assertEquals(testUser.getRank(), 200);
        assertEquals(testUser.getXP(), 1);
    }

    @Test
    public void testQuiz(){
        Quiz testQuiz = new Quiz(1, "Name the first album of Nirvana", "Bleach", "In Utero", "Nevermind", "In Bloom", "From The Muddy Banks of Wishkah", "Heartshaped Box");
        Treasure testTreasure = new Treasure(new Treasure.Location(5, 100, 47.232164, 11.441617), testQuiz, new Treasure.Size(1, 20, 1), null);
        assertEquals(testQuiz.getXP(), 1);
        assertEquals(testTreasure.getLocation(), new Treasure.Location(5, 100, 47.232164, 11.441617));
        assertEquals(testQuiz.getAnswer1(), "Bleach");
    }

    @Test
    public void testHotCold(){
        HotColdManager.HOT_COLD_STATE testCase = HotColdManager.HOT_COLD_STATE.HOT;
        assertEquals(testCase, HotColdManager.determineHotCold(175));
        assertNotSame(testCase, HotColdManager.determineHotCold(800));
        HotColdManager.HOT_COLD_STATE testCase2 = HotColdManager.HOT_COLD_STATE.VERY_COLD;
        assertEquals(testCase2, HotColdManager.determineHotCold(800));
    }

    @Test
    public void testMapLocation(){
        MapLocationHelper testMapLocation = new MapLocationHelper();
        LatLng testLatLng = new LatLng(47.232164, 11.441617);
        assertEquals(true, testMapLocation.isInRange(testLatLng, testLatLng, 0));
        assertEquals(0.0, testMapLocation.distanceBetween(testLatLng, testLatLng));
    }

}
