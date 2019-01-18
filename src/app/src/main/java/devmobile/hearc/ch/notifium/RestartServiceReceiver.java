package devmobile.hearc.ch.notifium;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Broadcast reciever to restart the NotifierService
 */
public class RestartServiceReceiver extends BroadcastReceiver {
    // Broadcast use to restart the service and maintains it active
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            context.startForegroundService(new Intent(context, NotifierService.class));
        }
        catch (Exception e)
        {
        }
    }
}
