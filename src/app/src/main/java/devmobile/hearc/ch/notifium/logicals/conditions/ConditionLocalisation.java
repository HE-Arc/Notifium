package devmobile.hearc.ch.notifium.logicals.conditions;

import devmobile.hearc.ch.notifium.geolocation.Coordinates;
import devmobile.hearc.ch.notifium.geolocation.LocationGetter;
import devmobile.hearc.ch.notifium.logicals.enums.ConditionType;

import static devmobile.hearc.ch.notifium.geolocation.LocationGetter.getInstance;

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
     * Compare current user coordinates with the stored one
     * @return
     */
    public boolean evaluatePredicate() {
        Coordinates currentCoords = LocationGetter.getInstance().getLocation(100, 10);
        if (currentCoords != null && currentCoords != Coordinates.UNDEFINED) {
            return (
                (currentCoords.getX() > (lng - radius)) &&
                (currentCoords.getX() < (lng + radius)) &&
                (currentCoords.getY() > (lat - radius)) &&
                (currentCoords.getY() < (lat + radius))
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

    @Override
    public int hashCode() {
        return (((Float)lng).hashCode() << 16) ^ (((Float)lat).hashCode() & 65535);
    }
}
