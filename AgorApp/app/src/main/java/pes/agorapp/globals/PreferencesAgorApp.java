package pes.agorapp.globals;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by marc on 15/10/17.
 */

public class PreferencesAgorApp {
    private static SharedPreferences prefs;

    private static String NAME_PREFERENCES = "PrefsApplication";
    private static String SCREEN_SIZE = "screen_size";
    private static String ID = "id";
    private static String NAME = "name";
    private static String EMAIL = "email";
    private static String IMAGE_URL = "image_url";
    private static String LEVEL = "user_level";
    private static String VERSION_APP = "versionApp";
    private static String PLATFORM_LOGIN = "platform_login";
    private static String ACTIVE_TOKEN = "active_token";

    public PreferencesAgorApp(Context activity) {
        prefs = activity.getSharedPreferences(NAME_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void setScreenSize(float size) {
        prefs.edit().putFloat(SCREEN_SIZE, size).apply();
    }

    public float getScreenSize() {
        return prefs.getFloat(SCREEN_SIZE, 0);
    }

    public String getId() {
        return prefs.getString(ID, "");
    }

    public void setId(String id) {
        prefs.edit().putString(ID, id).apply();
    }

    public String getUserName() {
        return prefs.getString(NAME, "");
    }

    public void setName(String name) {
        prefs.edit().putString(NAME, name).apply();
    }

    public String getEmail() {
        return prefs.getString(EMAIL, "");
    }

    public void setEmail(String email) {
        prefs.edit().putString(EMAIL, email).apply();
    }

    public String getImageUrl() {
        return prefs.getString(IMAGE_URL, "");
    }

    public void setImageUrl(String imageUrl) {
        prefs.edit().putString(IMAGE_URL, imageUrl).apply();
    }

    public String getAppVersion() {
        return prefs.getString(VERSION_APP,"");
    }

    public void setAppVersion(String current_version) {
        prefs.edit().putString(VERSION_APP, current_version).apply();
    }

    public String getPlatform() {
        return prefs.getString(PLATFORM_LOGIN,"");
    }

    public void setPlatform(String platform) {
        prefs.edit().putString(PLATFORM_LOGIN, platform).apply();
    }

    public String getActiveToken() {
        return prefs.getString(ACTIVE_TOKEN,"");
    }

    public void setActiveToken(String active_token) {
        prefs.edit().putString(ACTIVE_TOKEN, active_token).apply();
    }

    public int getLevel() {
        return prefs.getInt(LEVEL, 1);
    }

    public void setLevel(int user_level) {
        prefs.edit().putInt(LEVEL, user_level).apply();
    }

    public void deleteSession(){
        prefs.edit().remove(NAME).apply();
        prefs.edit().remove(ID).apply();
        prefs.edit().remove(IMAGE_URL).apply();
        prefs.edit().remove(EMAIL).apply();
        prefs.edit().remove(PLATFORM_LOGIN).apply();
        prefs.edit().remove(LEVEL).apply();
        prefs.edit().remove(ACTIVE_TOKEN).apply();
    }

    public void checkPreferences(){
        Log.i("Id: ", getId() + "");
        Log.i("Name: ", getUserName() + "");
        Log.i("Email: ", getEmail() + "");
        Log.i("user_level", getLevel()+"");
        Log.i("version", getAppVersion());
        Log.i("platform", getPlatform());
        Log.i("Token", getActiveToken());
    }
}
