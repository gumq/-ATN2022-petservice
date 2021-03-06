package co.tranlequyen.palacepetz.Activitys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

import co.tranlequyen.palacepetz.Adapters.Warnings;


import co.tranlequyen.palacepetz.Firebase.ConfFirebase;
import co.tranlequyen.palacepetz.R;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class SplashScreen extends AppCompatActivity {
    ConstraintLayout base_animation_splash;

    //  Create timer
    private final Handler timer = new Handler();

    final String email = "usermobile@palacepetz.com";
    final String password = "mobile123456";
    private int MAIN_TIMER = 500;

    //  Firebase
    FirebaseAuth auth;
    static final Retrofit retrofitMobile = new Retrofit.Builder()
            .baseUrl("https://palacepetzapi.herokuapp.com/mobile/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        base_animation_splash = findViewById(R.id.base_animation_splash);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1)
            launchShortcuts();



        //  Set Containers on Screen
        base_animation_splash.setVisibility(View.VISIBLE);
        auth = ConfFirebase.getFirebaseAuth();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            Log.d("FirstLogin", "First login is ok");
            timer.postDelayed(this::verifyIfUsersLogged, 500);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private void launchShortcuts() {
        ShortcutManager mShortcutManager = getSystemService(ShortcutManager.class);

        //  ShortCut All Products
        ShortcutInfo mShortcutProduct = CreateShortCut(CreateIntent(10), getString(R.string.products),
                getString(R.string.see_all_products), R.drawable.products_sheetmenu, 10);

        //  ShortCut Shopping Cart
        ShortcutInfo mShortcutShoppingCart = CreateShortCut(CreateIntent(20), getString(R.string.my_shopping_cart),
                getString(R.string.see_my_shoppingCart), R.mipmap.ic_shopping_cart, 20);

        //  ShortCut Services
        ShortcutInfo mShortcutServices = CreateShortCut(CreateIntent(30), getString(R.string.services),
                getString(R.string.schedule_a_service), R.drawable.ic_consultation, 30);

        //  ShortCut Palace Fountain
        ShortcutInfo mShortcutPalaceFountain = CreateShortCut(CreateIntent(40), getString(R.string.palaceFountain),
                getString(R.string.manage_Fountain), R.drawable.fountain_sheetmenu, 40);

        //  ShortCut Cards
        ShortcutInfo mShortcutCards = CreateShortCut(CreateIntent(50), getString(R.string.myCards),
                getString(R.string.myCards), R.drawable.cards_icon, 50);

        mShortcutManager.setDynamicShortcuts(Arrays.asList(mShortcutProduct, mShortcutShoppingCart, mShortcutServices, mShortcutCards, mShortcutPalaceFountain));
    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private ShortcutInfo CreateShortCut(Intent intent, String ShortLabel, String LongLabel, int icon, int id) {
        return new ShortcutInfo.Builder(this, "Shortcut_" + id)
                .setShortLabel(ShortLabel)
                .setLongLabel(LongLabel)
                .setIcon(Icon.createWithResource(this, icon))
                .setIntent(intent)
                .build();
    }

    private Intent CreateIntent(int shortCutId){
         return new Intent(this, MainActivity.class)
        .putExtra("shortcut", shortCutId)
        .setAction(Intent.ACTION_VIEW);
    }

    public void verifyIfUsersLogged() {
        //  Verification of user preference information
        SharedPreferences sp_First = getSharedPreferences("First_See", MODE_PRIVATE);
        if (sp_First.contains("viewed"))
            timer.postDelayed(this::GoToMain, MAIN_TIMER);
        else
            timer.postDelayed(this::GoToIntro, 2000);

        Warnings.ApplyDevelopersPref(SplashScreen.this, 0);
    }

    private void GoToMain(){
        Intent goto_main = new Intent(SplashScreen.this, MainActivity.class);
        goto_main.putExtra("shortcut", 0);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),R.anim.move_to_left, R.anim.move_to_right);
        ActivityCompat.startActivity(SplashScreen.this, goto_main, activityOptionsCompat.toBundle());
        finish();
    }

    private void GoToIntro(){
        Intent goto_intro = new Intent(SplashScreen.this, IntroActivity.class);
        goto_intro.putExtra("shortcut", 0);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),R.anim.move_to_left, R.anim.move_to_right);
        ActivityCompat.startActivity(SplashScreen.this, goto_intro, activityOptionsCompat.toBundle());
        finish();
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}