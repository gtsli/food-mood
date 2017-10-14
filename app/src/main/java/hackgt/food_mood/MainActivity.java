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

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    // Main Food Mood swipe screen
    // USE NEXUS 5 API 26 EMULATOR

    private GoogleApiClient mGoogleApiClient;
    private String placesKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        JSONObject restaurants = new JSONObject();
        try { // GET the wrapper JSON object of places in a new thread (can't run on main thread)
            restaurants = (new GetJSON().execute(urlString)).get();
            Log.d("Json files", restaurants.toString()); // look for JSON object
        } catch (Exception ex) {
            Log.e("error", ex.toString());
        }

        JSONArray places = listOfPlaces(restaurants);
        Log.d("places json array: ", places.toString());

        String[] places_ids = placesIds(places);
        // print out all place_ids
        for (int i = 0; i < places_ids.length; i++) {
            Log.d("places_ids array elem: ", places_ids.toString());
        }
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

    private JSONArray listOfPlaces(JSONObject wrapper) {
        // Parse through a JSON object of JSON objects
        //
        // @param JSONObject wrapper is the initial JSON wrapper object with 'results' being
        //       the list of all the places
        // @returns array of places (JSON objects with unique place_id 's)
        //          or empty array if exception
        try {
            return wrapper.getJSONArray("results");
        } catch (JSONException e) {
            Log.e("JSON places arr err: ", e.getMessage());
        }
        return new JSONArray(); // exception, return empty JSONArray
    }

    private String[] placesIds(JSONArray listOfPlaces) {
        // Parse through JSONArray of places (JSONObjects)
        //
        // @param JSONArray listOfPlaces is an array of places
        //
        // @returns JSONArray of place_id's)
        //          or empty array if exception
        String[] result = new String[listOfPlaces.length()];
        for (int i = 0; i < listOfPlaces.length(); i++) {
            // put places["place_id"] into array
            try {
                JSONObject current = listOfPlaces.getJSONObject(i);
                String place_id = current.get("place_id").toString();
                result[i] = place_id;
                //Log.d("each place_id: ", place_id);
            } catch (JSONException e) {
                Log.e("Places arr err at: " + i, e.getMessage());
            }
        }
        return result; // exception, return empty JSON Array
    }
}
