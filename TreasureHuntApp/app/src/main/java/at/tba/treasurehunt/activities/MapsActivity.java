package at.tba.treasurehunt.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import at.tba.treasurehunt.R;
import at.tba.treasurehunt.controller.LocationController;
import at.tba.treasurehunt.dataprovider.IOpenTreasureCallback;
import at.tba.treasurehunt.dataprovider.ITreasureLoadedCallback;
import at.tba.treasurehunt.dataprovider.TreasureChestsProvider;
import at.tba.treasurehunt.servercomm.ServerCommunication;
import at.tba.treasurehunt.treasures.TreasureChestHolder;
import at.tba.treasurehunt.utils.AlertHelper;
import at.tba.treasurehunt.utils.RectangleDrawView;
import at.tba.treasurehunt.utils.GPSTracker;
import at.tba.treasurehunt.utils.GPSUpdateHandler;
import at.tba.treasurehunt.utils.HotColdManager;
import at.tba.treasurehunt.utils.TreasureUpdateHandler;
import data_structures.treasure.Treasure;


/**
 * MAP Activity keeps screen alive! Set in onCreate.
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ITreasureLoadedCallback, IOpenTreasureCallback {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GPSTracker gpsTracker;
    private RectangleDrawView drawRectView;

    /*
    Used for single loading of treasures.
    When the android devices orientation is changed, the onCreate method is called again.
     */
    private View mProgressView;
    private View mMapsFrameLayout;

    private Button openTreasureButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        drawRectView = (RectangleDrawView) findViewById(R.id.rectView);
        drawRectView.setBackgroundColor(Color.TRANSPARENT);
        //mProgressView = findViewById(R.id.load_treasure_progress);
       // mMapsFrameLayout = findViewById(R.id.mapsContent);
        openTreasureButton = (Button) findViewById(R.id.btnOpenTreasure);
        setUpMapIfNeeded();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ActivityManager.setCurrentActivity(this);
        //loadTreasures();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        refreshMap();
        ActivityManager.setCurrentActivity(this);
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
       TreasureUpdateHandler treasureUpdate = new TreasureUpdateHandler(this);
       gpsUpdate.startHandler();
       treasureUpdate.startHandler();
       initMapAndLocations();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }


    private void initMapAndLocations(){
        gpsTracker = new GPSTracker(this);
        LocationController.getInstance().setMapAndGps(mMap, gpsTracker);
        LocationController.getInstance().initialSetLocations();
        mMap.setOnMyLocationChangeListener(gpsTracker);
    }

    public void onTreasureInRange(){
        Button b = (Button) findViewById(R.id.btnOpenTreasure);
        b.setVisibility(View.VISIBLE);
        b.setBackgroundColor(Color.RED);
        b.setTextColor(Color.WHITE);

        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        Animation blink = AnimationUtils.loadAnimation(this, R.anim.blink);
        b.startAnimation(shake);
        b.startAnimation(blink);

        b.setEnabled(true);
    }

    public void onNoTreasureInRange(){
        Button b = (Button) findViewById(R.id.btnOpenTreasure);
        b.setVisibility(View.INVISIBLE);
        b.setEnabled(false);
        b.clearAnimation();
    }


    /**
     * Only helper function here. Should be placed somewhere else, as soon as data structure is fixed
     * @param type Treasure.Type object
     * @return  true, if Type is a Quiz
     */
    private boolean isQuiz(Treasure.Type type){
        return true;
    }

    public void updateHotColdDistance(double distance){

        HotColdManager.HOT_COLD_STATE distState = HotColdManager.determineHotCold((int) distance);

        int red = 0;
        int green = 0;
        int blue = 255;



        switch(distState){
            case VERY_COLD: red = 0; green = 0; blue = 255; break;
            case COLD: red = 55; green = 0; blue = 200; break;
            case WARM: red = 150; green = 0; blue = 100; break;
            case HOT: red = 200; green = 0; blue = 55; break;
            case VERY_HOT: red = 255; green = 0; blue = 0; break;
            default: red = 0; green = 0; blue = 0;break;
        }

        // Testing interpolation algorithm
        if (distance > 800) {
            red = 0;
            blue = 255;
            green = 0;
        }else{
            red = (int) (255 - ((distance / 80) * (255/10)));
            blue = (int) (0 + ((distance / 80) * 255/10));
            green = 0;
        }


        drawRectView.setRectangleColor(red, green, blue);
        drawRectView.invalidate();
    }

    public void refreshMap(){
        TreasureChestHolder.getInstance().updateTreasureList();
        TreasureChestHolder.getInstance().drawChestsOnMap(this.mMap);
        LatLng userPos = LocationController.getInstance().getMyPosition();
        TreasureChestHolder.getInstance().updateTreasuresInRange(userPos);
        updateHotColdDistance(TreasureChestHolder.getInstance().getNearestTreasureDistance());
        if (TreasureChestHolder.getInstance().isTreasureInRange()){
            onTreasureInRange();
        }else{
            onNoTreasureInRange();
        }

    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mMapsFrameLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            mMapsFrameLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mMapsFrameLayout.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mMapsFrameLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void loadTreasures(){
            //showProgress(true);
            TreasureChestsProvider.getInstance().loadTreasures(this);
    }



    public void onButtonOpenTreasureClick(View v){
        //ShowMessageHelper.showSimpleInfoMessagePopUp("You found a treasure bro", this);
        TreasureChestHolder.getInstance().openTreasure(TreasureChestHolder.getInstance().getNearestTreasure(),this);
        openTreasureButton.setClickable(false);
    }

    @Override
    public void onTreasuresLoadedSuccess() {
        //setUpMapIfNeeded();
        refreshMap();
       // showProgress(false);
    }

    @Override
    public void onTreasureLoadedFailure() {
        AlertHelper.showNewAlertSingleButton(this, "Something went wrong..",
                "An error occured loading the Treasures!. Please try again.",
                new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });
    }

    @Override
    public void onOpenTreasureSuccess() {
        openTreasureButton.setClickable(true);
        if (isQuiz(TreasureChestHolder.getInstance().getNearestTreasure().getType())) {
            TreasureChestHolder.getInstance().setCurrentSelectedTreasure(TreasureChestHolder.getInstance().getNearestTreasure());
            Intent actSwitch = new Intent(this, QuizActivity.class);
            startActivity(actSwitch);
        }
    }

    @Override
    public void onOpenTreasureFailure() {
        openTreasureButton.setClickable(true);
        TreasureChestsProvider.getInstance().removeTreasureFromList(TreasureChestHolder.getInstance().getNearestTreasure());
        AlertHelper.showNewAlertSingleButton(this, "Something went wrong.."
                , "You are not allowed to open this treasure at the moment! Maybe another Player is trying to open it. Sorry.",
                new Runnable() {
                    @Override
                    public void run() {
                        refreshMap();
                    }
                });
    }

    public GoogleMap getMap(){
        return mMap;
    }
}
