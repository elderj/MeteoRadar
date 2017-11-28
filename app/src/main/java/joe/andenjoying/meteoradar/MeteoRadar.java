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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/*
 * MeteoRadar App.
 * A basic weather application (WIP!!)
 *
 *  * Created by jelder on 11/11/17.
 *
 */

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
    private TextView windText;
    private ImageView iconImage;

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
        humidityText = (TextView) findViewById(R.id.humidityTextView);
        windText = (TextView) findViewById(R.id.windTextView);
        iconImage = (ImageView) findViewById(R.id.conditionIcon);

        //Access fine location GPS Coords, if possible
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (provider != null && !provider.equals("")) {
            location = locationManager.getLastKnownLocation(provider);
            locationManager.requestLocationUpdates(provider, 120000, 10000, this);
            if (location != null) {
                onLocationChanged(location);
            } else {
                Toast.makeText(getBaseContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getBaseContext(), "No Provider Found", Toast.LENGTH_SHORT).show();
        }

        final String url = "http://api.openweathermap.org/data/2.5/weather?appid=" + appid + "&units=" + units + "&lat=" + lat + "&lon=" + lon;
        final WeatherReport wthr = new WeatherReport(url);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                UpdateFields(wthr);
            }
        }, 3000);

        findViewById(R.id.updatebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateFields(wthr);

            }
        });
    }

    private void UpdateFields(WeatherReport w) {
        cityText.setText(w.getCity());
        tempText.setText(w.getTemp());
        conditionText.setText(w.getCondition());
        descriptionText.setText(w.getDescription());
        humidityText.setText(w.getHumidity());
        windText.setText(w.getWind());
        ChangeWeatherImage(w.getIcon_code());
    }


    private void ChangeWeatherImage(String iconcode){
        String iconfilename="wthr"+iconcode;
        int resId = getResources().getIdentifier(iconfilename, "drawable", getPackageName());
        iconImage.setImageResource(resId);

    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getBaseContext(), "Location changed!! LAT:"+lat+" LON:"+lon, Toast.LENGTH_SHORT).show();
        lat = location.getLatitude();
        lon = location.getLongitude();
        String currentURL = "http://api.openweathermap.org/data/2.5/weather?appid=" + appid + "&units=" + units + "&lat=" + lat + "&lon=" + lon;
        final WeatherReport currWeather = new WeatherReport(currentURL);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                UpdateFields(currWeather);
            }
        }, 3000);
        //Need to add this here to automatically update display upon loc change
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