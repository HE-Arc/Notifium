package devmobile.hearc.ch.notifium;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import devmobile.hearc.ch.notifium.logicals.Alert;

/**
 * Service is always working in background
 * even without the app
 *
 * In API 28, it's not possible to block the closing of the service
 * when the app is closed. So a broadcast reciever is used to start
 * service at the closing and on the boot
 */
public class NotifierService extends Service {
    private Timer timer; // use to execute code periodically
    private TimerTask timerTask; // code executed periodically
    private ArrayList<Alert> alerts;
    private ArrayList<String> triggeredAlerts; // use to trigger one time

    public NotifierService() {}

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        triggeredAlerts = new ArrayList<String>();
        startTimer();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        // Instantiate a broadcast reciever
        // which restart the service
        // after the closure
        Intent restartIntent = new Intent(this, RestartServiceReceiver.class);
        //filter use by the broadcast reciever and defined in AndroidManifest.xml
        restartIntent.setAction("devmobile.hearc.ch.notifium");
        sendBroadcast(restartIntent);
    }

    @Override
    public void onTaskRemoved(Intent intent) {
        super.onTaskRemoved(intent);

        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        Intent restartIntent = new Intent(this, RestartServiceReceiver.class);
        restartIntent.setAction("devmobile.hearc.ch.notifium");

        sendBroadcast(restartIntent);
    }

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 1000); //
    }

    public void initializeTimerTask() {
        // define the task which be
        // executed every second
        timerTask = new TimerTask() {
            public void run() {
                launchNotification();
            }
        };
    }

    private void launchNotification()
    {
        // launch notifications of the alerts


        // currently the data must be reloaded
        // each time, because it impossible for
        // the app to reach the service
        // after a reboot of the phone
        loadAlerts();

        // Check all alert
        for (Alert alert: alerts) {
            boolean result = alert.evaluate();

            // Check if the alert is still in the same trigger
            if (triggeredAlerts.contains("")) {
                if (!result)
                {
                    triggeredAlerts.remove("");
                }
            }
            else {
                // Launch the notification if the alert is triggered
                if (result)
                {
                    startRingtone(getApplicationContext());
                    showNotification(getApplicationContext(), alert.getName(), alert.getNotification());
                    triggeredAlerts.add("");
                }
            }
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notifier Channel";
            String description = "Notifier Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Notifier Channel", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification(Context context, String title, String text) {
        // Push a notifiaction in the Notifier Channel

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification =
                new Notification.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setChannelId("Notifier Channel").build();

        // Launch the notification
        notificationManager.notify(1, notification);
    }

    private void startRingtone(Context context) {
        // Launch a ringtone if one is set in the phone

        try {
            Uri alarmUri = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alarmUri == null) {
                alarmUri = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
            Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
            ringtone.play();

        } catch (Exception ex) {
        }
    }

    public void loadAlerts()
    {
        this.alerts = AlertStorage.load(this.getApplicationContext());
    }
}
