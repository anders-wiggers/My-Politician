package aacorp.mypolitician.patterns;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;
import java.util.Timer;

import aacorp.mypolitician.Implementation.MatchImpl;
import aacorp.mypolitician.framework.Match;

public class Database {
    private static Database instance;
    private FirebaseFirestore db;
    private Timer timer = new Timer();


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
                List<DocumentSnapshot> myData = queryDocumentSnapshots.getDocuments();
                //match = queryDocumentSnapshots.toObjects(MatchImpl.class);
                match = (MatchImpl) generatePolitician(myData.get(0).getData());
            }
        });
    }
    private  MatchImpl match;

    private Match generatePolitician(Map<String,Object> data){
        Match politician = new MatchImpl(data.get("name").toString(),data.get("party").toString());
        return politician;
    }

    private void gogog(){

    }

    private MatchImpl politician;
    public Match fetchRandomPolitician(boolean first){
        db.collection("politicians").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> myData = queryDocumentSnapshots.getDocuments();
                //match = queryDocumentSnapshots.toObjects(MatchImpl.class);
                politician = (MatchImpl) generatePolitician(myData.get(0).getData());
            }
        });

        return politician;
    }

}
