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


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aacorp.mypolitician.Implementation.PoliticianImpl;
import aacorp.mypolitician.Implementation.StrengthImpl;
import aacorp.mypolitician.framework.Politician;

public class Database {
    private static Database instance;  //Singleton instance
    private FirebaseFirestore db;   //Firestore instance
    private List<PoliticianImpl> politicians = new ArrayList<>(); //Politicians fetched from the database


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

    /**
     * Method that create a politician and transfers the newly created politicians
     * to the Firestore database.
     */
    public void createPolitician(){
        PoliticianImpl politician = new PoliticianImpl();
        politician.setName("lala");
        politician.setArea(new GeoPoint(50,10));
        politician.setBannerId(12345);
        politician.setParty("Liberal");
        politician.setProfilePictureId(12345);

        StrengthImpl s = new StrengthImpl();
        StrengthImpl s1 = new StrengthImpl();
        Map<String,StrengthImpl> sl = new HashMap<>();

        s.setPercent(20);
        s.setText("ayyy");
        sl.put("def",s);

        s1.setText("I believe in the Power");
        s1.setPercent(50);
        sl.put("wor",s1);

        politician.setStrength(sl);

        System.out.println(politician.toString());
        db.collection("politicians").add(politician);
    }

}
