package aacorp.mypolitician.patterns;

import com.google.firebase.firestore.FirebaseFirestore;

class Database {
    private static final Database instance = new Database();
    private FirebaseFirestore db;

    private Database(){}

    public static Database getInstance(){
        return instance;
    }

    public FirebaseFirestore getDatabase(){
        return db = FirebaseFirestore.getInstance();
    }




}
