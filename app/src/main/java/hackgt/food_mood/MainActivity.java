package hackgt.food_mood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    // Main Food Mood swipe screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected String placesAPIKey() {
        // Reads Google Places API Key from "places_api_key.txt"
        String fileName = "places_api_key.txt";
        String placesApiKey = "";
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                new BufferedReader(fileReader);

            placesApiKey = bufferedReader.readLine();
            bufferedReader.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println("Unable to open file: '" + fileName + "'");
        }
        catch (IOException ex) {
            System.out.println("Error reading file: '" + fileName + "'");
        }
        return placesApiKey;
    }
}
