package co.tranlequyen.palacepetz.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import co.tranlequyen.palacepetz.R;

public class LoadingDialog {

    private final Activity activity;
    private Dialog dialog;

    public LoadingDialog(Activity myactivity){
        activity = myactivity;
    }

    @SuppressLint("InflateParams")
    public void  startLoading(){
        Handler timer = new Handler();
        dialog = new Dialog(activity);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.adapter_custom_loading);
        LottieAnimationView progressBarDialog = dialog.findViewById(R.id.progressBarDialog);

        timer.postDelayed(() -> progressBarDialog.setSpeed((float) 2.5),3000);

        dialog.show();
    }

   public void dimissDialog(){
        dialog.dismiss();
    }
}
