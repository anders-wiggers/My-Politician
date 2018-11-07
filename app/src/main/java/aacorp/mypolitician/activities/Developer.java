package aacorp.mypolitician.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import aacorp.mypolitician.R;
import aacorp.mypolitician.patterns.Database;

public class Developer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
        Database.getInstance();
    }

    public void MatchList(View view){
        /*
        Intent intent = new Intent(this,MatchList.class);
        startActivity(intent);
        */
        TextView textView = findViewById(R.id.textView);

        textView.setText(Database.getInstance().fetchRandomPolitician(true).getName());
        //Log.d("DbTest",Database.getInstance().getAMatch().getName());


    }

    public void Login(View view){
        Intent intent = new Intent(this,LogIn.class);
        startActivity(intent);
    }
}
