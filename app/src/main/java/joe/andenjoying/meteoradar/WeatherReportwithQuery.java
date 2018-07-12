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
public class WeatherReportwithQuery {

    private JSONObject json;

    private String id = "";
    private String city = "default";
    private String temp = "0.0\u00B0";
    private String humidity = "0.0%";
    private String condition = "";
    private String description = "";
    private String icon_code = "";
    private String wind_speed = "";
    private String wind_direction= "";

    public WeatherReportwithQuery(final String url) {

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
            id = json.getString("id");
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

            JSONObject json_wind = json.getJSONObject("wind");
            wind_speed = json_wind.getString("speed");


            //Direction is supplied in angles, have to convert it to compass direction
            double wind_angle = json_wind.getDouble("deg");
            wind_direction = CalculateCompassDirection(wind_angle);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String CalculateCompassDirection(double degrees){

        String orientation="";
        if(((degrees >= 0) && (degrees < 90)) || (degrees == 360)){
            orientation="N";
        }
        else if((degrees >= 90) && (degrees < 180)){
            orientation="E";
        }
        else if((degrees >= 180) && (degrees < 270)){
            orientation="S";
        }
        else if((degrees >= 270) && (degrees < 360)){
            orientation="E";
        }
        else{
            orientation="C Deg not defined "+ degrees;
        }
        return orientation;
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
        return "\""+description+"\"";
    }

    public String getIcon_code() {
        return icon_code;
    }

    public String getId() { return id; }

    public String getWind() {
        return wind_speed+"mph "+wind_direction;

    }


}