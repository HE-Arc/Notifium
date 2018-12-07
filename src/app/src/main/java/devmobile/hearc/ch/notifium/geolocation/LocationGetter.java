package devmobile.hearc.ch.notifium.geolocation;

import android.content.Context;
import android.location.Location;
import android.os.Looper;

public class LocationGetter {
    private final Context context;
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

    public LocationGetter(Context context) {
        if (context == null)
            throw new IllegalArgumentException("context == null");

        this.context = context;
    }

    public synchronized Coordinates getLocation(int maxWaitingTime, int updateTimeout) {
        try {
            final int updateTimeoutPar = updateTimeout;
            synchronized (gotLocationLock) {
                new Thread() {
                    public void run() {
                        Looper.prepare();
                        LocationResolver locationResolver = new LocationResolver();
                        locationResolver.prepare();
                        locationResolver.getLocation(context, locationResult, updateTimeoutPar);
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
