package khaled.ahmed.pharostask.Objects;

/**
 * Created by ah.khaled1994@gmail.com on 4/15/2018.
 */

public class Cities {

    private String country;
    private String name;
    private Cordinate coord;
    private String imageUrl;

    public Cities(String country, String name, Cordinate coord) {
        this.country = country;
        this.name = name;
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public Cordinate getCoord() {
        return coord;
    }

    public String getImageUrl() {
        imageUrl = "http://maps.google.com/maps/api/staticmap?zoom=15&size=300x300&sensor=false&" +
                "markers=color:bluezlabel:S|" + coord.getLat() + "," + coord.getLon();
        return imageUrl;
    }
}
