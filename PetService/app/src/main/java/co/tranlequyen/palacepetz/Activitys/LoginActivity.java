package co.tranlequyen.palacepetz.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import co.tranlequyen.palacepetz.Adapters.LoadingDialog;
import co.tranlequyen.palacepetz.Adapters.Warnings;
import co.tranlequyen.palacepetz.Data.User.DtoUser;

import co.tranlequyen.palacepetz.R;


public class LoginActivity extends AppCompatActivity {


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://goglemap-6d1f6-default-rtdb.firebaseio.com/");

    //  Login items


    private EditText editLogin_emailUser, editLogin_passwordUser;
    private LinearLayout txt_SingUpLogin;

    //  User information
    private int id_user, user_type, status;
    private String name_user, Email_Username, cpf_user, address_user, complement, zipcode, phone_user, birth_date, img_user, password;

    //  Next Activity
    private TextView txt_forgot_your_password;

    //  Login bottom
    private CardView cardBtn_SingIn;

    //  Tools
    private LoadingDialog loadingDialog;
    private static InputMethodManager imm;

    //  Set preferences
    private SharedPreferences mPrefs;
    private static final String PREFS_NAME = "myPrefs";
    private int SHORTCUT_ID = 0;

    //  Fountain info
    boolean isDevicePre;

    //  Retrofit / Firebase
    FirebaseAnalytics mFirebaseAnalytics;
    @SuppressWarnings("FieldCanBeLocal")

    private String emailUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Ids();
        getWindow().setStatusBarColor(getColor(R.color.background_bottom));
        getWindow().setNavigationBarColor(getColor(R.color.white));
        cardBtn_SingIn.setElevation(20);
        verifyIfUsersLogged();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (!(bundle == null)){

            Email_Username = bundle.getString("email_user");
            cpf_user = bundle.getString("sdt");
            password = bundle.getString("password_user");
            SHORTCUT_ID = bundle.getInt("shortcut");
            editLogin_emailUser.setText(Email_Username);
            editLogin_passwordUser.setText(password);
        }

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //  Btn to Sing In
        cardBtn_SingIn.setOnClickListener(v -> {

            editLogin_emailUser = findViewById(R.id.editLogin_emailUser);
            editLogin_passwordUser = findViewById(R.id.editLogin_emailUser);
            cpf_user = editLogin_emailUser.getText().toString();
            password = editLogin_passwordUser.getText().toString();


            DtoUser dtoUser = new DtoUser(cpf_user, password);

            databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(editLogin_emailUser.getText().toString())){

                        final String getPassword  = snapshot.child(cpf_user).child("password").getValue(String.class);
                        if(getPassword.equals(editLogin_passwordUser.getText())){

                            Toast.makeText(LoginActivity.this,"đăng nhập thành công",Toast.LENGTH_LONG);
                            SharedPreferences prefsFirst = getSharedPreferences("First_See", MODE_PRIVATE);
                            SharedPreferences.Editor prefsFirstIntro = prefsFirst.edit();
                            prefsFirstIntro.putBoolean("viewed", true);
                            prefsFirstIntro.apply();
                            GoToMain(cpf_user, name_user, emailUser,
                                     password);
                            Intent goTo_SingIn = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(goTo_SingIn);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this,"Tài khoản hoặc mật khẩu sai",Toast.LENGTH_LONG);
                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    loadingDialog.dimissDialog();
                    Warnings.showWeHaveAProblem(LoginActivity.this);
                }
            });
