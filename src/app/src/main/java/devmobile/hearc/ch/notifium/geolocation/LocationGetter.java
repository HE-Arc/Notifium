package devmobile.hearc.ch.notifium.geolocation;

import android.content.Context;
import android.location.Location;
import android.os.Looper;

import devmobile.hearc.ch.notifium.AlertAdapter;


/**
 * FROM :
 * - https://stackoverflow.com/questions/1513485/how-do-i-get-the-current-gps-location-programmatically-in-android
 * - https://stackoverflow.com/questions/3145089/what-is-the-simplest-and-most-robust-way-to-get-the-users-current-location-on-a/3145655#3145655
 * - https://stackoverflow.com/questions/32491960/android-check-permission-for-locationmanager
 * - https://stackoverflow.com/questions/6899988/android-location-manager-permissions-to-be-used
 */
public class LocationGetter {

    private static LocationGetter instance = null;
    public static LocationGetter getInstance() {
        if(instance == null)
        {
            instance = new LocationGetter();
        }
        return instance;
    }

    // Turn the constructor to private
    private LocationGetter()
    {

    }

    private Location location = null;
    private final Object gotLocationLock = new Object();
    private final LocationResolver.LocationResult locationResult = new LocationResolver.LocationResult() {
        @Override
        public void gotLocation(Location location) {
            synchronized (gotLocationLock) {
                LocationGetter.this.location = location;
                gotLocationLock.notifyAll();
                Looper.myLooper().quit();
            }
        }
    };

    public synchronized Coordinates getLocation(int maxWaitingTime, int updateTimeout) {
        try {
            final int updateTimeoutPar = updateTimeout;
            synchronized (gotLocationLock) {
                new Thread() {
                    public void run() {
                        Looper.prepare();
                        // Context should not be retrieved like this but ...
                        LocationResolver.getInstance().getLocation(AlertAdapter.CONTEXT, locationResult, updateTimeoutPar);
                        Looper.loop();
                    }
                }.start();

                gotLocationLock.wait(maxWaitingTime);
            }
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        Coordinates coordinates;
        if (location != null)
            coordinates = new Coordinates(location.getLatitude(), location.getLongitude());
        else
            coordinates = Coordinates.UNDEFINED;
        return coordinates;
    }
}
