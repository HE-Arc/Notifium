/**
 * Projet : Notifium
 * Students : Raphaël Margueron / Fabien Mottier / Segan Salomon
 * Teacher : Aicha Rizzotti
 * Module : 3255.1-Developpement_mobile
 * Repository Git : https://github.com/HE-Arc/Notifium
 * Date : 25.01.2019
 */
package devmobile.hearc.ch.notifium.logicals.conditions;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import devmobile.hearc.ch.notifium.activities.AlertListActivity;
import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

/**
 * Implements Condition_I
 * Used to raise an alert at a given battery level
 */
public class ConditionBatteryLevel implements Condition_I {

    private final int threshold;

    public ConditionBatteryLevel(int _threshold) {
        threshold = _threshold;
    }

    @Override
    public boolean evaluatePredicate() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = AlertListActivity.context.registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float) scale;

        return false;
    }

    public int getThreshold() {
        return threshold;
    }

    @Override
    public ConditionType getConditionType() {
        return ConditionType.Battery;
    }

    @Override
    public int hashCode() {
        return (super.hashCode() << 16) ^ (threshold & 65535);
    }
}
