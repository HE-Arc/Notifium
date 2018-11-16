package devmobile.hearc.ch.notifium.logicals.conditions;

import java.util.concurrent.locks.Condition;

import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

public class ConditionLocalisation implements Condition_I {

    private float lat;
    private float lng;
    private float radius;

    public ConditionLocalisation(float _lat, float _lng, float _radius)
    {
        lat = _lat;
        lng = _lng;
        radius = _radius;
    }

    /**
     * TODO
     * @return
     */
    public boolean evaluatePredicate()
    {
        return false;
    }

    @Override
    public ConditionType getConditionType() {
        return ConditionType.Position;
    }
}
