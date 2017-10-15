package pes.agorapp.activities;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.FacebookCallback;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.api.Auth;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.gson.Gson;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import pes.agorapp.JSONObjects.UserFacebook;
import pes.agorapp.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private String email;
    private String loginWith;
    private ConnectionResult mConnectionResult;
    private CallbackManager callbackManagerFacebook;
    private static String TAG = "MainActivity";
    private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
    private static final int RC_SIGN_IN_GOOGLE = 1988;
    private static final int RC_SIGN_IN_TWITTER = TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE;
    private static GoogleApiClient mGoogleApiClient;
    private TwitterLoginButton loginButtonTwitter;
    private LoginButton loginButtonFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Twitter.initialize(this);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initGoogleComponents();
        initTwitterComponents();
        initFacebookComponents();
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
                        //Toast.makeText(getApplicationContext(), "Loguejat amb FACEBOOK\nemail: "+email, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), "Login cancelat! =(", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getApplicationContext(), "FAIL! =(", Toast.LENGTH_LONG).show();
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
                String token = authToken.token;
                String secret = authToken.secret;
                final String[] tmail = new String[1];
                TwitterAuthClient authClient = new TwitterAuthClient();
                authClient.requestEmail(session, new Callback<String>() {
                    @Override
                    public void success(Result<String> result) {
                        tmail[0] = String.valueOf(result);
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        tmail[0] = "fail twitter";
                    }
                });
                Toast.makeText(getApplicationContext(), "Loguejat amb TWITTER\nmail: "+tmail[0], Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(getApplicationContext(), "FAIL! =(", Toast.LENGTH_LONG).show();
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                loginWith = "google";
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                requestDataUser(result);
                break;
            case RC_SIGN_IN_TWITTER:
                loginWith = "twitter";
                loginButtonTwitter.onActivityResult(requestCode, resultCode, data);
                //requestDataUser(null);
                break;
            default:
                loginWith = "facebook";
                callbackManagerFacebook.onActivityResult(requestCode, resultCode, data);
                //requestDataUser(null);
                break;
        }
    }

    /**
     * Request REST for get data user Facebook/Twitter/Google
     */
    private void requestDataUser(GoogleSignInResult result_google) {
        switch(loginWith){
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
            case "uoc":
                break;
            case "google":
                if (result_google.isSuccess()) {

                    email = result_google.getSignInAccount().getEmail();
                    GoogleSignInAccount acct = result_google.getSignInAccount();

                    String url_image_profile;

                    if(acct.getPhotoUrl() != null){
                        url_image_profile = acct.getPhotoUrl().toString();
                    }else{
                        url_image_profile = "www.imatgedummy.com";
                    }

                    Log.d("UserName Google:",acct.getDisplayName());
                    Log.d("Image Google: ",url_image_profile);
                    Log.d("ID Google: ", acct.getId());

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
     * @param userName  Username platform
     * @param url_image URL user image platform
     * @param platform_name Login platform name
     */
    private void createUserDB(final String userName, final String url_image, final String platform_name) {
        //aquí es munta el json 'user' i s'envia mitjançant la petició a l'api de crear usuari
        Toast.makeText(getApplicationContext(), "login OK\nmail: "+email+"\nusername: "+userName+"\nurl_image: "+
                url_image+"\nplatform_name: "+platform_name, Toast.LENGTH_LONG).show();
        //si tot es correcte, entrem a l'app mitjançant loginok()
        loginok();
    }

    private void loginok() {
        //"entrem" a l'app
        Toast.makeText(getApplicationContext(), "Tot correcte", Toast.LENGTH_LONG).show();
    }

    private void signOutGoogle() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {}
                });
    }
}
