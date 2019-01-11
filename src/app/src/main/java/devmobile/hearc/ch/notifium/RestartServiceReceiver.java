package devmobile.hearc.ch.notifium;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RestartServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            context.startForegroundService(new Intent(context, NotifierService.class));

            Intent updateIntent = new Intent(context, UpdateServiceReceiver.class);
            updateIntent.setAction("devmobile.hearc.ch.notifium");
            context.sendBroadcast(updateIntent);
        }
        catch (Exception e)
        {
            Log.i("fabien", e.getMessage());
        }
    }
}
