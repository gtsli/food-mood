package hackgt.food_mood;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.places.PlaceBuffer;

public class NewCard extends AsyncTask<PendingResult<PlaceBuffer>, Void, PlaceBuffer> {

    private Exception exception;

    protected PlaceBuffer doInBackground(PendingResult<PlaceBuffer> ... result) {
        try {
            Log.d("lichard49", "HIIIIIIIIII");
            PlaceBuffer pb = result[0].await();
            Log.d("lichard49", "BYEEEEEE");
            return pb;
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    protected void onPostExecute(PlaceBuffer pb) {
        // TODO: check this.exception
        //Log.e("Possible JSON GET error", this.exception.getMessage());
    }

}