//            Intent goTo_Main = new Intent(this, MainActivity.class);
//            goTo_Main.putExtra("id_user", id_user);
//            goTo_Main.putExtra("name_user", name_user);
//            goTo_Main.putExtra("email_user", emailUser);
//            goTo_Main.putExtra("cpf_user", cpf_user);
//            goTo_Main.putExtra("address_user", address_user);
//            goTo_Main.putExtra("complement", complement);
//            goTo_Main.putExtra("zipcode", zipcode);
//            goTo_Main.putExtra("phone_user", phone_user);
//            goTo_Main.putExtra("birth_date", birth_date);
//            goTo_Main.putExtra("img_user", img_user);
//            goTo_Main.putExtra("password", password);
//            goTo_Main.putExtra("shortcut", SHORTCUT_ID);
//            goTo_Main.putExtra("AddressAlert", true);
//            startActivity(goTo_Main);
//            finish();
//            if (editLogin_emailUser.getText().length() == 0)
//                showError(editLogin_emailUser, getString(R.string.email_required));
//            else if(editLogin_passwordUser.getText().length() == 0)
//                showError(editLogin_passwordUser, getString(R.string.password_required));
//            else {
//                cardBtn_SingIn.setElevation(0);
//                cardBtn_SingIn.setEnabled(false);
//                Email_Username = editLogin_emailUser.getText().toString().trim();
//                password = editLogin_passwordUser.getText().toString().trim();
//                try {
//                    if (ValidateCPF.isValidCPF(Email_Username)){
//                        StringBuilder sb = new StringBuilder(Email_Username.trim().replace(".", "").replace("-", ""));
//                        sb = sb.insert(3,".");
//                        sb = sb.insert(7,".");
//                        sb = sb.insert(11,"-");
//                        Email_Username = sb + "";
//                    }
//                    DoLogin(Email_Username + "", password);
//                }catch (Exception ex){
//                    DoLogin(Email_Username, password);
//                    Log.d("Login", ex.toString());
//                }
//            }
        });

        txt_SingUpLogin.setOnClickListener(v -> {
            Intent goTo_SingUp = new Intent(this, CreateAccountActivity.class);
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),R.anim.move_to_left, R.anim.move_to_right);
            ActivityCompat.startActivity(this, goTo_SingUp, activityOptionsCompat.toBundle());
            finish();
            getWindow().setNavigationBarColor(getColor(R.color.background_top));
        });

        txt_forgot_your_password.setOnClickListener(v -> {
            Intent goTo_ForgotPassword = new Intent(this, ForgotPasswordActivity.class);
            startActivity(goTo_ForgotPassword);
        });
    }

    private void showError(@NonNull EditText editText, String error){
        editText.requestFocus();
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        editText.setError(error);
        cardBtn_SingIn.setElevation(20);
    }

    private void Ids() {
        cardBtn_SingIn = findViewById(R.id.cardBtn_SingIn);
        txt_forgot_your_password = findViewById(R.id.txt_forgot_your_password);
        txt_SingUpLogin = findViewById(R.id.txt_SingUpLogin);
        editLogin_emailUser = findViewById(R.id.editLogin_emailUser);
        editLogin_passwordUser = findViewById(R.id.editLogin_passwordUser);
        loadingDialog = new LoadingDialog(LoginActivity.this);
    }

    public void verifyIfUsersLogged() {
        //  Verification of user preference information
        mPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (sp.contains("pref_email") && sp.contains("pref_password")) {
            String emailPref = sp.getString("pref_email", "not found");
            String PassPref = sp.getString("pref_password", "not found");

            DoLogin(emailPref, PassPref);
        }
    }

    private void DoLogin(String cpf_user, String password) {

        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(cpf_user)){

            final String getPassword  = snapshot.child(cpf_user).child("password").getValue(String.class);
            if(getPassword.equals(editLogin_passwordUser.getText())){
                Toast.makeText(LoginActivity.this,"đăng nhập thành công",Toast.LENGTH_LONG);
                    SharedPreferences prefsFirst = getSharedPreferences("First_See", MODE_PRIVATE);
                     SharedPreferences.Editor prefsFirstIntro = prefsFirst.edit();
                            prefsFirstIntro.putBoolean("viewed", true);
                                    prefsFirstIntro.apply();
                                    GoToMain( cpf_user,name_user, emailUser, password);
                Intent goTo_SingIn = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(goTo_SingIn);
                finish();
            }else{
                Toast.makeText(LoginActivity.this,"Tài khoản hoặc mật khẩu sai",Toast.LENGTH_LONG);
            }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingDialog.dimissDialog();
                Warnings.showWeHaveAProblem(LoginActivity.this);
            }
        });
    }

    private void GoToMain(String cpf_user,String name_user, String emailUser,String password) {

        Intent goTo_Main = new Intent(this, MainActivity.class);
//        goTo_Main.putExtra("id_user", id_user);
        goTo_Main.putExtra("name_user", name_user);
        goTo_Main.putExtra("email_user", emailUser);
        goTo_Main.putExtra("cpf_user", cpf_user);
//        goTo_Main.putExtra("address_user", address_user);
//        goTo_Main.putExtra("complement", complement);
//        goTo_Main.putExtra("zipcode", zipcode);
//        goTo_Main.putExtra("phone_user", phone_user);
//        goTo_Main.putExtra("birth_date", birth_date);
//        goTo_Main.putExtra("img_user", img_user);
        goTo_Main.putExtra("password", password);
        goTo_Main.putExtra("shortcut", SHORTCUT_ID);
        goTo_Main.putExtra("AddressAlert", true);
        startActivity(goTo_Main);
        finish();
    }
}

