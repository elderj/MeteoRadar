package joe.andenjoying.meteoradar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by jelder on 6/30/18.
 */

public class HourlyWeather extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String value = intent.getStringExtra("key"); //if it's a string you stored.
        setContentView(R.layout.hourly_weather);
    }

}
