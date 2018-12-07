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
import android.util.Log;

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
import aacorp.mypolitician.framework.Constants;
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


    /**
     * Method that fill the database base with all danish parties.
     */
    public void createParty(){
        Party party = new Party();
        party.setName("Dansk Folkeparti");
        party.setColor(Color.parseColor("#386891"));
        party.setColorOfBlock("Blue block");
        db.collection("parties").add(party);

        party.setName("Venstre");
        party.setColor(Color.parseColor("#4179A5"));
        party.setColorOfBlock("Blue block");
        db.collection("parties").add(party);

        party.setName("Liberal Alliance");
        party.setColor(Color.parseColor("#3F4E65"));
        party.setColorOfBlock("Blue block");
        db.collection("parties").add(party);

        party.setName("Alternativet");
        party.setColor(Color.parseColor("#54FD59"));
        party.setColorOfBlock("Red block");
        db.collection("parties").add(party);

        party.setName("Radikale Venstre");
        party.setColor(Color.parseColor("#E21588"));
        party.setColorOfBlock("Red block");
        db.collection("parties").add(party);

        party.setName("Socialistisk Folkeparti");
        party.setColor(Color.parseColor("#B91026"));
        party.setColorOfBlock("Red block");
        db.collection("parties").add(party);

        party.setName("Det Konservative Folkeparti");
        party.setColor(Color.parseColor("#214A37"));
        party.setColorOfBlock("Blue block");
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
        politician.setName("BÜNYAMIN SIMSEK");
        politician.setArea(new GeoPoint(56.1567,10.2108));
        politician.setBannerId("https://firebasestorage.googleapis.com/v0/b/my-politician-eb1be.appspot.com/o/politicians%2Fbunyamin-simsek%2F43765040_1142146165966554_4056712003958341632_o.jpg?alt=media&token=6eb82036-f699-4c78-9f3c-f7b8af8c8cb3");
        politician.setParty("Venstre");
        politician.setProfilePictureId("https://firebasestorage.googleapis.com/v0/b/my-politician-eb1be.appspot.com/o/politicians%2Fbunyamin-simsek%2Fbu-e%CC%82nyamin-simsek.jpg?alt=media&token=5dffc45b-67fd-4f40-8492-c0a6956da059");
        politician.setIsMale(true);
        politician.setId(myId);

        StrengthImpl s = new StrengthImpl();
        StrengthImpl s1 = new StrengthImpl();
        StrengthImpl s2 = new StrengthImpl();
        Map<String,StrengthImpl> sl = new HashMap<>();

        s.setPercent(70);
        s.setText("Skolerne skal undervise børn tidligt i ekstreme holdninger:\n" +
                "\n" +
                "- Jo tidligere, jo bedre. Og vi er så heldige, at vi modsat de radikale miljøer har skolen som en unik vej til at påvirke børn og unge");
        sl.put("edu",s);

        s1.setText("Der er forskellige behov i vores områder, det er nemt at sige til alle dagtilbud at alle skal gøre som dem der får mest ud af midlerne – det ønsker jeg ikke at diktere, lokalt ved man bedst hvordan midlerne bedst gør gavn.\n" +
                "\n" +
                "Derfor har jeg også afsat 28 millioner kroner til 70 nye pædagoger.\n" +
                "\n");
        s1.setPercent(55);
        sl.put("mon",s1);

        s2.setText("Jeg har sikret, at der er afsat midler til yderlige tre nye kunststofgræsbaner i Aarhus. Midlerne er fundet ved et forslag fra mit parti om, at nedlægge et kommunalt beskæftigelsesprojekt.\n" +
                "\n" +
                "Mit mål er, at Aarhus skal have en bane for hver 14.000 indbyggere. Det betyder, at der skal etableres 13 flere kunststofbaner.\n" +
                "\n" +
                "I dag har Aarhus en bane for hver 34.000 borger, og i København er der en for hver 17.000.");
        s2.setPercent(30);
        sl.put("act",s2);


        politician.setStrength(sl);

        System.out.println(politician.toString());


        db.collection("politicians").document(myId).set(politician);

        ref = db.collection("politicians").document();
        myId = ref.getId();
        politician.setName("JETTE SKIVE");
        politician.setArea(new GeoPoint(56.1567,10.2108));
        politician.setBannerId("https://firebasestorage.googleapis.com/v0/b/my-politician-eb1be.appspot.com/o/politicians%2Fjette-skrive%2F23755079_941956259294663_1845532625494371532_n.jpg?alt=media&token=019abb00-7fae-4286-92ce-2c65bcfd903d");
        politician.setParty("Dansk Folkeparti");
        politician.setProfilePictureId("https://firebasestorage.googleapis.com/v0/b/my-politician-eb1be.appspot.com/o/politicians%2Fjette-skrive%2Fjette-skive.jpg?alt=media&token=9812777d-0048-4773-bc6c-661b9d46a898");
        politician.setIsMale(false);
        politician.setId(myId);

        s = new StrengthImpl();
        s1 = new StrengthImpl();
        s2 = new StrengthImpl();
        sl = new HashMap<>();

        s.setPercent(90);
        s.setText("Jeg ønsker, at kommunens plejehjem skal være så hjemlige som muligt. Det har jeg kæmpet for i mine fire år som Rådmand for Sundhed og Omsorg. Vi har blandt andet fået den varme hjemmelavede mad tilbage på plejehjemmene og gjort det muligt for beboerne at dufte den friske mad blive lavet samtidig med, at vi har givet beboerne mulighed for at hjælpe til med at tilberede maden hvis de kan.");
        sl.put("edu",s);

        s1.setText("Kommunens personale er pressede, og der er stærkt brug for mere personale til at tage sig af et stigende antal ældre. Får vi ikke mere personale skal det nuværende personale løbe endnu hurtigere i fremtiden. Det kan vi ikke byde dem, og det vil komme til at gå ud over beboerne på kommunens plejehjem. Derfor kæmper jeg for flere varme hænder på ældreområdet i Århus!");
        s1.setPercent(80);
        sl.put("mon",s1);

        s2.setText("På kulturområdet så jeg gerne, at vi i Århus Kommune foretog en anden prioritering af kulturmidlerne. Efter min mening bliver de i dag spredt alt for meget ud over alt for mange forskellige områder og projekter. Jeg så gerne, at man i fremtiden prioriterede mere kvalitetskultur, hvor det er dansk kultur og traditioner, der er i højsædet.");
        s2.setPercent(70);
        sl.put("cul",s2);


        politician.setStrength(sl);

        System.out.println(politician.toString());


        db.collection("politicians").document(myId).set(politician);

        ref = db.collection("politicians").document();
        myId = ref.getId();
        politician.setName("Franciska Rosenkilde");
        politician.setArea(new GeoPoint(55.662,12.5729));
        politician.setBannerId("https://firebasestorage.googleapis.com/v0/b/my-politician-eb1be.appspot.com/o/politicians%2Ffranciska-rosenkilde%2F21151298_1527625440630927_5334454527136855996_n.jpg?alt=media&token=1e34d4f0-e1f1-4a7b-b9a9-1a5385107d43");
        politician.setParty("Alternativet");
        politician.setProfilePictureId("https://firebasestorage.googleapis.com/v0/b/my-politician-eb1be.appspot.com/o/politicians%2Ffranciska-rosenkilde%2Fbro_20171207_br_franciska_rosenkilde_1030-edit%20copy.jpg?alt=media&token=8fed74f2-8f38-4ec4-87aa-f03ee958a662");
        politician.setIsMale(false);
        politician.setId(myId);

        s = new StrengthImpl();
        s1 = new StrengthImpl();
        s2 = new StrengthImpl();
        sl = new HashMap<>();

        s.setPercent(90);
        s.setText("Vores landbrug og fødevareproduktion skal være 100% økologisk. Vi skal have giftfri og næringsrige fødevare. Vi skal have et renere miljø, der også skal kunne dyrkes i fremtiden. Vi skal have højere dyrevelfærd, så dyret er i fokus ikke produktionen.");
        sl.put("org",s);

        s1.setText("Vi er nød til at sætte klimaet øverst på dagsordenen i EU, så al udvikling er miljømæssig bæredygtig");
        s1.setPercent(80);
        sl.put("env",s1);

        s2.setText("Det skal altid være gratis at gå til lægen");
        s2.setPercent(70);
        sl.put("mon",s2);


        politician.setStrength(sl);

        System.out.println(politician.toString());


        db.collection("politicians").document(myId).set(politician);

        //new politician
        ref = db.collection("politicians").document();
        myId = ref.getId();
        politician.setName("Mette Bock");
        politician.setArea(new GeoPoint(55.662,12.5729));
        politician.setBannerId("https://firebasestorage.googleapis.com/v0/b/my-politician-eb1be.appspot.com/o/politicians%2Fmette-bock%2Fforside_desktop2.png?alt=media&token=261f3a13-9f2b-4e06-8ea3-2b5d3544715e");
        politician.setParty("Liberal Alliance");
        politician.setProfilePictureId("https://firebasestorage.googleapis.com/v0/b/my-politician-eb1be.appspot.com/o/politicians%2Fmette-bock%2FMette_Bock_500.jpg.jpg?alt=media&token=51271f08-5dc5-4588-bf35-329490f83aef");
        politician.setIsMale(false);
        politician.setId(myId);

        s = new StrengthImpl();
        s1 = new StrengthImpl();
        s2 = new StrengthImpl();
        sl = new HashMap<>();

        s.setPercent(100);
        s.setText("Jeg ønsker mindre stat og mere privat. En begrænset statsmagt, lav skat, en robust grundlov, uafhængige domstole og klare individuelle rettigheder er en forudsætning for et frit samfund. Jeg tror på medborgerskab, hvor respekten for menneskenes ligeværd og de demokratiske spilleregler er grundlaget for samfundets organisering.");
        sl.put("mon",s);

        s1.setText("Jeg tror på frihed under ansvar. Personligt ansvar er en naturlig følge af den personlige frihed. Man er ansvarlig for sine succeser såvel som sine fiaskoer. Ligesom man får mulighed for at høste frugterne af sin egen indsats og succes, må man acceptere at nedsætte sin levestandard, hvis man enten sløser med sine muligheder eller blot er uheldig.");
        s1.setPercent(60);
        sl.put("fre",s1);

        s2.setText("Jeg ønsker mindre stat og mere privat. En begrænset statsmagt, lav skat, en robust grundlov, uafhængige domstole og klare individuelle rettigheder er en forudsætning for et frit samfund. Jeg tror på medborgerskab, hvor respekten for menneskenes ligeværd og de demokratiske spilleregler er grundlaget for samfundets organisering.");
        s2.setPercent(50);
        sl.put("def",s2);


        politician.setStrength(sl);

        System.out.println(politician.toString());


        db.collection("politicians").document(myId).set(politician);

        //new politician
        ref = db.collection("politicians").document();
        myId = ref.getId();
        politician.setName("Jacob Mark");
        politician.setArea(new GeoPoint(55.662,12.5729));
        politician.setBannerId("https://firebasestorage.googleapis.com/v0/b/my-politician-eb1be.appspot.com/o/politicians%2Fjacob-mark%2F26165476_10155547076463411_1407688060456158526_n.jpg?alt=media&token=dea973b5-d3fa-4af5-8258-78d15ff783b8");
        politician.setParty("Socialistisk Folkeparti");
        politician.setProfilePictureId("https://firebasestorage.googleapis.com/v0/b/my-politician-eb1be.appspot.com/o/politicians%2Fjacob-mark%2FJacob_Mark_500.jpg.jpg?alt=media&token=78053c8a-24a1-4cb9-9b25-c41a96321c9c");
        politician.setIsMale(true);
        politician.setId(myId);

        s = new StrengthImpl();
        s1 = new StrengthImpl();
        s2 = new StrengthImpl();
        sl = new HashMap<>();

        s.setPercent(80);
        s.setText("Danmark skal bygges på socialistiske værdier, og samfundet skal skabe størst mulig velfærd, velstand, frihed og flest muligheder for alle mennesker.");
        sl.put("mon",s);

        s1.setText("Det skal være lige så attraktivt at tage en erhvervsuddannelse som at gå i gymnasiet. Derfor skal adgangskravet til både gymnasier og erhvervsuddannelser være det samme.");
        s1.setPercent(60);
        sl.put("edu",s1);


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

    /**
     * @return the databse state
     */
    public boolean isAppReady() {
        return AppReady;
    }

    /**
     * @return returns the current user
     */
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

    /**
     * add a politician a user has liked to the users list, so that
     * the user wont get the same politicians twice
     * @param id the ID of the politician to be added
     */
    public void addLikeToUser(String id) {
        user.addLikedPolitician(id);
        db.collection("users").document(user.getUsername()).set(user);

    }

    /**
     * removes a likes politician from a user
     * @param id the id of the politician to remove
     */
    public void removeLikeFromUser(String id){
        user.removeLikedPolitician(id);
        db.collection("users").document(user.getUsername()).set(user);
    }

    /**
     * removes a seen politician
     * @param id the id of the politicians to remove
     */
    public void removeSeenFromUser(String id){
        user.removeSeenPolitician(id);
        db.collection("users").document(user.getUsername()).set(user);
    }

    /**
     * Readys the index of politicians for feching by removing politicians that has been
     * like or dislike from the rest.
     */
    public void readyIndex(){
        politicians.addAll(politiciansFixed);
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
     *
     * @param location
     */
    public void readyLocalPoliticians(GeoPoint location){
        politicians.addAll(politiciansFixed);
        List<String> ids = new ArrayList<>();
        ids.addAll(user.getSeenPoliticians()); ids.addAll(user.getLikedPoliticians());
        Iterator<PoliticianImpl> i = politicians.iterator();
        while(i.hasNext()) {
            boolean stopped = false;
            Politician p = i.next();
            for (String id : ids) {
                if (id.equals(p.getId())) {
                    i.remove();
                    stopped = true;
                }
            }
            if(Constants.distance(location.getLatitude(),p.getArea().getLatitude(),location.getLongitude(),p.getArea().getLongitude(),0,0)>50000 && !stopped){
                Log.e("distance",Constants.distance(location.getLatitude(),p.getArea().getLatitude(),location.getLongitude(),p.getArea().getLongitude(),0,0)+"");
                i.remove();
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

    /**
     *
     * @param location
     * @return
     */
    public boolean hasNextPolitician(GeoPoint location){
        readyLocalPoliticians(location);
        boolean hasNext = politicians.size()>0;
        if(hasNext) return true;
        return false;
    }


    /**
     * returns a fixed list of politicians
     * @return List of politician implementations
     */
    public List<PoliticianImpl> getPoliticiansFixed(){
        return politiciansFixed;
    }

    /**
     * Resets the user likes and seen politicians
     */
    public void clearUser(){
        User user1 = new User();
        user1.setLikedPoliticians(new ArrayList<String>());
        user1.setSeenPoliticians(new ArrayList<String>());
        user1.setUsername(user.getUsername());
        user1.setEmail(user.getEmail());
        user1.setLocalPoliticianSetting(user.getLocalPoliticianSetting());
        db.collection("users").document(user1.getUsername()).set(user1);
        user = user1;
        initialize();
    }

}
