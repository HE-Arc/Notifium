package devmobile.hearc.ch.notifium.geolocation;

/**
 * Class used to depict position on a map
 */
public class Coordinates {
    private double x, y;

    public Coordinates(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getX() {return this.x;}
    public double getY() {return this.y;}

    public static Coordinates UNDEFINED = new Coordinates(0,0);
}
