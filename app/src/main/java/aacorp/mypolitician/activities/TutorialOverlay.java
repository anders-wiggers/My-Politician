package aacorp.mypolitician.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import aacorp.mypolitician.R;

public class TutorialOverlay extends AppCompatActivity {
    private ImageView img3;
    private ImageView img4;
    private ImageView img5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_overlay);
        img3 = findViewById(R.id.imageView3);
        img4 = findViewById(R.id.imageView4);
        img5 = findViewById(R.id.imageView5);
        img4.setVisibility(View.GONE);
        img5.setVisibility(View.GONE);
    }
    public void changeBackground(View view){
        img3.setVisibility(View.GONE);
        img4.setVisibility(View.VISIBLE);
        img5.setVisibility(View.GONE);
    }

    public void changeBackground2(View view){
        img4.setVisibility(View.GONE);
        img5.setVisibility(View.VISIBLE);
    }

    public void changeBackground3(View view){
        Intent intent = new Intent(this, aacorp.mypolitician.activities.Match.class);
        startActivity(intent);
    }

}
