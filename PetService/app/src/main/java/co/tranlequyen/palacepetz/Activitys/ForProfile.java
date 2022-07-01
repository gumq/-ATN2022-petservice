package co.tranlequyen.palacepetz.Activitys;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import co.tranlequyen.palacepetz.R;


public class ForProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_profile);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
