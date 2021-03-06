package joe.andenjoying.meteoradar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by jelder on 7/10/18.
 */

public class WeatherReport {

    String datetime;

    //Main
    String temp;
    String tempMin;
    String tempMax;
    String humidity;

    //Weather
    String condition;
    String iconCode;

    public WeatherReport (JSONObject json) throws JSONException {


        SimpleDateFormat sdf = new SimpleDateFormat("M/dd ha");
        sdf.setTimeZone(TimeZone.getDefault());
        datetime = sdf.format(new Date(Long.parseLong(json.getString("dt")) * 1000));

        //"Get Values from 'main' object"
        temp = json.getJSONObject("main").getString("temp")+"\u00B0";
        tempMin = json.getJSONObject("main").getString("temp_min");
        tempMax = json.getJSONObject("main").getString("temp_max");
        humidity = json.getJSONObject("main").getString("humidity") + "%";

        //Get Values from 'Weather[0]' object'
        condition = json.getJSONArray("weather").getJSONObject(0).getString("main");
        iconCode = json.getJSONArray("weather").getJSONObject(0).getString("icon");
        PrintValues();
    }

    private void PrintValues(){
        System.out.println("temp: "+temp);
        System.out.println("tempMin: "+tempMin);
        System.out.println("tempMax: "+tempMax);
        System.out.println("humidity: "+humidity);
    }

    public String getDatetime()  { return datetime; }
    public String getTemp()      { return temp; }
    public String getTempMin()   { return tempMin; }
    public String getTempMax()   { return tempMax; }
    public String getHumidity()  { return humidity;}
    public String getCondition() { return condition; }
    public String getIconCode()  { return iconCode; }
}
