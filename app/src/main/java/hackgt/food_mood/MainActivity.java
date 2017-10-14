package hackgt.food_mood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    // Reads Google Places API Key from "places_api_key.txt"
    String fileName = "places_api_key.txt";
    String placesApiKey = "";
    try {
        // FileReader reads text files in the default encoding.
        FileReader file =
            new FileReader(fileName);

        // Always wrap FileReader in BufferedReader.
        BufferedReader bufferedReader =
            new BufferedReader(fileReader);

        placesApiKey = bufferedReader.readLine();
        bufferedReader.close();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
