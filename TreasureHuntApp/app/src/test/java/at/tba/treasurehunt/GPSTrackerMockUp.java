package at.tba.treasurehunt;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import at.tba.treasurehunt.utils.GPSTracker;

/**
 * Created by poorpot on 11.06.15.
 */
public class GPSTrackerMockUp extends TestCase {


    @Mock
    GPSTracker testGPSTracker;

    @Test
    public void testGPSdata() {
        testGPSTracker = Mockito.mock(GPSTracker.class);
        Mockito.when(testGPSTracker.canGetLocation()).thenReturn(true);
        Location n = Mockito.mock(Location.class);
        n.setLatitude(47.265);
        n.setLongitude(11.395);

        Mockito.when(testGPSTracker.getLatitude()).thenReturn(47.265);
        Mockito.when(testGPSTracker.getLongitude()).thenReturn(11.395);
        Mockito.when(testGPSTracker.getLocation()).thenReturn(n);

        assertEquals(testGPSTracker.canGetLocation(), true);
        assertEquals(testGPSTracker.getLatitude(), 47.265);
        assertEquals(testGPSTracker.getLongitude(), 11.395);
    }

}