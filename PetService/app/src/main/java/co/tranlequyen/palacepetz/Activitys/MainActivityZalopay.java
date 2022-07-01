package co.tranlequyen.palacepetz.Activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import co.tranlequyen.palacepetz.Helper.AppInfo;

import co.tranlequyen.palacepetz.R;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPaySDK;

public class MainActivityZalopay extends AppCompatActivity {
    Button btnPay,btnvideocall;
    String amount = "100000";
    TextView fblink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zalopay);

     //   btnPay = findViewById(R.id.btnPay);
btnvideocall = findViewById(R.id.btnvideocall);
fblink = findViewById(R.id.fblink);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ZaloPaySDK.init(AppInfo.APP_ID, Environment.SANDBOX);
fblink.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Uri webpage = Uri.parse("https://www.facebook.com/Gum-101288085447187/?ref=pages_you_manage");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
    }
});
//        btnPay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                CreateOrder orderApi = new CreateOrder();
//                try {
//                    JSONObject data = orderApi.createOrder(amount);
//                    String code = data.getString("returncode");
//
//                    if (code.equals("1")) {
//
//                        String token = data.getString("zptranstoken");
//
//                        ZaloPaySDK.getInstance().payOrder(MainActivityZalopay.this, token, "demozpdk://app", new PayOrderListener() {
//                            @Override
//                            public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
//                                Toast.makeText(MainActivityZalopay.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onPaymentCanceled(String zpTransToken, String appTransID) {
//                                Toast.makeText(MainActivityZalopay.this, "Thanh toán bị hủy", Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
//                                Toast.makeText(MainActivityZalopay.this, "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        btnvideocall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityZalopay.this,DashboardActivity_Callvideo.class);
                startActivity(intent);
                overridePendingTransition(R.anim.move_to_left,R.anim.move_to_left);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}
