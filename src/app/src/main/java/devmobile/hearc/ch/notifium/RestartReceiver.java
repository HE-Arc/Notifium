package devmobile.hearc.ch.notifium;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RestartReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("cool", "restart");
        try {
            context.startForegroundService(new Intent(context, NotifierService.class));
        }
        catch (Exception e)
        {
            Log.i("cool", e.getMessage());
        }

        Log.i("cool", "started");
    }
}
