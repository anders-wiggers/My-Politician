package aacorp.mypolitician.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import aacorp.mypolitician.R;

public class Match extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);



    }

    public void MatchList(View view){
        Intent intent = new Intent(this,MatchList.class);
        startActivity(intent);
    }

    public void Login(View view){
        Intent intent = new Intent(this,LogInActivity.class);
        startActivity(intent);
    }
}
