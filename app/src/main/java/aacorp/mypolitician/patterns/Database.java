/**
 *  Database is a Singleton class which handles all communication with
 *  Firestore.
 *
 *  @author Anders Bille Wiggers
 *  for Introduction-to-Human-Computer-InteractionI course.
 *  Copyright (c) 2018.
 *
 */

package aacorp.mypolitician.patterns;


import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import aacorp.mypolitician.Implementation.PoliticianImpl;
import aacorp.mypolitician.Implementation.StrengthImpl;
import aacorp.mypolitician.framework.Party;
import aacorp.mypolitician.framework.Politician;
import aacorp.mypolitician.framework.User;

public class Database {
    private static Database instance;  //Singleton instance
    private FirebaseFirestore db;   //Firestore instance
    private List<PoliticianImpl> politicians = new ArrayList<>(); //Politicians fetched from the database
    private User user = null;
    private boolean AppReady;
    private List<Party> parties = new ArrayList<>();//Parties fetches from the database

    protected Database(){
        initialize();
    }

    /**
     * Singleton method to return the instance of the Database class.
     * @return the only object of the database instance.
     */
    public static synchronized Database getInstance(){
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }

    public FirebaseFirestore getDatabase(){
        return db;
    }

    /**
     * Initialises the Firestore database and fetches politicians to the ArrayList politicians.
     */
    private void initialize(){
        db = FirebaseFirestore.getInstance();

        db.collection("politicians").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                politicians = queryDocumentSnapshots.toObjects(PoliticianImpl.class);
            }
        });

        db.collection("parties").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                parties = queryDocumentSnapshots.toObjects(Party.class);
            }
        });
    }

    /**
     * Method fetches a dummy politicians that is always the first in the arrayList
     * @return dummy Politician
     */
    public Politician fetchDummy(){
        return politicians.get(0);
    }

    /**
     * Method fetches a random politician from the arrayList politicians by generating
     * a random number that is between 0 and the number of politicians fetches to the list.
     * @return random Politician
     */
    public Politician fetchRandomPolitician(){
        int rngPolitician = (int) Math.floor(Math.random() * Math.floor(politicians.size()));
        return politicians.get(rngPolitician);
    }

    public void createParty(){
        Party party = new Party();
        party.setName("Alternativet");
        party.setColor(Color.GREEN);
        db.collection("parties").add(party);
    }

    /**
     * Method that create a politician and transfers the newly created politicians
     * to the Firestore database.
     */
    public void createPolitician(){
        PoliticianImpl politician = new PoliticianImpl();
        politician.setName("Lars Thomas");
        politician.setArea(new GeoPoint(51,10));
        politician.setBannerId(41601);
        politician.setParty("Alternativet");
        politician.setProfilePictureId(52351);

        StrengthImpl s = new StrengthImpl();
        StrengthImpl s1 = new StrengthImpl();
        Map<String,StrengthImpl> sl = new HashMap<>();

        s.setPercent(10);
        s.setText("People should chill");
        sl.put("def",s);

        s1.setText("I think EU is great man!");
        s1.setPercent(90);
        sl.put("wor",s1);

        politician.setStrength(sl);

        System.out.println(politician.toString());
        db.collection("politicians").add(politician);
    }


    /**
     * Method for generating user in the database, If the user exist the user will be
     * pulled and inserted into the user object, else the user will be created and
     * pushed to the firestore.
     * @param firebaseUser Need a firebase User to pull authentication from.
     */
    public void setUser(final FirebaseUser firebaseUser){
        db.collection("users").document(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if(documentSnapshot.get("username")==null){
                    User user1 = new User();
                    user1.setUsername(firebaseUser.getUid());
                    user1.setEmail(firebaseUser.getEmail());
                    user1.setLikedPoliticians(new ArrayList<String>());
                    user1.setSeenPoliticians(new ArrayList<String>());
                    db.collection("users").document(user1.getUsername()).set(user1);
                    user = user1;
                    Log.e("Should","Have been here");
                }
                else{
                    user = documentSnapshot.toObject(User.class);
                }
                AppReady = true;
            }
        });
    }

    public boolean isAppReady() {
        return AppReady;
    }

    public User getUser() {
        return user;
    }

    public List<Party> getParties() {
        return parties;
    }

    /**
     * add a politician a user has seen to the users list, so that
     * the user wont get the same politicians twice
     * @param id the ID of the politician to be added
     */
    public void addSeenToUser(String id){
        user.addSeenPolitician(id);
        db.collection("users").document(user.getUsername()).set(user);
    }

    public void addLikeToUser(String id) {
        user.addLikedPolitician(id);
        db.collection("users").document(user.getUsername()).set(user);

    }

    /**
     * Readys the index of politicians for feching by removing politicians that has been
     * like or dislike from the rest.
     */
    public void readyIndex(){
        List<String> ids = new ArrayList<>();
        ids.addAll(user.getSeenPoliticians());
        Iterator<PoliticianImpl> i = politicians.iterator();
        while(i.hasNext()) {
            Politician p = i.next();
            for (String id : ids) {
                if (id.equals(p.getId())) {
                    i.remove();
                }
            }
        }
    }

    /**
     * check if there is any politicians left
     * @return boolean true/false if any politicians is left on the list.
     */
    public boolean hasNextPolitician(){
        readyIndex();
        boolean hasNext = politicians.size()>0;
        if(hasNext) return true;
        return false;
    }

    public List<PoliticianImpl> getPoliticians(){
        return politicians;
    }

}
