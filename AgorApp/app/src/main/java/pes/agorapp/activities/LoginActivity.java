package pes.agorapp.activities;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.gson.Gson;

import com.google.gson.JsonObject;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import pes.agorapp.JSONObjects.UserAgorApp;
import pes.agorapp.JSONObjects.UserFacebook;
import pes.agorapp.R;

import pes.agorapp.customComponents.DialogServerKO;
import pes.agorapp.globals.PreferencesAgorApp;
import pes.agorapp.network.AgorAppApiManager;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by marc on 15/10/17.
 */

public class LoginActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private String email;
    private String userName;
    private String url_image;
    private String platform_name;
    private ConnectionResult mConnectionResult;
    private CallbackManager callbackManagerFacebook;
    private static String TAG = "LoginActivity";
    private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
    private static final int RC_SIGN_IN_GOOGLE = 1988;
    private static final int RC_SIGN_IN_TWITTER = TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE;
    private static GoogleApiClient mGoogleApiClient;
    private TwitterLoginButton loginButtonTwitter;
    private LoginButton loginButtonFacebook;
    private PreferencesAgorApp prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = new PreferencesAgorApp(LoginActivity.this);

        //si ja estem loguejats...
        if (!prefs.getUserName().equals("")) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(i);
            finish();
        }
        else {
            Twitter.initialize(this);
            FacebookSdk.sdkInitialize(getApplicationContext());

            setContentView(R.layout.activity_login);

            initGoogleComponents();
            initTwitterComponents();
            initFacebookComponents();
        }
    }


    private void initFacebookComponents() {
        loginButtonFacebook = (LoginButton) findViewById(R.id.sign_in_button_facebook);
        loginButtonFacebook.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        callbackManagerFacebook = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManagerFacebook,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        if (AccessToken.getCurrentAccessToken() != null) {
                            requestEmailFacebook();
                            requestDataUser(null);
                        }
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), "Login cancelat! (facebook) =(", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getApplicationContext(), "FAIL! (facebook) =(", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void requestEmailFacebook() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    email = object.getString("email");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void initTwitterComponents() {
        loginButtonTwitter = (TwitterLoginButton) findViewById(R.id.sign_in_button_twitter);
        loginButtonTwitter.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                if (authToken != null) {
                    requestDataUser(null);
                }
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(getApplicationContext(), "FAIL! (twitter) =(", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initGoogleComponents() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        findViewById(R.id.sign_in_button_google).setOnClickListener(this);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (mConnectionResult == null) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, REQUEST_CODE_RESOLVE_ERR);
                } catch (IntentSender.SendIntentException e) {
                    mGoogleApiClient.connect();
                }
            }
        }
        mConnectionResult = connectionResult;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button_google:
                signInGoogle();
                break;
        }
    }

    private void signInGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN_GOOGLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_SIGN_IN_GOOGLE:
                platform_name = "google";
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                requestDataUser(result);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case RC_SIGN_IN_TWITTER:
                platform_name = "twitter";
                loginButtonTwitter.onActivityResult(requestCode, resultCode, data);
                break;
            default:
                platform_name = "facebook";
                callbackManagerFacebook.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    /**
     * Request REST for get data user Facebook/Twitter/Google
     */
    private void requestDataUser(GoogleSignInResult result_google) {
        switch(platform_name){
            case "facebook":
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                UserFacebook usr_fb = getUserFacebook(response.getJSONObject().toString());
                                Log.i("Username Facebook: ", usr_fb.getName());
                                String url_img_facebook = "https://graph.facebook.com/" + usr_fb.getId() + "/picture?width=100&height=100";

                                createUserDB(usr_fb.getName(), url_img_facebook, "Facebook");

                                //Logout Facebook
                                if(LoginManager.getInstance() != null){
                                    LoginManager.getInstance().logOut();
                                }
                            }
                        }
                ).executeAsync();
                break;
            case "twitter":
                Call<User> userResult = TwitterCore.getInstance()
                        .getApiClient()
                        .getAccountService()
                        .verifyCredentials(true, false, true);
                userResult.enqueue(new Callback<User>() {
                    @Override
                    public void failure(TwitterException e) {
                        Toast.makeText(getApplicationContext(), "FAIL! (twitter) =(", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void success(Result<User> userResult) {
                        User user = userResult.data;

                        email = user.screenName;

                        Log.d("UserName Google:",user.name);
                        Log.d("Image Google: ",user.profileImageUrl);

                        createUserDB(user.name, user.profileImageUrl, "Twitter");
                    }

                });
                break;
            case "google":
                if (result_google.isSuccess()) {

                    email = result_google.getSignInAccount().getEmail();
                    GoogleSignInAccount acct = result_google.getSignInAccount();

                    String url_image_profile;

                    if (acct.getPhotoUrl() != null && !acct.getPhotoUrl().toString().equals("")) {
                        url_image_profile = String.valueOf(acct.getPhotoUrl());
                    } else {
                        url_image_profile = "www.imatgedummy.com";
                    }

                    Log.d("UserName Google:", acct.getDisplayName());
                    Log.d("Image Google: ", url_image_profile);

                    Toast.makeText(getApplicationContext(), url_image_profile, Toast.LENGTH_LONG).show();

                    createUserDB(acct.getDisplayName(), url_image_profile, "Google");
                    signOutGoogle();
                } else {
                    signOutGoogle();
                }
                break;
        }
    }

    /**
     * Get user Facebook from request response
     * @param strResponse   Request response
     * @return UserFacebook Object
     */
    private UserFacebook getUserFacebook(String strResponse){
        return new Gson().fromJson(strResponse, UserFacebook.class);
    }

    /**
     * Send user data to server
     */
    private void createUserDB(final String userName, final String url_image, final String platform_name) {
        this.userName = userName;
        this.url_image = url_image;
        this.platform_name = platform_name;

        //aquí es munta el json 'user' i s'envia mitjançant la petició a l'api de crear usuari
        JsonObject jsonUser = new JsonObject();
        jsonUser.addProperty("name", userName);
        jsonUser.addProperty("email", email);
        jsonUser.addProperty("image_url", url_image);
        jsonUser.addProperty("platform_name", platform_name);

        //JsonObject json = new JsonObject();
        //json.add("user", jsonUser);

        AgorAppApiManager
                .getService()
                .createUser(jsonUser)
                .enqueue(new retrofit2.Callback<UserAgorApp>() {
                    @Override
                    public void onResponse(Call<UserAgorApp> call, Response<UserAgorApp> response) {

                        Log.i("response code", String.valueOf(response.code()));

                        String id = response.body().getId();
                        String token = response.body().getActiveToken();
                        saveUserInPreferences(id, token);

                        Log.i("token", token);

                        loginok();
                    }

                    @Override
                    public void onFailure(Call<UserAgorApp> call, Throwable t) {
                        System.out.println("Something went wrong!");
                        new DialogServerKO(LoginActivity.this).show();
                    }
                });
    }

    private void saveUserInPreferences(String id, String active_token) {
        prefs.setId(id);
        prefs.setPlatform(platform_name);
        prefs.setName(userName);
        prefs.setEmail(email);
        prefs.setImageUrl(url_image);
        prefs.setActiveToken(active_token);
    }

    private void loginok() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(i);
        finish();
    }

    private void signOutGoogle() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {}
                });
    }

    @Override
    public void onResume() {
        super.onResume();

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }
}
