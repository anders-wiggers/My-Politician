/**
 *  PolitcianImpl is the implementation of the Politician Interface.
 *  The class is a pretty trivial implementation, the reason for this is
 *  that a trivial class is easy to move to the FireStore solution.
 *
 *  @author Anders Bille Wiggers
 *  for Introduction-to-Human-Computer-InteractionI course.
 *  Copyright (c) 2018.
 *
 */

package aacorp.mypolitician.Implementation;

import com.google.firebase.firestore.GeoPoint;

import java.util.Map;

import aacorp.mypolitician.framework.Politician;

public class PoliticianImpl implements Politician {

    private String name;
    private String party;
    private GeoPoint area;
    private int bannerId;
    private int profilePictureId;
    private Map<String,StrengthImpl> strength;

    public PoliticianImpl(){
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getParty() { return party; }

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
    public Map<String,StrengthImpl> getStrength() {
        return strength;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public void setArea(GeoPoint geoPoint) {
        this.area = geoPoint;
    }

    public void setBannerId(int bannerId) {
        this.bannerId = bannerId;
    }

    public void setProfilePictureId(int profilePictureId) {
        this.profilePictureId = profilePictureId;
    }

    public void setStrength(Map<String,StrengthImpl> strength) {
        this.strength = strength;
    }

    @Override
    public String toString() {
        return "PoliticianImpl{" +
                "Name='" + name + '\'' +
                ", Party='" + party + '\'' +
                ", Location=" + area +
                ", Banner id=" + bannerId +
                ", Profile picture id=" + profilePictureId +
                ", Strength=" + strength +
                '}';
    }

   /* public void like(){
        db.collection("politicians").add(politician);
    }
    */
}
