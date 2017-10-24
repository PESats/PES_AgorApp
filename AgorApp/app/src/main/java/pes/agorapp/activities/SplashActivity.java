package pes.agorapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import pes.agorapp.R;
import pes.agorapp.globals.PreferencesAgorApp;

/**
 * Created by marc on 24/10/17.
 */

public class SplashActivity extends Activity {

    private static int SECONDS_SPLASH = 2500;
    private PreferencesAgorApp prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        prefs = new PreferencesAgorApp(SplashActivity.this);

        //Calculem meesures pantalla per a ajustar texts
        prefs.setScreenSize(getResources().getDisplayMetrics().density);

        IntentLauncher launcher = new IntentLauncher();
        launcher.start();
    }

    private class IntentLauncher extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(SECONDS_SPLASH);
            }
            catch (Exception e) {
                Log.e("IntentLauncher", e.getMessage());
            }
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
