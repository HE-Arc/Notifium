package devmobile.hearc.ch.notifium;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import devmobile.hearc.ch.notifium.NotifierService.NotifierBinder;

public class UpdateServiceReceiver extends BroadcastReceiver {
    NotifierService notifierService;
    boolean mBound;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            NotifierBinder binder = (NotifierBinder) service;
            notifierService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newIntent = new Intent(context, NotifierService.class);
        context.bindService(newIntent, mConnection, Context.BIND_AUTO_CREATE);
        notifierService.loadAlerts();
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
