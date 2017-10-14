package hackgt.food_mood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;

public class MainActivityScreen extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    // Main Food Mood swipe screen
//    public static final String EXTRA_MESSAGE = "com.example.food-mood.MESSAGE";

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

        setContentView(R.layout.activity_main);
    }


    // Connection failed listener method for
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (result.getErrorCode() == ConnectionResult.API_UNAVAILABLE) {
            // The Places API is unavailable
            System.out.println
                ("The LocationServices or Places APIs are unavailable");
        }
    }
}