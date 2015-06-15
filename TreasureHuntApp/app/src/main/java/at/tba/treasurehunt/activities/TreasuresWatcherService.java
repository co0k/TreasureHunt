package at.tba.treasurehunt.activities;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import at.tba.treasurehunt.R;
import at.tba.treasurehunt.controller.LocationController;
import at.tba.treasurehunt.dataprovider.ITreasureLoadedCallback;
import at.tba.treasurehunt.dataprovider.TreasureChestsProvider;
import at.tba.treasurehunt.treasures.TreasureChestHolder;
import at.tba.treasurehunt.utils.GPSTracker;
import at.tba.treasurehunt.utils.TreasureUpdateHandler;
import data_structures.treasure.Treasure;

public class TreasuresWatcherService extends IntentService implements ITreasureLoadedCallback {

	public final static String METHOD = "method";
	private final static float minAccuracy = 30.0f;
	private final static int refreshInterval = 50;
	private final static float treasureNearThreshold = 300;
	private Location location;
	private boolean isNotifiedClose = false;
	private boolean isNotifiedFound = false;
	private MyLocationListener locationListener;

	public TreasuresWatcherService() {
		super("TreasureHuntWatcher");
		this.isNotifiedClose = false;
		this.isNotifiedFound = false;
		this.locationListener = new MyLocationListener();
	}

	/**
	 * @param name Used to name the worker thread, important only for debugging.
	 */
	public TreasuresWatcherService(String name) {
		super(name);
	}

	@Override
	public void onTreasuresLoadedSuccess(List<Treasure> treasures) {
		TreasureChestHolder.getInstance().updateTreasureList();
		LatLng userPos = LocationController.getInstance().getMyPosition();
		TreasureChestHolder.getInstance().updateTreasuresInRange(userPos);

		NotificationManager notificationManager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
		Intent tmp = new Intent(getBaseContext(),MapsActivity.class);
		tmp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		PendingIntent mainScr = PendingIntent.getActivity(getBaseContext(), 0, tmp, 0);
		Log.d("bitch", "whoreson");
		if (TreasureChestHolder.getInstance().isTreasureInRange()){
			if(!isNotifiedFound) {
				notificationManager.cancel(2);
				isNotifiedClose = false;
				isNotifiedFound = true;
				final Notification notification = new NotificationCompat.Builder(getBaseContext())
						.setSmallIcon(R.mipmap.ic_launcher)
						.setContentTitle("found treasure chest!")
						.setSound(alarmSound)
						.setOnlyAlertOnce(true)
						.setContentIntent(mainScr)
						.build();
				notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;
				notification.defaults = Notification.DEFAULT_SOUND;
				notificationManager.notify(1, notification);
			}
		} else if (TreasureChestHolder.getInstance().getNearestTreasureDistance() <= treasureNearThreshold) {
			if(!isNotifiedClose) {
				notificationManager.cancel(1);
				isNotifiedClose = true;
				isNotifiedFound = false;
				final Notification notification = new NotificationCompat.Builder(getBaseContext())
						.setSmallIcon(R.mipmap.ic_launcher)
						.setContentTitle("treasure chest is close!")
						.setSound(alarmSound)
						.setOnlyAlertOnce(true)
						.setContentIntent(mainScr)
						.build();
				notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;
				notification.defaults = Notification.DEFAULT_SOUND;
				notificationManager.notify(2, notification);
			}
		} else {
			notificationManager.cancel(1);
			notificationManager.cancel(2);
			isNotifiedClose = false;
			isNotifiedFound = false;
		}
	}

	@Override
	public void onTreasureLoadedFailure() {

	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String method = intent.getStringExtra(METHOD);
		GPSTracker gpsTracker = GPSTracker.getInstance();
		NotificationManager notificationManager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
		switch(method) {
			case "start":
				Intent tmp = new Intent(getBaseContext(),MapsActivity.class);
				tmp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				PendingIntent mainScr = PendingIntent.getActivity(getBaseContext(), 0, tmp, 0);
				TreasureUpdateHandler.getInstance().startHandler();
				TreasureChestsProvider.getInstance().registerListener(this);
				final Notification notification = new NotificationCompat.Builder(getBaseContext())
						.setSmallIcon(R.mipmap.ic_launcher)
						.setContentTitle("The treasure hunt is running!")
						.setContentIntent(mainScr)
						.setOngoing(true)
						.build();
				notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_NO_CLEAR;
				notificationManager.notify(0, notification);
				gpsTracker.registerLocationListener(locationListener);

				break;
			case "stop":
				notificationManager.cancel(0);
				gpsTracker.removeLocationListener(locationListener);
				TreasureUpdateHandler.getInstance().stopHandler();
				TreasureChestsProvider.getInstance().removeListener(this);

		}
	}

	@Override
	public void onTaskRemoved(Intent intent) {
		GPSTracker gpsTracker = GPSTracker.getInstance();
		NotificationManager notificationManager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(0);
		gpsTracker.removeLocationListener(locationListener);
		stopSelf();
	}

	private class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			if(location.hasAccuracy() && location.getAccuracy() <= minAccuracy) {
				TreasuresWatcherService.this.location = location;
			}
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		@Override
		public void onProviderEnabled(String provider) {

		}

		@Override
		public void onProviderDisabled(String provider) {

		}
	}
}
