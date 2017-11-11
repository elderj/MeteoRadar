package joe.andenjoying.meteoradar;


import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * MeteoRadar App.
 *
 * A basic weather application (WIP!!)
 *
 * Created by jelder on 11/11/17.
 */
public class WeatherReport {

    JSONObject json;
    String city="default";
    String temp = "hotaf";

    public WeatherReport(final String url){

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



        //ParseData(json);

    }
    private void ParseData(String result)
    {
        try {
            json = new JSONObject(result);
            city = json.getString("name");
            JSONObject jsonMain=json.getJSONObject("main");
            temp = jsonMain.getString("temp");

            System.out.println(city+" : "+ temp+"!!!!!!!!!!!");
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
}
