/*
 * Copyright (c) 2018.
 * @author Anders Bille Wiggers
 * for Introduction-to-Human-Computer-InteractionI course.
 *
 */

package aacorp.mypolitician.Implementation;

import com.google.firebase.firestore.GeoPoint;

import java.util.Map;

import aacorp.mypolitician.framework.Party;
import aacorp.mypolitician.framework.Politician;

public class PoliticianImpl implements Politician {

    private int uniqueKey;
    private String name;
    private Party party;
    private GeoPoint geoPoint;
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
    public int getKey(){return uniqueKey;}

    @Override
    public Party getParty() { return party; }

    @Override
    public GeoPoint getGeoPoint() {
        return geoPoint;
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

    public void setParty(Party party) {
        this.party = party;
    }

    public void setArea(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
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
                ", Location=" + geoPoint +
                ", Banner id=" + bannerId +
                ", Profile picture id=" + profilePictureId +
                ", Strength=" + strength +
                '}';
    }

   /* public void like(){
        db.collection("politicians").add(politician);
    }

    public void dislike(){

    }*/


}
