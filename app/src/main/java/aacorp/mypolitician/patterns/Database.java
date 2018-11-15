/*
 * Copyright (c) 2018.
 * @author Anders Bille Wiggers
 * for Introduction-to-Human-Computer-InteractionI course.
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
    private static Database instance;
    private FirebaseFirestore db;
    private List<PoliticianImpl> politicians = new ArrayList<>();


    protected Database(){
        initialize();
    }

    public static synchronized Database getInstance(){
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }

    public FirebaseFirestore getDatabase(){
        return db;
    }


    private void initialize(){
        db = FirebaseFirestore.getInstance();

        db.collection("politicians").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                politicians = queryDocumentSnapshots.toObjects(PoliticianImpl.class);
            }
        });
    }

    public Politician fetchDummy(){
        return politicians.get(0);
    }

    public Politician fetchRandomPolitician(){
        int rngPolitician = (int) Math.floor(Math.random() * Math.floor(politicians.size()));
        return politicians.get(rngPolitician);
    }

    public void createPolitician(){
        PoliticianImpl politician = new PoliticianImpl();
        politician.setName("lala");
        politician.setArea(new GeoPoint(50,10));
        politician.setBannerId(12345);
        politician.setParty("Svendborg Partiet");
        politician.setProfilePictureId(12345);

        StrengthImpl s = new StrengthImpl();
        StrengthImpl s1 = new StrengthImpl();
        Map<String,StrengthImpl> sl = new HashMap<>();

        s.setPercent(20);
        s.setText("ayyy");
        sl.put("def",s);

        s1.setText("I belive in the Power");
        s1.setPercent(50);
        sl.put("wor",s1);

        politician.setStrength(sl);

        System.out.println(politician.toString());
        db.collection("politicians").add(politician);
    }
}
