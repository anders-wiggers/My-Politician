package aacorp.mypolitician.framework;

import java.util.Objects;

public class Area {

    private final int lon;
    private final int lat;

    public Area(int lat, int lon){
        this.lat = lat; this.lon = lon;
    }

    public int getLat() {
        return lat;
    }

    public int getLon() {
        return lon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area area = (Area) o;
        return lon == area.lon && lat == area.lat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lon, lat);
    }

    @Override
    public String toString() {
        return "Geolocation{" + "latitude=" + lat + ", longitude=" + lon + '}';
    }
}
