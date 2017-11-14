package joe.andenjoying.meteoradar;


import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * It might be a good idea to make this an interface
 * so when implemented, it can accept different forms of
 * coordinates/location formats
 *
 * Created by jelder on 11/11/17.
 */
public class WeatherReport {

    JSONObject json;
    String city = "default";
    String temp = "0.0\u00B0";
    String humidity = "0.0%";
    String condition = "";

    String description = "";
    String icon_code = "";
    String wind_speed = "";
    String wind_direction = "";

    public WeatherReport(final String url) {

        System.out.println("Created a WeatherReport Object");

        new HttpHandler() {
            @Override
            public HttpUriRequest getHttpRequestMethod() {
                return new HttpGet(url);
            }

            @Override
            public void onResponse(String result) {
                ParseData(result);
            }

        }.execute();

    }

    private void ParseData(String result) {
        try {
            json = new JSONObject(result);
            city = json.getString("name");
            JSONObject json_main = json.getJSONObject("main");
            temp = json_main.getString("temp")+"\u00B0"; //Add degrees symbol to temp value
            humidity = json_main.getString("humidity")+"%";

            //Conditions (unfortunatly named 'weather')
            JSONArray json_weather_arr = json.getJSONArray("weather");

            JSONObject json_weather = (JSONObject) json_weather_arr.get(0);

            condition = json_weather.getString("main");
            description = json_weather.getString("description");
            icon_code = json_weather.getString("icon");

            //Get Wind speed and direction
            JSONObject json_wind = json.getJSONObject("wind");
            wind_speed = json.getString("speed");
            wind_direction = json.getString("deg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getCity() {
        return city;
    }

    public String getTemp() {
        return temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getCondition() {
        return condition;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon_code() {
        return icon_code;
    }

    public String getWind() {
        return wind_speed;
    }

    /*
    public String getWind_direction() {
        return wind_direction;
    }
    */
}