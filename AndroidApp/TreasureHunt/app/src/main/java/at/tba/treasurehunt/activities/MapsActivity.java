package at.tba.treasurehunt.activities;

import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import at.tba.treasurehunt.R;
import at.tba.treasurehunt.controller.LocationController;
import at.tba.treasurehunt.utils.GPSTracker;
import at.tba.treasurehunt.utils.GPSUpdateHandler;
import at.tba.treasurehunt.utils.ShowMessageHelper;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_maps);
        setUpMapIfNeeded();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
       mMap.setMyLocationEnabled(true);
       GPSUpdateHandler gpsUpdate = new GPSUpdateHandler(this);
       gpsUpdate.startHandler();
       initMapAndLocations();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    private void initMapAndLocations(){
        gpsTracker = new GPSTracker(this);
        LocationController.getInstance().setMapAndGps(mMap, gpsTracker);
        LocationController.getInstance().initialSetLocations();
    }

    public void onTreasureInRange(){
        Log.i("TRSR", "FOUND A TREASURE!!");
        Button b = (Button) findViewById(R.id.btnOpenTreasure);
        b.setVisibility(View.VISIBLE);
        b.setEnabled(true);
    }

    public void onButtonOpenTreasureClick(View v){
        ShowMessageHelper.showSimpleInfoMessagePopUp("You found a treasure bro", this);
    }

    public void updateHotColdDistance(double distance){
        TextView t = (TextView) findViewById(R.id.textViewDistToTreasure);
        t.setText("Distance to next Treasure: "+distance);
    }

}
