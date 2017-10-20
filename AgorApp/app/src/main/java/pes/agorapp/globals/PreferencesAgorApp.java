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
    private static String UUID = "uuid";
    private static String USER_NAME = "userName";
    private static String EMAIL = "email";
    private static String IMAGE_URL = "image_url";
    private static String LEVEL = "user_level";
    private static String VERSION_APP = "versionApp";
    private static String PLATFORM_LOGIN = "platform_login";

    public PreferencesAgorApp(Context activity) {
        prefs = activity.getSharedPreferences(NAME_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void setScreenSize(float size){
        prefs.edit().putFloat(SCREEN_SIZE, size).apply();
    }

    public float getScreenSize(){
        return prefs.getFloat(SCREEN_SIZE, 0);
    }

    public String getUuid() {
        return prefs.getString(UUID, "");
    }

    public void setUuid(String uuid){
        prefs.edit().putString(UUID, uuid).apply();
    }

    public String getUserName() {
        return prefs.getString(USER_NAME, "");
    }

    public void setUserName(String userName){
        prefs.edit().putString(USER_NAME, userName).apply();
    }

    public String getEmail() {
        return prefs.getString(EMAIL, "");
    }

    public void setEmail(String email){
        prefs.edit().putString(EMAIL, email).apply();
    }

    public String getImageUrl() {
        return prefs.getString(IMAGE_URL, "");
    }

    public void setImageUrl(String imageUrl) {
        prefs.edit().putString(IMAGE_URL, imageUrl).apply();
    }

    public int getLevel(){
        return prefs.getInt(LEVEL, 1);
    }

    public void setLevel(int user_level){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(LEVEL, user_level);
        editor.apply();
    }

    public String getAppVersion(){
        return prefs.getString(VERSION_APP,"");
    }

    public void setAppVersion(String current_version){
        prefs.edit().putString(VERSION_APP, current_version).apply();
    }

    public String getPlatform(){
        return prefs.getString(PLATFORM_LOGIN,"");
    }

    public void setPlatform(String platform){
        prefs.edit().putString(PLATFORM_LOGIN, platform).apply();
    }

    public void deleteSession(){
        prefs.edit().remove(USER_NAME).apply();
        prefs.edit().remove(UUID).apply();
        prefs.edit().remove(PLATFORM_LOGIN).apply();
        prefs.edit().remove(LEVEL).apply();
    }

    public void checkPreferences(){
        Log.i("Uuid: ", getUuid() + "");
        Log.i("Username: ", getUserName() + "");
        Log.i("user_level", getLevel()+"");
        Log.i("version", getAppVersion());
        Log.i("platform", getPlatform());
    }
}
