package devmobile.hearc.ch.notifium.logicals.conditions;

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

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public ConditionType getConditionType() {
        return ConditionType.Position;
    }
}

