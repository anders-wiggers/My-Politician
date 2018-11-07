package aacorp.mypolitician.patterns;


import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

import aacorp.mypolitician.Implementation.MatchImpl;
import aacorp.mypolitician.framework.Match;

public class Database {
    private static Database instance;
    private FirebaseFirestore db;

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
    }

    private  MatchImpl match;
    public Match getAMatch(){
        db.collection("politicians").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> myData = queryDocumentSnapshots.getDocuments();
                //match = queryDocumentSnapshots.toObjects(MatchImpl.class);
                Map<String,Object> data = myData.get(0).getData();
                match = new MatchImpl(data.get("name").toString(),data.get("party").toString());
                Log.d("Match: ", match.getName());

            }
        });
        return match;
    }


}
