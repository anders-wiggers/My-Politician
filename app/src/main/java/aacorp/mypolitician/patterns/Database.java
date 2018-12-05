/**
 *  Database is a Singleton class which handles all communication with
 *  Firestore.
 *
 *  @author Anders Bille Wiggers
 *  @autho
 *  for Introduction-to-Human-Computer-InteractionI course.
 *  Copyright (c) 2018.
 *
 */

package aacorp.mypolitician.patterns;


import android.graphics.Color;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
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
    private List<PoliticianImpl> politiciansFixed = new ArrayList<>();
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

    /**
     * Initialises the Firestore database and fetches politicians to the ArrayList politicians.
     */
    private void initialize(){
        db = FirebaseFirestore.getInstance();

        db.collection("politicians").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                politicians = queryDocumentSnapshots.toObjects(PoliticianImpl.class);
                politiciansFixed = queryDocumentSnapshots.toObjects(PoliticianImpl.class);
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



    public Politician fetchLocalPolitician(){
            return null;
    }

    public void createParty(){
        Party party = new Party();
        party.setName("Alternativet");
        party.setColor(Color.GREEN);
        party.setColorOfBlock("Red block");
        db.collection("parties").add(party);
    }

    /**
     * Method that create a politician and transfers the newly created politicians
     * to the Firestore database.
     */
    public void createPolitician(){
        DocumentReference ref = db.collection("politicians").document();
        String myId = ref.getId();

        PoliticianImpl politician = new PoliticianImpl();
        politician.setName("Jacob Bundsgaard");
        politician.setArea(new GeoPoint(56.1567,10.2108));
        politician.setBannerId("");
        politician.setParty("Socialdemokratiet");
        politician.setProfilePictureId("");
        politician.setIsMale(true);
        politician.setId(myId);

        StrengthImpl s = new StrengthImpl();
        StrengthImpl s1 = new StrengthImpl();
        StrengthImpl s2 = new StrengthImpl();
        Map<String,StrengthImpl> sl = new HashMap<>();

        s.setPercent(80);
        s.setText("Vi bor i en by i hastig udvikling. Generationer har gennem hårdt arbejde skabt vores by, og derfor har vi alle et ansvar for at gøre Aarhus endnu bedre – hvad end vi har boet her hele vores liv eller blev aarhusianere i sidste måned. Alle skal have del i fremtidens muligheder. Derfor skal vi sikre, at alle uanset uddannelse og baggrund kan bidrage. Der skal være plads til at tænke stort for os selv, for hinanden og for vores by. Vores drømme skal gøres til en del af Aarhus.");
        sl.put("mon",s);

        s1.setText("\n" +
                "Aarhus skal være en tryg by. Vi skal alle passe på hinanden, fordi tryghed skabes i stærke, inkluderende og forpligtende fællesskaber. " +
                "Vi skal sætte hårdt ind over for bander, organiseret kriminalitet og parallelsamfund, der melder sig ud af Aarhus. Alle aarhusianere skal vide, " +
                "at vi bor i en by, som rækker hånden ud, hvis behovet opstår. Og vi skal vide, at vi selv har en forpligtelse til at bidrage, når vi er en del af Aarhus. ");
        s1.setPercent(60);
        sl.put("def",s1);

        s2.setText("I Aarhus tager vi ansvar over for naturen, vores klima og vores fremtid. Vi skal gøre Aarhus grønnere, og byens borgere skal sammen kunne nyde " +
                "den fantastiske aarhusianske natur. At være en grøn by betyder også, at vi skal gå forrest med ambitiøse målsætninger for at reducere vores fælles " +
                "påvirkning af klimaet. Vi skylder vores børn og børnebørn at aflevere et grønnere Aarhus end det, vi overtog. Vores natur er en del af Aarhus, " +
                "og den skal vi passe på.");
        s2.setPercent(40);
        sl.put("env",s2);


        politician.setStrength(sl);

        System.out.println(politician.toString());


        db.collection("politicians").document(myId).set(politician);
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
                    user1.setLocalPoliticianSetting(false);
                    db.collection("users").document(user1.getUsername()).set(user1);
                    user = user1;
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

    public void updateLocalPoliticianSetting(Boolean b){
        user.setLocalPoliticianSetting(b);
        db.collection("users").document(user.getUsername()).set(user);
    }

    public void addLikeToUser(String id) {
        user.addLikedPolitician(id);
        db.collection("users").document(user.getUsername()).set(user);

    }

    public void removeLikeFromUser(String id){
        user.removeLikedPolitician(id);
        db.collection("users").document(user.getUsername()).set(user);
    }

    public void removeSeenFromUser(String id){
        user.removeSeenPolitician(id);
        db.collection("users").document(user.getUsername()).set(user);
    }

    /**
     * Readys the index of politicians for feching by removing politicians that has been
     * like or dislike from the rest.
     */
    public void readyIndex(){
        List<String> ids = new ArrayList<>();
        ids.addAll(user.getSeenPoliticians()); ids.addAll(user.getLikedPoliticians());
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

    public List<PoliticianImpl> getPoliticiansFixed(){
        return politiciansFixed;
    }

    public void clearUser(){
        User user1 = new User();
        user1.setLikedPoliticians(new ArrayList<String>());
        user1.setSeenPoliticians(new ArrayList<String>());
        user1.setUsername(user.getUsername());
        user1.setEmail(user.getEmail());
        db.collection("users").document(user1.getUsername()).set(user1);
        user = user1;
        initialize();
    }

}
