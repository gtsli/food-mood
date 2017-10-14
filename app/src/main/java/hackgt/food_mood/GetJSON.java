package hackgt.food_mood;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetJSON extends AsyncTask<String, Void, JSONObject> {

    private Exception exception;

    // https://stackoverflow.com/questions/34691175/how-to-send-httprequest-and-get-json-response-in-android
    private static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */ );
        urlConnection.setConnectTimeout(15000 /* milliseconds */ );
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            //System.out.println(line + "\n");
            sb.append(line + "\n");
        }
        br.close();

        String jsonString = sb.toString();
        //Log.d("overall wrapper JSON", "JSON: " + jsonString);

        return new JSONObject(jsonString);
    }

    protected JSONObject doInBackground(String... url) {
        try {
            JSONObject query = getJSONObjectFromURL(url[0]);
            //Log.e("test", url[0].toString());
            return query;
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    protected void onPostExecute(JSONObject query) {
        // TODO: check this.exception
        //Log.e("Possible JSON GET error", this.exception.getMessage());
    }
}
