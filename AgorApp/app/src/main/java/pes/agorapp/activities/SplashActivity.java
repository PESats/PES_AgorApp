package pes.agorapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import java.util.Locale;

import pes.agorapp.R;
import pes.agorapp.globals.PreferencesAgorApp;

/**
 * Created by marc on 24/10/17.
 */

public class SplashActivity extends Activity {

    private static int SECONDS_SPLASH = 2000;
    private PreferencesAgorApp prefs;
    private boolean hasLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        prefs = new PreferencesAgorApp(SplashActivity.this);

        //Calculem meesures pantalla per a ajustar texts
        prefs.setScreenSize(getResources().getDisplayMetrics().density);

        //obtenim si hi ha idioma previ
        hasLanguage = prefs.hasLanguage();

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

            if (hasLanguage) {
                setLanguage(prefs.getLanguage());
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                setLanguage("ca");
                Intent intent = new Intent(SplashActivity.this, LanguageActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private void setLanguage(String languageToLoad) {
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
}
