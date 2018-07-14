package joe.andenjoying.meteoradar;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 *
 * Created by jelder on 11/11/17.
 */
public class HourlyWeatherQuery {

    private JSONObject json;

    public HourlyWeatherQuery(final String url) {

        new HttpHandler() {
            @Override
            public HttpUriRequest getHttpRequestMethod() {
                return new HttpGet(url);
            }

            @Override
            public void onResponse(String result) {
                try {
                    json = new JSONObject(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    public JSONObject getJson() { return json; }

}