import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;

//import org.robolectric.RobolectricTestRunner;

import android.widget.TextView;


//@RunWith(RobolectricTestRunner.class)
public class FirstTest {
    @Before
    public void testUser(){
    }


    @Test
    public void testInstantiation() {

        User
        java.at.tba.treasurehunt.datastructures.user.User TestUser;
        TestUser = new User(1,"Testuser","test111","test@email",1,200, new Inventory(null));
        TestUser.
        //Activity activity = new Activity();

        //TextView tv = new TextView(activity);
        //tv.setText("test");

        assertEquals(TestUser.getID(), 1);
    }
}