package pes.agorapp.network;

import pes.agorapp.globals.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by marc on 15/10/17.
 */

public class AgorAppApiManager {

    // interface containing HTTP methods as given by Retrofit
    private static AgorAppApiService agorappApiService;

    // static adapter to be used in entire app
    static volatile Retrofit retrofit;

    static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    private AgorAppApiManager(){}

    //Singleton for Retrofit
    public static Retrofit getAdapter(){

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);

        if(retrofit == null){
            synchronized (AgorAppApiManager.class){
                if(retrofit == null){
                    retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(httpClient.build())
                            .build();
                }
            }
        }
        return retrofit;
    }

    //Initialize Singleton for QuadriviaApiService
    public static void initQuadriviaApiService(){
        if(agorappApiService == null){
            synchronized (AgorAppApiManager.class){
                if(agorappApiService == null){
                    agorappApiService = getAdapter().create(AgorAppApiService.class);
                }
            }
        }
    }

    public static AgorAppApiService getService(){
        if(agorappApiService == null){
            initQuadriviaApiService();
        }
        return agorappApiService;
    }
}
