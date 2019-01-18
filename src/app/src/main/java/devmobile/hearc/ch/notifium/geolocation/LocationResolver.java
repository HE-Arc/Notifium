/**
 * Projet : Notifium
 * Students : Raphaël Margueron / Fabien Mottier / Segan Salomon
 * Teacher : Aicha Rizzotti
 * Module : 3255.1-Developpement_mobile
 * Repository Git : https://github.com/HE-Arc/Notifium
 * Date : 25.01.2019
 */
package devmobile.hearc.ch.notifium.geolocation;

import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import devmobile.hearc.ch.notifium.AlertAdapter;
import devmobile.hearc.ch.notifium.activities.AlertListActivity;

/**
 * FROM :
 * - https://stackoverflow.com/questions/1513485/how-do-i-get-the-current-gps-location-programmatically-in-android
 * - https://stackoverflow.com/questions/3145089/what-is-the-simplest-and-most-robust-way-to-get-the-users-current-location-on-a/3145655#3145655
 * - https://stackoverflow.com/questions/32491960/android-check-permission-for-locationmanager
 * - https://stackoverflow.com/questions/6899988/android-location-manager-permissions-to-be-used
 */
public class LocationResolver  {

    private static LocationResolver instance = null;
    public static LocationResolver getInstance() {
        if(instance == null)
        {
            instance = new LocationResolver();
        }
        return instance;
    }

    // Turn the constructor to private
    private LocationResolver()
    {
        this.prepare();
    }

    private Timer timer;
    private LocationManager locationManager;
    private LocationResult locationResult;
    private boolean gpsEnabled = false;
    private boolean networkEnabled = false;
    private Handler locationTimeoutHandler;

    private final Handler.Callback locationTimeoutCallback = new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            locationTimeoutFunc();
            return true;
        }

        private void locationTimeoutFunc() {
            try {
                locationManager.removeUpdates(locationListenerGps);
                locationManager.removeUpdates(locationListenerNetwork);

                Location networkLocation = null, gpsLocation = null;
                if (gpsEnabled)
                    gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (networkEnabled)
                    networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                // if there are both values use the latest one
                if (gpsLocation != null && networkLocation != null) {
                    if (gpsLocation.getTime() > networkLocation.getTime())
                        locationResult.gotLocation(gpsLocation);
                    else
                        locationResult.gotLocation(networkLocation);
                    return;
                }

                if (gpsLocation != null) {
                    locationResult.gotLocation(gpsLocation);
                    return;
                }
                if (networkLocation != null) {
                    locationResult.gotLocation(networkLocation);
                    return;
                }
                locationResult.gotLocation(null);
            }
            catch (SecurityException e) {
                String text = e.getMessage();
                int duration = Toast.LENGTH_SHORT;

                // Should not be done like this but ...
                // Toast toast = Toast.makeToast(, text, duration);
                // toast.show();
            }
        }
    };

    private final LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer.cancel();
            locationResult.gotLocation(location);
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerNetwork);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    private final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer.cancel();
            locationResult.gotLocation(location);
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerGps);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    public void prepare() {
        locationTimeoutHandler = new Handler(locationTimeoutCallback);
    }

    public synchronized boolean getLocation(Context context, LocationResult result, int maxMillisToWait) {

        try {
            locationResult = result;
            if (locationManager == null)
                locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            // exceptions will be thrown if provider is not permitted.
            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            // don't start listeners if no provider is enabled
            if (!gpsEnabled && !networkEnabled)
                return false;

            if (gpsEnabled)
                locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListenerGps, Looper.myLooper());
            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
            if (networkEnabled)
                locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListenerNetwork, Looper.myLooper());
            //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);

            timer = new Timer();
            timer.schedule(new GetLastLocationTask(), maxMillisToWait);
            return true;
        }
        catch (SecurityException e) {
            String text = e.getMessage();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return false;
        }
    }

    private class GetLastLocationTask extends TimerTask {
        @Override
        public void run() {
            locationTimeoutHandler.sendEmptyMessage(0);
        }
    }

    public static abstract class LocationResult {
        public abstract void gotLocation(Location location);
    }

    /*
    public void cancelTimer() {
        timer1.cancel();
        lm.removeUpdates(locationListenerGps);
        lm.removeUpdates(locationListenerNetwork);
    }
    */

}
