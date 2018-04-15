package khaled.ahmed.pharostask.Objects;

/**
 * Created by ah.khaled1994@gmail.com on 4/15/2018.
 */

public class Cordinate {

    private String lat;
    private String lon;

    public Cordinate(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return Double.valueOf(lat);
    }

    public double getLon() {
        return Double.valueOf(lon);
    }
}
