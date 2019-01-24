/**
 * Projet : Notifium
 * Students : Raphaël Margueron / Fabien Mottier / Segan Salomon
 * Teacher : Aicha Rizzotti
 * Module : 3255.1-Developpement_mobile
 * Repository Git : https://github.com/HE-Arc/Notifium
 * Date : 25.01.2019
 */
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
            context.startService(new Intent(context, NotifierService.class));
        }
        catch (Exception e)
        {
        }
    }
}
