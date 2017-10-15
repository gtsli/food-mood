package hackgt.food_mood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

import java.io.IOException;

import static hackgt.food_mood.R.id.name1;
import static hackgt.food_mood.R.id.name2;
import static hackgt.food_mood.R.id.name3;
import static hackgt.food_mood.R.id.price1;
import static hackgt.food_mood.R.id.price2;
import static hackgt.food_mood.R.id.price3;
import static hackgt.food_mood.R.id.rating1;
import static hackgt.food_mood.R.id.rating2;
import static hackgt.food_mood.R.id.rating3;

//import static hackgt.food_mood.MainActivityScreen.EXTRA_MESSAGE;

/**
 * Created by sarah on 10/14/17.
 */

public class FavoritesActivity extends AppCompatActivity {

    private Place[] favorites;
    private String[] places_ids;
    private GoogleApiClient mGoogleApiClient;
    private String favorite1name;
    private String favorite2name;
    private String favorite3name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_view_favorites);

        favorite1name = intent.getStringExtra("Favorite 1 name");
        favorite2name = intent.getStringExtra("Favorite 2 name");
        favorite3name = intent.getStringExtra("Favorite 3 name");
        int favorite1price = intent.getIntExtra("Favorite 1 price", 0);
        int favorite2price = intent.getIntExtra("Favorite 2 price", 0);
        int favorite3price = intent.getIntExtra("Favorite 3 price", 0);
        float favorite1rating = intent.getFloatExtra("Favorite 1 rating", (float) 0.0);
        float favorite2rating = intent.getFloatExtra("Favorite 2 rating", (float) 0.0);
        float favorite3rating = intent.getFloatExtra("Favorite 3 rating", (float) 0.0);

        TextView name1field = (TextView) findViewById(R.id.name1);
        Log.d("is it null???", name1field.toString());
        TextView name2field = (TextView) findViewById(R.id.name2);
        TextView name3field = (TextView) findViewById(R.id.name3);
        TextView price1field = (TextView) findViewById(R.id.price1);
        TextView price2field = (TextView) findViewById(R.id.price2);
        TextView price3field = (TextView) findViewById(R.id.price3);
        TextView rating1field = (TextView) findViewById(R.id.rating1);
        TextView rating2field = (TextView) findViewById(R.id.rating2);
        TextView rating3field = (TextView) findViewById(R.id.rating3);

        name1field.setText("Restaurant 1: " + favorite1name);
        name2field.setText("Restaurant 2: " + favorite2name);
        name3field.setText("Restaurant 3: " + favorite3name);
        price1field.setText("Price 1: " + favorite1price);
        price2field.setText("Price 2: " + favorite2price);
        price3field.setText("Price 3: " + favorite3price);
        rating1field.setText("Rating 1: " + favorite1rating);
        rating2field.setText("Rating 2: " + favorite2rating);
        rating3field.setText("Rating 3: " + favorite3rating);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.random_item:
//                randomSendMessage(findViewById(R.id.scrollView));
//                break;
////          case R.id.pick_item:
////                pickSendMessage(view);
////                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    /** Called when the user taps the Random button */
    public void randomClicked(MenuItem item) {
        Intent randomIntent = new Intent(FavoritesActivity.this, DisplayMessageActivity.class);
//        TextView textView1 = (TextView) findViewById(R.id.name1);
//        String message1 = textView1.getText().toString();
        randomIntent.putExtra("Card 1", favorite1name);
//
//        TextView textView2 = (TextView) findViewById(R.id.name2);
//        String message2 = textView2.getText().toString();
        randomIntent.putExtra("Card 2", favorite2name);

//        TextView textView3 = (TextView) findViewById(R.id.name3);
//        String message3 = textView3.getText().toString();
        randomIntent.putExtra("Card 3", favorite3name);
        startActivity(randomIntent);
    }

    /*
    // Called when the user taps the Pick button
    public void pickSendMessage(View view) {
        Intent pickIntent = new Intent(FavoritesActivity.this, DisplayMessageActivity.class);
        try {
            val user = await(githubApi.userAsync());
            await(userRepository.storeAsync(user));
            asyncAwaitTV.text = "asyncAwait: [$user]";
        } catch(IOException e) {
            asyncAwaitTV.text = "asyncAwait: [User retrieval failed.]";
        }
        LinearLayout layout = (LinearLayout) view;
        String message = editText.getText().toString();
        pickIntent.putExtra(EXTRA_MESSAGE, message);
        startActivity(pickIntent);
    }
    */
}
