package aacorp.mypolitician.patterns;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import aacorp.mypolitician.frameword.Match;

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

    public Match getAMatch(){
        Log.e("db svar",db.collection("politicians").get().toString());
        return null;
    }


}
