package pes.agorapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import pes.agorapp.R;
import pes.agorapp.globals.Constants;
import pes.agorapp.globals.PreferencesAgorApp;

import java.util.Locale;

public class LanguageActivity extends Activity {

    private String lang;
    private Button cat_Button;
    private Button eng_Button;
    private Button esp_Button;
    private Button enter_Button;

    private Drawable img_greenTick, img_flag_cat, img_flag_esp, img_flag_eng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_language);

        initComponents();

        setListeners();

        //Get device language
        if (Locale.getDefault().toString().equals("es_ES")) {
            spanish_checked();
        } else if (Locale.getDefault().toString().equals("en_EN")) {
            english_checked();
        } else {
            catalan_checked();
        }
    }

    private void initComponents() {
        img_greenTick = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_check_24dp, null);
        img_greenTick.setTint(getResources().getColor(R.color.colorButtons));
        img_flag_cat = ResourcesCompat.getDrawable(getResources(), R.drawable.catalunya_flag, null);;
        img_flag_esp = ResourcesCompat.getDrawable(getResources(), R.drawable.espana_flag, null);;
        img_flag_eng = ResourcesCompat.getDrawable(getResources(), R.drawable.england_flag, null);;

        cat_Button = (Button) findViewById(R.id.btn_catala_lang);
        eng_Button = (Button) findViewById(R.id.btn_english_lang);
        esp_Button = (Button) findViewById(R.id.btn_espanol_lang);

        enter_Button = (Button) findViewById(R.id.btn_enter_lang);
    }

    private void setListeners() {
        cat_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catalan_checked();
            }
        });

        eng_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                english_checked();
            }
        });

        esp_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spanish_checked();
            }
        });

        enter_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLanguage(lang);

                Intent intent = new Intent(LanguageActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Show tick in Spanish button, and change language
     */
    private void spanish_checked(){
        cat_Button.setCompoundDrawablesWithIntrinsicBounds(img_flag_cat, null, null, null);
        eng_Button.setCompoundDrawablesWithIntrinsicBounds(img_flag_eng, null, null, null);
        esp_Button.setCompoundDrawablesWithIntrinsicBounds(img_flag_esp, null, img_greenTick, null);
        lang = Constants.LANG_ES;
        setLanguage();
    }

    /**
     * Show tick in Catalan button, and change language
     */
    private void catalan_checked(){
        cat_Button.setCompoundDrawablesWithIntrinsicBounds(img_flag_cat, null, img_greenTick, null);
        eng_Button.setCompoundDrawablesWithIntrinsicBounds(img_flag_eng, null, null, null);
        esp_Button.setCompoundDrawablesWithIntrinsicBounds(img_flag_esp, null, null, null);
        lang = Constants.LANG_CA;
        setLanguage();
    }

    /**
     * Show tick in English button, and change language
     */
    private void english_checked(){
        cat_Button.setCompoundDrawablesWithIntrinsicBounds(img_flag_cat, null, null, null);
        eng_Button.setCompoundDrawablesWithIntrinsicBounds(img_flag_eng, null, img_greenTick, null);
        esp_Button.setCompoundDrawablesWithIntrinsicBounds(img_flag_esp, null, null, null);
        lang = Constants.LANG_EN;
        setLanguage();
    }

    private void setLanguage() {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        onConfigurationChanged(config);
    }

    /**
     * Update screen everytime that language has change
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        cat_Button.setText(R.string.lang_cat);
        eng_Button.setText(R.string.lang_eng);
        esp_Button.setText(R.string.lang_esp);
        enter_Button.setText(R.string.start);

        super.onConfigurationChanged(newConfig);
    }

    /**
     * Save language into App Preferences
     * @param langToSave    Language to save
     */
    private void saveLanguage(String langToSave){
        PreferencesAgorApp prefs = new PreferencesAgorApp(LanguageActivity.this);
        prefs.setLanguage(langToSave);
    }

}