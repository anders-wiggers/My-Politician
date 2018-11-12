package aacorp.mypolitician.framework;

import com.google.firebase.firestore.GeoPoint;

import java.util.Map;

public interface Match {

    String getName();

    String getParty();

    GeoPoint getArea();

    int getBannerId();

    int getProfilePictureId();

    Map<String,Strength> getStrength();

}
