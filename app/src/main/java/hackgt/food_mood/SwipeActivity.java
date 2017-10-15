package hackgt.food_mood;

/*
Samuel Zhang (google api)
*/

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SwipeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    // Main Food Mood swipe screen
    // USE NEXUS 5 API 26 EMULATOR

    private GoogleApiClient mGoogleApiClient;
    private String placesKey;
//    CollectionPagerAdapter collectionPagerAdapter;
//    ViewPager mViewPager;
    private String[] places_ids;
    private Place[] favorites;
    private int counter;
    private int placeIndex;
//    private GoogleMap map;
    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        // Get user input from previous screen
        Intent intent = getIntent();
        String keyword = intent.getStringExtra(MainActivity.keyword);
        Log.e("keyword: ", keyword);

        // Create a GoogleApiClient instance
        mGoogleApiClient = new GoogleApiClient.Builder(this)
            .enableAutoManage(this /* FragmentActivity */,
                              this /* OnConnectionFailedListener */)
            .addApi(LocationServices.API)
            .addApi(Places.GEO_DATA_API)
            .addApi(Places.PLACE_DETECTION_API)
            .build();

        mGoogleApiClient.connect();

        // Get an array of place_ids near the user at latitude / longitude
        double latitude = 33.776725899999995;
        double longitude = -84.39613039999999;
        int radius = 500; // in meters
        places_ids = getPlacesIds(latitude,longitude, radius,
                                           "restaurant", keyword);
        // Loop through places
        counter = 0;
        placeIndex = 0;
        favorites = new Place[3];

        newCard();

//        // Hook up adapter
//        collectionPagerAdapter =
//                new CollectionPagerAdapter(
//                        getSupportFragmentManager());
//        mViewPager = (ViewPager) findViewById(R.id.pager);
//        mViewPager.setAdapter(collectionPagerAdapter);
//
//        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
//                .getMap();
//
//        if (map!=null){
//            Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
//                    .title("Hamburg"));
//            Marker kiel = map.addMarker(new MarkerOptions()
//                    .position(KIEL)
//                    .title("Kiel")
//                    .snippet("Kiel is cool")
//                    .icon(BitmapDescriptorFactory
//                            .fromResource(R.drawable.ic_launcher)));
//        }
    }

    protected Place newCard() {
        setContentView(R.layout.activity_swipe);
        PendingResult<PlaceBuffer> result = Places.GeoDataApi.getPlaceById(
                mGoogleApiClient, places_ids);
        result.setResultCallback(new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(@NonNull PlaceBuffer placeBuffer) {
                Log.d("lichard49", "WE HAVE THE ANSWER RAWR " + placeBuffer);
                if (places_ids.length > placeIndex && counter < 3) {
                    Place currentPlace = placeBuffer.get(placeIndex);
                    String currentName = currentPlace.getName().toString();
                    int currentPrice = currentPlace.getPriceLevel();
                    float currentRating = currentPlace.getRating();
                    TextView nameField = (TextView) findViewById(R.id.name_tv);
                    TextView priceField = (TextView) findViewById(R.id.price_tv);
                    TextView ratingField = (TextView) findViewById(R.id.rating_tv);
                    nameField.setText("Name: " + currentName);
                    priceField.setText("Price (on a scale from 1 to 4): " + currentPrice);
                    ratingField.setText("Rating (on a scale from 1 to 5: " + currentRating);
                    placeIndex++;
                    place = currentPlace;
//                    return currentPlace;
                } else {
                  // redirect to favorites screen
                    Intent intent = new Intent(SwipeActivity.this, FavoritesActivity.class);
                    intent.putExtra("Favorite 1 name", favorites[0].getName().toString());
                    Log.d(":(", favorites[0].getName().toString());
                    intent.putExtra("Favorite 1 price", favorites[0].getPriceLevel());
                    intent.putExtra("Favorite 1 rating", favorites[0].getRating());
                    intent.putExtra("Favorite 2 name", favorites[1].getName().toString());
                    intent.putExtra("Favorite 2 price", favorites[1].getPriceLevel());
                    intent.putExtra("Favorite 2 rating", favorites[1].getRating());
                    intent.putExtra("Favorite 3 name", favorites[2].getName().toString());
                    intent.putExtra("Favorite 3 price", favorites[2].getPriceLevel());
                    intent.putExtra("Favorite 3 rating", favorites[2].getRating());
                    startActivity(intent);
                }
            }
        }, 1000, TimeUnit.MILLISECONDS);
        return null;
    }

    protected void left(View view) {
        newCard();
    }

    protected void right(View view) {
        newCard();
        favorites[counter] = place;
        Log.d("debugging favorites: ", favorites[counter].getName().toString());
        counter++;
    }

    private String[] getPlacesIds(double latitude, double longitude, int radius,
                                  String typeOfPlace, String keyword) {
        // Use Nearby Search API to return a JSON of JSONs with place_ids
        String urlString =
            "https://maps.googleapis.com/maps/api/place/nearbysearch/" + // call Nearby Search
            "json?location=" + latitude + "," + longitude + // location (latitude, longitude)
            "&radius=" + radius + // radius of x meters
            "&type=" + typeOfPlace + "&" + // type of "Place" (restaurants)
            "keyword=" + keyword + "&" + // search 'keyword' from previous Intent (input)
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

        places_ids = placesIds(places);
        // print out all place_ids
        for (int i = 0; i < places_ids.length; i++) {
            Log.d("places_ids array elem: ", places_ids.toString());
        }
        return places_ids;
    }

//    // Since this is an object collection, use a FragmentStatePagerAdapter,
//    // and NOT a FragmentPagerAdapter.
//    public class CollectionPagerAdapter extends FragmentStatePagerAdapter {
//        public CollectionPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int i) {
//            Fragment fragment = new ObjectFragment();
//            Bundle args = new Bundle();
//            // Our object is just an integer :-P
//            args.putStringArray("placeIDs", places_ids);
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        @Override
//        public int getCount() {
//            return places_ids.length;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return "OBJECT " + (position + 1);
//        }
//    }
//
//    // Instances of this class are fragments representing a single
//    // object in our collection.
//    public static class ObjectFragment extends Fragment {
//        public static final String ARG_OBJECT = "object";
//
//        @Override
//        public View onCreateView(LayoutInflater inflater,
//                                 ViewGroup container, Bundle savedInstanceState) {
//            // The last two arguments ensure LayoutParams are inflated
//            // properly.
//            View rootView = inflater.inflate(
//                    R.layout.fragment_collection_object, container, false);
//            Bundle args = getArguments();
//            ((TextView) rootView.findViewById(android.R.id.textView)).setText(
//                    Integer.toString(args.getInt(ARG_OBJECT)));
//            return rootView;
//        }
//    }


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
