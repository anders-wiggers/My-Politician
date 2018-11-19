/**
 * Copyright (c) 2018.
 * @author Anders Bille Wiggers
 * @auther Alex Krogh Smythe
 * for Introduction-to-Human-Computer-InteractionI course.
 *
 */

package aacorp.mypolitician.framework;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String email;
    private ArrayList<Politician> likedPoliticians;

    public User(String username, String email){
        this.username = username;
        this.email = email;
        likedPoliticians = new ArrayList<>();

    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public List<Politician> getLikedPoliticians(){
        return likedPoliticians;
    }

    public void addLikedPolitician(Politician politician){
        likedPoliticians.add(politician);
    }



}
