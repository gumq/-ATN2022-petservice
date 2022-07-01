//package co.kaua.palacepetz.Activitys;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.media.MediaPlayer;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.util.Log;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.localbroadcastmanager.content.LocalBroadcastManager;
//
//
//import co.kaua.palacepetz.Activitys.Services.ApiClient;
//import co.kaua.palacepetz.Activitys.Services.ApiService;
//import co.kaua.palacepetz.Activitys.uitilities.Constants;
//
//import org.jitsi.meet.sdk.JitsiMeetActivity;
//import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.net.URL;
//
//import co.kaua.palacepetz.R;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class IncomingInvitationActivity extends AppCompatActivity {
//
//    private String meetingType = null;
//MediaPlayer player;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_incoming_invitation);
//
//        ImageView imageMeetingType = findViewById(R.id.imageMeetingType);
//        meetingType = getIntent().getStringExtra(Constants.REMOTE_MSG_MEETING_TYPE);
//
//        if (meetingType != null) {
//            if (meetingType.equals("video")) {
//                imageMeetingType.setImageResource(R.drawable.ic_video);
//            } else {
//                imageMeetingType.setImageResource(R.drawable.ic_audio);
//            }
//        }
//
//        TextView textFirstChar = findViewById(R.id.textFirstChar);
//        TextView textUserName = findViewById(R.id.textUserName);
//        TextView textEmail = findViewById(R.id.textEmail);
//
//        String firstName = getIntent().getStringExtra(Constants.KEY_FIRST_NAME);
//        if (firstName != null) {
//            textFirstChar.setText(firstName.substring(0, 1));
//        }
//
//        textUserName.setText(String.format(
//                "%s %s",
//                firstName,
//                getIntent().getStringExtra(Constants.KEY_LAST_NAME)
//        ));
//
//        textEmail.setText(getIntent().getStringExtra(Constants.KEY_EMAIL));
//
//        ImageView imageAcceptInvitation = findViewById(R.id.imageAcceptInvitation);
//        imageAcceptInvitation.setOnClickListener(view -> sendInvitationResponse(
//                Constants.REMOTE_MSG_INVITATION_ACCEPTED,
//                getIntent().getStringExtra(Constants.REMOTE_MSG_INVITER_TOKEN)
//        ));
//
//        ImageView imageRejectInvitation = findViewById(R.id.imageRejectInvitation);
//        imageRejectInvitation.setOnClickListener(view -> sendInvitationResponse(
//                Constants.REMOTE_MSG_INVITATION_REJECTED,
//                getIntent().getStringExtra(Constants.REMOTE_MSG_INVITER_TOKEN)
//        ));
//
//        new CountDownTimer(30000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
//                play();
//            }
//
//            public void onFinish() {
//                stop();
//                sendInvitationResponse(
//                        Constants.REMOTE_MSG_INVITATION_REJECTED,
//                        getIntent().getStringExtra(Constants.REMOTE_MSG_INVITER_TOKEN));
//
//                finish();
//                Toast.makeText(IncomingInvitationActivity.this,"Kết thúc", Toast.LENGTH_SHORT).show();
//            }
//
//        }.start();
//
//
//    }
//public  void play(){
//if(player == null){
//    player = MediaPlayer.create(this,R.raw.ocsentruyentin);
//}
//player.start();
//
//}
//    public  void stop(){
//if(player!= null){
//    player.release();
//    player = null;
//    Toast.makeText(IncomingInvitationActivity.this,"Tắt âm thanh", Toast.LENGTH_SHORT).show();
//}
//    }
////    private void settimeout() {
////        long startTime = System.currentTimeMillis();
////        long elapsedTime = 0L;
////        new CountDownTimer()
////        while (elapsedTime >= 6*1000) {
////            //perform db poll/check
////            elapsedTime = (new Date()).getTime() - startTime;
////            Toast.makeText(this, "10S", Toast.LENGTH_SHORT).show();
////           sendInvitationResponse(
////                    Constants.REMOTE_MSG_INVITATION_REJECTED,
////                    getIntent().getStringExtra(Constants.REMOTE_MSG_INVITER_TOKEN));
////            finish();
//////            Intent intent = new Intent(IncomingInvitationActivity.this,MainActivity.class);
//////            startActivity(intent);
////
////        }
////    }
//
//    private void sendInvitationResponse(String type, String receiverToken) {
//        try {
//
//            JSONArray tokens = new JSONArray();
//            tokens.put(receiverToken);
//
//            JSONObject body = new JSONObject();
//            JSONObject data = new JSONObject();
//
//            data.put(Constants.REMOTE_MSG_TYPE, Constants.REMOTE_MSG_INVITATION_RESPONSE);
//            data.put(Constants.REMOTE_MSG_INVITATION_RESPONSE, type);
//
//            body.put(Constants.REMOTE_MSG_DATA, data);
//            body.put(Constants.REMOTE_MSG_REGISTRATION_IDS, tokens);
//
//            sendRemoteMessage(body.toString(), type);
//
//        } catch (Exception e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            finish();
//        }
//    }
//
//
//    private void sendRemoteMessage(String remoteMessageBody, String type) {
//        ApiClient.getClient().create(ApiService.class).sendRemoteMessage(
//                Constants.getRemoteMessageHeaders(), remoteMessageBody
//        ).enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
//                if (response.isSuccessful()) {
//                    if (type.equals(Constants.REMOTE_MSG_INVITATION_ACCEPTED)) {
//
//                        try {
//                            URL serverURL = new URL("https://meet.jit.si");
//
//                            JitsiMeetConferenceOptions.Builder builder = new JitsiMeetConferenceOptions.Builder();
//                            builder.setServerURL(serverURL);
//                            builder.setWelcomePageEnabled(false);
//                           builder.setSubject("Call thả ga >_");
//                            builder.setRoom(getIntent().getStringExtra(Constants.REMOTE_MSG_MEETING_ROOM));
//
//                            if (meetingType.equals("audio")) {
//                                builder.setVideoMuted(true);
//
//                            }
//                            JitsiMeetActivity.launch(IncomingInvitationActivity.this, builder.build());
//                            finish();
//
//                        } catch (Exception exception) {
//                            Toast.makeText(IncomingInvitationActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
//                            finish();
//                        }
//                    } else {
//                        Toast.makeText(IncomingInvitationActivity.this, "Bạn đã từ chối cuộc gọi", Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
//                    if (type.equals(Constants.REMOTE_MSG_INVITATION_CANCELLED)) {
//                        Toast.makeText(IncomingInvitationActivity.this, "Đối phương đã kết thúc cuộc gọi", Toast.LENGTH_SHORT).show();
//                        finish();
//
//                    }
//                } else {
//                    Toast.makeText(IncomingInvitationActivity.this, response.message(), Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
//                Toast.makeText(IncomingInvitationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });
//    }
//
//    private BroadcastReceiver invitationResponseReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//           String  type = intent.getStringExtra(Constants.REMOTE_MSG_INVITATION_CANCELLED);
//            Log.e("c","đã nhận của outcommingacti");
//            if (type != null) {
//
//                if (type.equals(Constants.REMOTE_MSG_INVITATION_CANCELLED)) {
//                    Toast.makeText(IncomingInvitationActivity.this, "Đối phương đã kết thúc cuộc gọi", Toast.LENGTH_SHORT).show();
//                    finish();
//
//                }
//            }
//
//        }
//    };
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
//                invitationResponseReceiver,
//                new IntentFilter(Constants.REMOTE_MSG_INVITATION_RESPONSE)
//        );
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(
//                invitationResponseReceiver
//        );
//    }
//
//}