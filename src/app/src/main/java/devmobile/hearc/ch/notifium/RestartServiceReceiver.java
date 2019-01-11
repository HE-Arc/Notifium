package devmobile.hearc.ch.notifium;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RestartServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Log.i("fabien", "restarted");
            context.startForegroundService(new Intent(context, NotifierService.class));
        }
        catch (Exception e)
        {
            Log.i("fabien", e.getMessage());
        }
    }
}
