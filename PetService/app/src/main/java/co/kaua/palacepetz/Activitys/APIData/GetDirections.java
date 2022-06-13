package co.kaua.palacepetz.Activitys.APIData;

import android.os.AsyncTask;

import java.util.HashMap;

public class GetDirections extends AsyncTask<HashMap<String,String>, String, HashMap<String,String>> {

    private static final String TAG = "GetDirections";

    @SafeVarargs
    @Override
    protected final HashMap<String,String> doInBackground(HashMap<String, String>... hashMaps) {

        HashMap<String,String> directionsData = new HashMap<>();

        co.kaua.palacepetz.Activitys.APIData.DownloadUrl downloadURL = new co.kaua.palacepetz.Activitys.APIData.DownloadUrl();
        try {
            directionsData.put("walking", downloadURL.readUrl(hashMaps[0].get("walking")));
            directionsData.put("driving", downloadURL.readUrl(hashMaps[0].get("driving")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directionsData;
    }
}
