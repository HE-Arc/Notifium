package devmobile.hearc.ch.notifium;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class NotifierService extends Service {
    private Timer timer;
    private TimerTask timerTask;

    public NotifierService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i("cool", "create");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("cool", "start");
        createNotificationChannel();
        startTimer();

        return START_STICKY;
    }

    private void lauchNotification()
    {
        NotificationReceiver nr = new NotificationReceiver();
        Intent intent1 = new Intent(this, NotificationReceiver.class);
        nr.onReceive(getApplicationContext(), intent1);
    }

    @Override
    public void onDestroy() {
        Log.i("cool", "stop");
        super.onDestroy();

        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        Intent restartIntent = new Intent(this, RestartReceiver.class);
        restartIntent.setAction("devmobile.hearc.ch.notifium");

        sendBroadcast(restartIntent);
    }

    @Override
    public void onTaskRemoved(Intent intent) {
        Log.i("cool", "remove");
        super.onTaskRemoved(intent);

        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        Intent restartIntent = new Intent(this, RestartReceiver.class);
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
        timerTask = new TimerTask() {
            public void run() {
                lauchNotification();
                Log.i("cool", "timer");
            }
        };
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
}
