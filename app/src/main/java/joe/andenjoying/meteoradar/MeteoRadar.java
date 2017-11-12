package joe.andenjoying.meteoradar;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MeteoRadar extends Activity implements LocationListener {

    Location location;
    String appid = "b112d5780682a781f8b9e98755d188c6";
    String units = "imperial";

    //Default GPS Location is the Great Pyramids
    double lat = 29.975939;
    double lon = 31.130404;

    private TextView cityText;
    private TextView tempText;
    private TextView conditionText;
    private TextView descriptionText;
    private TextView humidityText;

    LocationManager locationManager;
    String provider;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo_radar);

        cityText = (TextView) findViewById(R.id.cityTextView);
        tempText = (TextView) findViewById(R.id.tempTextView);
        conditionText = (TextView) findViewById(R.id.conditionTextView);
        descriptionText = (TextView) findViewById(R.id.descriptionTextView);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (provider != null && !provider.equals("")) {
            location = locationManager.getLastKnownLocation(provider);
            locationManager.requestLocationUpdates(provider, 20000, 1, this);
            if (location != null) {
                onLocationChanged(location);
            } else {
                Toast.makeText(getBaseContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getBaseContext(), "No Provider Found", Toast.LENGTH_SHORT).show();
        }

        //Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
        final String url = "http://api.openweathermap.org/data/2.5/weather?appid=" + appid + "&units=" + units + "&lat=" + lat + "&lon=" + lon;
        final WeatherReport wthr = new WeatherReport(url);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                UpdateFields(wthr);
            }
        }, 1000);

        findViewById(R.id.updatebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityText.setText(wthr.getCity());
                tempText.setText(wthr.getTemp());
                conditionText.setText(wthr.getCondition());
                descriptionText.setText(wthr.getDescription());
            }
        });
    }

    public void UpdateFields(WeatherReport w) {
        cityText.setText(w.getCity());
        tempText.setText(w.getTemp());
        conditionText.setText(w.getCondition());
        descriptionText.setText(w.getDescription());
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }
}