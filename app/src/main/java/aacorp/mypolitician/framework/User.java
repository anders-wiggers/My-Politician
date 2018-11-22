/**
 * Copyright (c) 2018.
 * @author Anders Bille Wiggers
 * @auther Alex Krogh Smythe
 * for Introduction-to-Human-Computer-InteractionI course.
 *
 */

package aacorp.mypolitician.framework;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class User {
    private String username;
    private String email;
    private ArrayList<String> likedPoliticians = null;
    private ArrayList<String> seenPoliticians = null;

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

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
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
}
