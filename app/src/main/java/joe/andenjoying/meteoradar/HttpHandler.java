package joe.andenjoying.meteoradar;

import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONException;

public abstract class HttpHandler {

    public abstract HttpUriRequest getHttpRequestMethod();

    public abstract void onResponse(String result) throws JSONException;

    public void execute() {
        new AsyncHttpTask(this).execute();
    }
}