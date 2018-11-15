/**
 * @Author Alex Krogh Smythe
 * Simple User class used to create user objects. Mostly used for storing politicians.
 */

package aacorp.mypolitician.framework;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String username;
    public String email;
    public ArrayList<Politician> likedPoliticians;

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
