package aacorp.mypolitician.Implementation;

import com.google.firebase.firestore.GeoPoint;

import java.util.Map;

import aacorp.mypolitician.framework.Match;
import aacorp.mypolitician.framework.Strength;

public class MatchImpl implements Match {

    private String name;
    private String party;
    private GeoPoint area;
    private int bannerId;
    private int profilePictureId;
    private Map<String,Strength> strength;

    public MatchImpl(){
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getParty() {
        return party;
    }

    @Override
    public GeoPoint getArea() {
        return area;
    }

    @Override
    public int getBannerId() {
        return bannerId;
    }

    @Override
    public int getProfilePictureId() {
        return profilePictureId;
    }

    @Override
    public Map<String,Strength> getStrength() {
        return strength;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public void setArea(GeoPoint area) {
        this.area = area;
    }

    public void setBannerId(int bannerId) {
        this.bannerId = bannerId;
    }

    public void setProfilePictureId(int profilePictureId) {
        this.profilePictureId = profilePictureId;
    }

    public void setStrength(Map<String,Strength> strength) {
        this.strength = strength;
    }
}
