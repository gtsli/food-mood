package hackgt.food_mood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message1 = intent.getStringExtra("Card 1");
        String message2 = intent.getStringExtra("Card 2");
        String message3 = intent.getStringExtra("Card 3");
        String messages[] = new String[] {message1, message2, message3};

        Random random = new Random();
        int selectedInt = (int) (random.nextInt(3) + 1);
        String pickedRestaurant = messages[selectedInt];
        TextView pickedField = (TextView) findViewById(R.id.picked);
        pickedField.setText(pickedRestaurant);
/*
        switch (selectedInt) {
            case 1:
                pickedRestaurant = findRestaurantByName(message1);
                break;
            case 2:
                pickedRestaurant = findRestaurantByName(message2);
                break;
            case 3:
                pickedRestaurant = findRestaurantByName(message3);
                break;
        }

        // Capture the layout's TextView and set the string as its text
        TextView textView = (TextView) findViewById(R.id.textView5);
        textView.setText(pickedRestaurant);
*/
    }
}


