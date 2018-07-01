package joe.andenjoying.meteoradar;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        findViewById(R.id.loginButton).setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent currentActivity = new Intent(getApplicationContext(), CurrentWeather.class);
                startActivity(currentActivity);
            }
        });

    }

}
