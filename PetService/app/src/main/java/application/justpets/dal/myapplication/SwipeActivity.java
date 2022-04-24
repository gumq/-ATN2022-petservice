package application.justpets.dal.myapplication;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class SwipeActivity extends AppCompatActivity {

    ViewPager viewPager;
    ViewSwipeAdapter adapter;
    ImageView start1;
    Animation bottom,transition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        start1 = (ImageView) findViewById(R.id.start);
        bottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        transition = AnimationUtils.loadAnimation(this, R.anim.transition);
        start1.setAnimation(bottom);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAnimation(transition);
        Log.d("S", "viewpager initiated");
        adapter = new ViewSwipeAdapter(SwipeActivity.this);
        viewPager.setAdapter(adapter);
        Log.d("ss", "Adapter initiated");


    }

    public void getStarted(View view)
    {



        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }
}
