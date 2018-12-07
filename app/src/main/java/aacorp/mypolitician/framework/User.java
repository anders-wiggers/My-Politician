/**
 * Handles the user with all seen and liked politicians
 *
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

    /**
     * sets the userane
     * @param username the uiid from firebase
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * set the email to the facebook email
     * @param email string the mail
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * set the liked politicians a user has
     * @param likedPoliticians a list of liked politician ids
     */
    public void setLikedPoliticians(ArrayList<String> likedPoliticians) {
        this.likedPoliticians = likedPoliticians;
    }

    /**
     * sets the seen politicians for a user
     * @param seenPoliticians a list of seen politicians ids
     */
    public void setSeenPoliticians(ArrayList<String> seenPoliticians) {
        this.seenPoliticians = seenPoliticians;
    }

    /**
     * gets the local politicians setting
     * @return boolean depening on the setting
     */
    public Boolean getLocalPoliticianSetting() {
        return localPoliticianSetting;
    }

    /**
     * set the local politician setting
     * @param localPoliticianSetting boolean if the setting is on or not
     */
    public void setLocalPoliticianSetting(Boolean localPoliticianSetting) {
        this.localPoliticianSetting = localPoliticianSetting;
    }

    /**
     * returns the username
     * @return string username
     */
    public String getUsername() {
        return username;
    }

    /**
     * returns the email
     * @return string email
     */
    public String getEmail() {
        return email;
    }

    /**
     * reutrns the current users location
     * @return the location of the user
     */
    public Location getLocation() {
        return location;
    }

    /**
     * set the location of the user
     * @param location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * get a list of liked politicians
     * @return List of Strings that is the ids of liked politicians
     */
    public List<String> getLikedPoliticians(){
        return likedPoliticians;
    }

    /**
     * get a list of seen politcians
     * @return List of Strings that is the ids of seen politicians
     */
    public List<String> getSeenPoliticians() {
        return seenPoliticians;
    }

    /**
     * add a politicans to liked
     * @param id the id of the liked politician
     */
    public void addLikedPolitician(String id){
        likedPoliticians.add(id);
    }

    /**
     * add a seen politician to the seen list
     * @param id the id of the seen politician
     */
    public void addSeenPolitician(String id){
        seenPoliticians.add(id);
    }

    /**
     * removes a liked politcian
     * @param id the id of the politician
     */
    public void removeLikedPolitician(String id) {
        addSeenPolitician(id);

        Iterator<String> i = likedPoliticians.iterator();
        while(i.hasNext()){
            if(id.equals(i.next())){
                i.remove();
            }
        }
    }

    /**
     * remove a seen politician
     * @param id the id of the politician
     */
    public void removeSeenPolitician(String id) {
        Iterator<String> i = seenPoliticians.iterator();
        while(i.hasNext()){
            if(id.equals(i.next())){
                i.remove();
            }
        }
    }
}
