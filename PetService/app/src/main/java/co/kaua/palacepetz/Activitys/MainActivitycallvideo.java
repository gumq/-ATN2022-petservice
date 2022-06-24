//package co.kaua.palacepetz.Activitys;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FieldValue;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.ttl.videocall.R;
//import com.ttl.videocall.adapters.UsersAdapter;
//import com.ttl.videocall.listener.UsersListener;
//import com.ttl.videocall.models.User;
//import com.ttl.videocall.utilities.Constants;
//import com.ttl.videocall.utilities.PreferenceManager;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//public class MainActivitycallvideo extends AppCompatActivity implements UsersListener {
//
//    private PreferenceManager preferenceManager;
//    private List<User> users;
//    private UsersAdapter usersAdapter;
//    private TextView textErrorMessage;
//    private SwipeRefreshLayout swipeRefreshLayout;
//    private ImageView imageConference;
//    private  int REQUEST_CODE_BATTERY_OPTIMIZATIONS = 1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maincallvideo);
//
//        preferenceManager = new PreferenceManager(getApplicationContext());
//        imageConference = findViewById(R.id.imageConference);
//
//        TextView textView = findViewById(R.id.textTitle);
//        textView.setText(String.format(
//                "%s %s",
//                preferenceManager.getString(Constants.KEY_FIRST_NAME),
//                preferenceManager.getString(Constants.KEY_LAST_NAME)
//        ));
//
//        findViewById(R.id.textSignOut).setOnClickListener(view -> signOut());
//
//        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
//            if (task.isSuccessful() && task.getResult() != null) {
//                sendFCMTokenToDatabase(task.getResult().getToken());
//            }
//        });
//
//        RecyclerView usersRecyclerview = findViewById(R.id.recyclerViewUsers);
//        textErrorMessage = findViewById(R.id.textErrorMessage);
//
//        users = new ArrayList<>();
//        usersAdapter = new UsersAdapter(users, this);
//        usersRecyclerview.setAdapter(usersAdapter);
//
//        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
//        swipeRefreshLayout.setOnRefreshListener(this::getUsers);
//
//        getUsers();
//    }
//
//    private void getUsers() {
//        swipeRefreshLayout.setRefreshing(true);
//        FirebaseFirestore database = FirebaseFirestore.getInstance();
//        database.collection(Constants.KEY_COLLECTION_USERS)
//                .get()
//                .addOnCompleteListener(task -> {
//                    swipeRefreshLayout.setRefreshing(false);
//                    String myUsersId = preferenceManager.getString(Constants.KEY_USER_ID);
//                    if (task.isSuccessful() && task.getResult() != null) {
//                        users.clear();
//                        for (QueryDocumentSnapshot documentSnapshot: task.getResult()) {
//                            if (myUsersId.equals(documentSnapshot.getId())) {
//                                continue;
//                            }
//
//                            User user = new User();
//                            user.firstName  = documentSnapshot.getString(Constants.KEY_FIRST_NAME);
//                            user.lastName   = documentSnapshot.getString(Constants.KEY_LAST_NAME);
//                            user.email      = documentSnapshot.getString(Constants.KEY_EMAIL);
//                            user.token      = documentSnapshot.getString(Constants.KEY_FCM_TOKEN);
//                            users.add(user);
//                        }
//
//                        if (users.size() > 0) {
//                            usersAdapter.notifyDataSetChanged();
//                        } else {
//                            textErrorMessage.setText(String.format("%s", "Không có người dùng nào"));
//                            textErrorMessage.setVisibility(View.VISIBLE);
//                        }
//                    } else {
//                        textErrorMessage.setText(String.format("%s", "Không có người dùng nào"));
//                        textErrorMessage.setVisibility(View.VISIBLE);
//                    }
//                });
//    }
//
//    private void sendFCMTokenToDatabase(String token) {
//        FirebaseFirestore database = FirebaseFirestore.getInstance();
//        DocumentReference documentReference =
//                database.collection(Constants.KEY_COLLECTION_USERS).document(
//                        preferenceManager.getString(Constants.KEY_USER_ID)
//                );
//        documentReference.update(Constants.KEY_FCM_TOKEN, token)
//                .addOnFailureListener(e -> Toast.makeText(MainActivitycallvideo.this, "\n" +
//                        "Không thể gửi mã thông báo: "+e.getMessage(), Toast.LENGTH_SHORT).show());
//    }
//
//    private void signOut() {
//        Toast.makeText(this, "Đăng xuất...", Toast.LENGTH_SHORT).show();
//        FirebaseFirestore database = FirebaseFirestore.getInstance();
//        DocumentReference documentReference =
//                database.collection(Constants.KEY_COLLECTION_USERS).document(
//                        preferenceManager.getString(Constants.KEY_USER_ID)
//                );
//        HashMap<String, Object> updates = new HashMap<>();
//        updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());
//        documentReference.update(updates)
//                .addOnSuccessListener(aVoid -> {
//                    preferenceManager.clearPreferences();
//                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
//                    finish();
//                })
//                .addOnFailureListener(e -> Toast.makeText(MainActivitycallvideo.this, "Không thể đăng xuất", Toast.LENGTH_SHORT).show());
//    }
//
//    @Override
//    public void initiateVideoMeeting(User user) {
//
//        if (user.token == null || user.token.trim().isEmpty()) {
//            Toast.makeText(this, user.firstName+ " " +user.lastName+ " Không có trong cuộc gọi thoại", Toast.LENGTH_SHORT).show();
//        } else {
//            Intent intent = new Intent(getApplicationContext(), com.ttl.videocall.activites.OutgoingInvitationActivity.class);
//            intent.putExtra("user", user);
//            intent.putExtra("type", "video");
//            startActivity(intent);
//        }
//
//    }
//
//    @Override
//    public void initiateAudioMeeting(User user) {
//        if (user.token == null || user.token.trim().isEmpty()) {
//            Toast.makeText(this, user.firstName+ " " +user.lastName+ " Không có trong cuộc gọi thoại", Toast.LENGTH_SHORT).show();
//        } else {
//            Intent intent = new Intent(getApplicationContext(), com.ttl.videocall.activites.OutgoingInvitationActivity.class);
//            intent.putExtra("user", user);
//            intent.putExtra("type", "audio");
//            startActivity(intent);
//        }
//
//    }
//
//    @Override
//    public void onMultipleUsersAction(Boolean isMultipleUsersSelected) {
//
//    }
//}