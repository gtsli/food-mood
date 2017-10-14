package hackgt.food_mood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by sarah on 10/14/17.
 */

public class FavoritesActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.random_item:
                Intent randomIntent = new Intent(FavoritesActivity.this, RandomActivity.class);
                startActivity(randomIntent);
                break;
            case R.id.pick_item:
                Intent pickIntent = new Intent(FavoritesActivity.this, PickActivity.class);
                startActivity(pickIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
