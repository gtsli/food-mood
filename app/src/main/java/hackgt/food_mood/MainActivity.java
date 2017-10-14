package hackgt.food_mood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.json.JSONObject;
import org.json.JSONException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    // Main Food Mood swipe screen

    private GoogleApiClient mGoogleApiClient;
    private String placesKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a GoogleApiClient instance
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                                  this /* OnConnectionFailedListener */)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        mGoogleApiClient.connect();

        // Use Nearby Search API to return a JSON of JSONs with place_ids
        String urlString =
                "https://maps.googleapis.com/maps/api/place/nearbysearch/" + // call Nearby Search
                "json?location=33.776725899999995,-84.39613039999999" + // location (latitude, longitude)
                "&radius=500" + // radius of x meters
                "&type=restaurant&" + // type of "Place" (restaurants)
                "keyword=food&" + // search keyword (sushi, in this hard-code case)
                "key=AIzaSyApPDilSwXbduwINh2gCpGWOtmplH7cOuQ"; // API Key
        try {
            JSONObject restaurants = (new GetJSON().execute(urlString)).get();
            Log.e("Json files", restaurants.toString());
        } catch (Exception ex) {
            Log.e("error", ex.toString());
        }

        setContentView(R.layout.activity_main);
    }


    // Connection failed listener method for
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (result.getErrorCode() == ConnectionResult.API_UNAVAILABLE) {
            // The Places API is unavailable
            Log.e("APIs not loaded",
                "The LocationServices or Places APIs are unavailable");
        }
    }

}
