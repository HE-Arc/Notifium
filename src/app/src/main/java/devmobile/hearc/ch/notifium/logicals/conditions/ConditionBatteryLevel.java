package devmobile.hearc.ch.notifium.logicals.conditions;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import devmobile.hearc.ch.notifium.AlertAdapter;
import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

public class ConditionBatteryLevel implements Condition_I {

    private final int threshold;

    public ConditionBatteryLevel(int _threshold)
    {
        threshold = _threshold;
    }

    @Override
    public boolean evaluatePredicate() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus =  AlertAdapter.CONTEXT.registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float)scale;

        return false;
    }

    public int getThreshold()
    {
       return threshold;
    }
    @Override
    public ConditionType getConditionType() {
        return ConditionType.Battery;
    }
}

