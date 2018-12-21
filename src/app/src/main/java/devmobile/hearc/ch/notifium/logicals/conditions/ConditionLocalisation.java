package devmobile.hearc.ch.notifium.logicals.conditions;

import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

import static devmobile.hearc.ch.notifium.geolocation.LocationGetter.getInstance;

public class ConditionLocalisation implements Condition_I {

    private Coordinates coords;
    private float radius;

    public ConditionLocalisation(float _lat, float _lng, float _radius)
    {
        coords = new Coordinates(_lat, _lng);
        radius = _radius;
    }

    /**
     * Compare current user coordinates with the stored one
     * @return
     */
    public boolean evaluatePredicate() {
        Coordinates currentCoords = LocationGetter.getInstance().getLocation(100, 10);
        if (currentCoords != null && currentCoords != coords.UNDEFINED) {
            return (
                (currentCoords.getX() > (coords.getX() - radius)) &&
                (currentCoords.getX() < (coords.getX() + radius)) &&
                (currentCoords.getY() > (coords.getY() - radius)) &&
                (currentCoords.getY() < (coords.getY() + radius))
            );
        }
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
