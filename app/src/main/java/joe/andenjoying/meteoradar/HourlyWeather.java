package joe.andenjoying.meteoradar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jelder on 6/30/18.
 */

public class HourlyWeather extends Activity {

    private String searchKey;
    private String baseUrl;

    private TableLayout hourtable;
    private ImageButton hourlySearchButton;
    private EditText hourlyEditText;

    //Used to get the first 6 (since that is how many we have in the layout
    private final int hourSlices = 6;
    List<WeatherReport> futureReports = new ArrayList<WeatherReport>();

    private String cityName;
    private String country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Toast.makeText(getBaseContext(), "Hourly View", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        baseUrl = intent.getStringExtra("url");
        searchKey = intent.getStringExtra("search");
        setContentView(R.layout.hourly_weather);

        hourlySearchButton = (ImageButton) findViewById(R.id.hourlySearchButton);
        hourlyEditText = (EditText) findViewById(R.id.hourlyEditText);

        HourlyHTTPRequest(baseUrl+searchKey);

        findViewById(R.id.hourlySearchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchKey = "&zip="+hourlyEditText.getText();
                HourlyHTTPRequest(baseUrl+searchKey);
            }
        });
    }

    public void HourlyHTTPRequest(String query){
        final HourlyWeatherQuery hourlyWeatherQuery = new HourlyWeatherQuery(query);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    cityName = hourlyWeatherQuery.getJson().getJSONObject("city").getString("name");
                    country = hourlyWeatherQuery.getJson().getJSONObject("city").getString("country");

                    JSONArray json_weather_arr = hourlyWeatherQuery.getJson().getJSONArray("list");
                    futureReports.clear();
                    for(int i=0; i<hourSlices;i++){
                        futureReports.add(new WeatherReport((JSONObject) json_weather_arr.get(i)));
                    }

                    UpdateWidgetValues();
                    hourtable = (TableLayout) findViewById(R.id.hourTable);
                    hourtable.setVisibility(View.VISIBLE);
                    hourlyEditText.setVisibility(View.VISIBLE);
                    hourlySearchButton.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, 3000);

    }

    public void UpdateWidgetValues(){

        //Widgets which only appear once
        TextView cityText = (TextView) findViewById(getResources().getIdentifier("cityTextView","id",getPackageName()));
        cityText.setText(cityName);

        TextView TimeText;
        TextView ConditionText;
        ImageView ConditionIcon;
        TextView TemperatureText;
        TextView HumidityText;

        for(int i=1;i<=futureReports.size();i++){

            TimeText = (TextView) findViewById(getResources().getIdentifier("TimeText"+i,"id",getPackageName()));
            ConditionText = (TextView) findViewById(getResources().getIdentifier("ConditionText"+i,"id",getPackageName()));
            TemperatureText = (TextView) findViewById(getResources().getIdentifier("TemperatureText"+i,"id",getPackageName()));
            ConditionIcon = (ImageView) findViewById(getResources().getIdentifier("ConditionIcon"+i,"id",getPackageName()));
            HumidityText = (TextView) findViewById(getResources().getIdentifier("HumidityText"+i,"id",getPackageName()));

            System.out.println(futureReports);
            TimeText.setText(futureReports.get(i-1).getDatetime());
            ConditionText.setText(futureReports.get(i-1).getCondition());
            ConditionIcon.setImageResource( getResources().getIdentifier("wthr"+futureReports.get(i-1).getIconCode(), "drawable", getPackageName()));
            System.out.println("WEATHER ICON CODE::::::  "+"wthr"+futureReports.get(i-1).getIconCode());
            TemperatureText.setText(futureReports.get(i-1).getTemp());
            HumidityText.setText(futureReports.get(i-1).getHumidity());
        }
    }
}
