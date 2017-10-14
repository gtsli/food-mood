package hackgt.food_mood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

//import static hackgt.food_mood.MainActivityScreen.EXTRA_MESSAGE;

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
                randomSendMessage(findViewById(R.id.scrollView));
                break;
//          case R.id.pick_item:
//                pickSendMessage(view);
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Called when the user taps the Random button */
    public void randomSendMessage(View view) {
        Intent randomIntent = new Intent(FavoritesActivity.this, DisplayMessageActivity.class);
        TextView textView1 = (TextView) findViewById(R.id.Name);
        String message1 = textView1.getText().toString();
        randomIntent.putExtra("Card 1", message1);

        TextView textView2 = (TextView) findViewById(R.id._Details);
        String message2 = textView2.getText().toString();
        randomIntent.putExtra("Card 2", message2);

        TextView textView3 = (TextView) findViewById(R.id.Details21);
        String message3 = textView3.getText().toString();
        randomIntent.putExtra("Card 3", message3);
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
