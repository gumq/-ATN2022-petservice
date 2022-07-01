package co.tranlequyen.palacepetz.Activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

import co.tranlequyen.palacepetz.R;

public class DashboardActivity_Callvideo extends AppCompatActivity {

    EditText SecretCodeBox;
    Button joinBtn,shareBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_callvideo);

        SecretCodeBox=findViewById(R.id.codeBox);
        joinBtn=findViewById(R.id.joinBtn);


        URL serverURL;
        try {
            serverURL=new URL("https://meet.jit.si/");
            JitsiMeetConferenceOptions defaultoptions= new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverURL)
                    .setWelcomePageEnabled(false)
                    .setSubject("Phòng tư vấn cho vật nuôi, thú cưng")
                    .build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JitsiMeetConferenceOptions options=new JitsiMeetConferenceOptions.Builder()
                        .setRoom(SecretCodeBox.getText().toString())
                        .setWelcomePageEnabled(false)
                        .build();
                JitsiMeetActivity.launch(DashboardActivity_Callvideo.this,options);
            }
        });
    }
}