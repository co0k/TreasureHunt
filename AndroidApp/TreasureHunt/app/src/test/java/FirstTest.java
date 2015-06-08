import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;
import at.tba.treasurehunt.datastructures.user.User;
import at.tba.treasurehunt.datastructures.user.Inventory;
import at.tba.treasurehunt.utils.DummyDataProvider;
//import org.robolectric.RobolectricTestRunner;

import android.widget.TextView;


//@RunWith(RobolectricTestRunner.class)
public class FirstTest extends TestCase { 

    public FirstTest(){ super(); }   

    @Test 
    public void testUser(){
        User testUser = new User(1,"Testuser","test111","test@email",1,200, new Inventory(null));  
        assertEquals(testUser.getId(), 1); 
        }  

    @Test 
    public void testQuiz() { 
        Quiz testQuiz = new Quiz(1, "Name the first album of Nirvana", "Bleach", "In Utero", "Nevermind", "In Bloom", "From The Muddy Banks of Wishkah", "Heartshaped Box"); 
        Treasure testTreasure = new Treasure(new Treasure.Location(5, 100, 47.232164, 11.441617), testQuiz, new Treasure.Size(1, 20, 1), null);  
        assertEquals(testQuiz.getXP(), 1); 
        assertEquals(testTreasure.getLocation(), new Treasure.Location(5, 100, 47.232164, 11.441617));  
        }  

    @Test 
    public void testMapLocation(){ 
        MapLocationHelper testMapLocation = new MapLocationHelper(); 
        LatLng testLatLng = new LatLng(47.232164, 11.441617);  
        assertEquals(0, testMapLocation.isInRange(testLatLng, testLatLng, 0)); 
        assertEquals(true, testMapLocation.distanceBetween(testLatLng, testLatLng)); 
        }
          }