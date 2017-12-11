package pes.agorapp.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import pes.agorapp.JSONObjects.Announcement;
import pes.agorapp.R;
import pes.agorapp.customComponents.DialogServerKO;
import pes.agorapp.globals.PreferencesAgorApp;
import pes.agorapp.network.AgorAppApiManager;
import retrofit2.Call;
import retrofit2.Response;


public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private View view;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private SupportMapFragment mapFragment;
    private double lat;
    private double lng;
    private OnFragmentInteractionListener mListener;
    private PreferencesAgorApp prefs;
    ArrayList<Announcement> anuncis;

    public MapFragment() {
        // Required empty public constructor
    }

    public interface OnFragmentInteractionListener {
        void onAnnouncementSelected(Announcement announcement);
        void createNewAnnouncement();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_map, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }
        //view = inflater.inflate(R.layout.fragment_map, container, false);
        prefs = new PreferencesAgorApp(getActivity());

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            mGoogleApiClient.connect();
        }

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        LatLng coord = new LatLng(41.390205, 2.154007);
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            lat = mLastLocation.getLatitude();
            lng = mLastLocation.getLongitude();
            coord = new LatLng(lat, lng);
        }
        setMarkers();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(coord, 15);
        mMap.animateCamera(cameraUpdate);
    }

    private void setMarkers() {
        //TODO: Fer cirda api i pintar marker per cada un
        Log.d("AQUI:", prefs.getId() + " " + prefs.getActiveToken());
        //Integer.valueOf(prefs.getId())

        AgorAppApiManager
                .getService()
                .getAnnouncements(Integer.valueOf(prefs.getId()), prefs.getActiveToken())
                .enqueue(new retrofit2.Callback<ArrayList<Announcement>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Announcement>> call, Response<ArrayList<Announcement>> response) {
                        //Log.i("response code", String.valueOf(response.code()));
                        // Log.d("this is my arra3y", "arr: " + response.body().toString());
                        anuncis = response.body();
                        for (Announcement anunci : anuncis) {
                            //Log.d("anunci " + anunci.getId(), "Latitude: " + anunci.getLatitude() + " Longitude " + anunci.getLongitude());
                            LatLng coords = new LatLng(anunci.getLatitude(), anunci.getLongitude());
                            mMap.addMarker(new MarkerOptions()
                                    .position(coords)
                                    .title(Integer.toString(anuncis.indexOf(anunci)))
                            );

                            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                                @Override
                                public boolean onMarkerClick(Marker marker) {
                                    //if(marker.getTitle().equals("BARNA")) // if marker source is clicked
                                    //Toast.makeText(getActivity(), marker.getTitle(), Toast.LENGTH_SHORT).show();// display toast
                                    Announcement announcement = anuncis.get(Integer.valueOf(marker.getTitle()));
                                    //Log.d("",announcement.toString());
                                    mListener.onAnnouncementSelected(announcement);
                                    return true;
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Announcement>> call, Throwable t) {
                        System.out.println("Something went wrong!");
                        new DialogServerKO(getActivity()).show();
                    }
                });


    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        mMap = gMap;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_map);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.createNewAnnouncement();
            }
        });
    }
}
