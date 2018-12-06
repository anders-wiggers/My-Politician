/**
 * Copyright (c) 2018.
 * @author Anders Bille Wiggers
 * @auther Alex Krogh Smythe
 * for Introduction-to-Human-Computer-InteractionI course.
 *
 */

package aacorp.mypolitician.framework;

import android.location.Location;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class User {
    private String username;
    private String email;
    private ArrayList<String> likedPoliticians = null;
    private ArrayList<String> seenPoliticians = null;
    private Boolean localPoliticianSetting = null;
    private Location location;

    public User(){ }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLikedPoliticians(ArrayList<String> likedPoliticians) {
        this.likedPoliticians = likedPoliticians;
    }

    public void setSeenPoliticians(ArrayList<String> seenPoliticians) {
        this.seenPoliticians = seenPoliticians;
    }

    public Boolean getLocalPoliticianSetting() {
        return localPoliticianSetting;
    }

    public void setLocalPoliticianSetting(Boolean localPoliticianSetting) {
        this.localPoliticianSetting = localPoliticianSetting;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<String> getLikedPoliticians(){
        return likedPoliticians;
    }

    public List<String> getSeenPoliticians() {
        return seenPoliticians;
    }

    public void addLikedPolitician(String id){
        likedPoliticians.add(id);
    }

    public void addSeenPolitician(String id){
        seenPoliticians.add(id);
    }


    public void removeLikedPolitician(String id) {
        addSeenPolitician(id);

        Iterator<String> i = likedPoliticians.iterator();
        while(i.hasNext()){
            if(id.equals(i.next())){
                i.remove();
            }
        }
    }

    public void removeSeenPolitician(String id) {
        Iterator<String> i = seenPoliticians.iterator();
        while(i.hasNext()){
            if(id.equals(i.next())){
                i.remove();
            }
        }
    }
}
