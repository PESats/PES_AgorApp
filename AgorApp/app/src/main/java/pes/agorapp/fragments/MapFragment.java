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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pes.agorapp.JSONObjects.Announcement;
import pes.agorapp.JSONObjects.Botiga;
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
    private ArrayList<Announcement> anuncis;
    private List<Map.Entry<Integer, Marker>> markers;

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

    private void setShopMarkers() {
        AgorAppApiManager
                .getService()
                .getShops(Integer.valueOf(prefs.getId()), prefs.getActiveToken())
                .enqueue(new retrofit2.Callback<ArrayList<Botiga>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Botiga>> call, Response<ArrayList<Botiga>> response) {
                        for (Botiga botiga : response.body()) {
                            LatLng coords = new LatLng(botiga.getLatitude(), botiga.getLongitude());
                            mMap.addMarker(new MarkerOptions()
                                    .position(coords)
                                    .title(botiga.getName())
                                    .snippet(botiga.getDescription())
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.shop_icon))
                            );

                            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                                @Override
                                public boolean onMarkerClick(Marker marker) {
                                    marker.showInfoWindow();
                                    return true;
                                }
                            });
                        }
                        System.out.println("ID " + prefs.getId() + " TOKEN " + prefs.getActiveToken());
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Botiga>> call, Throwable t) {
                        System.out.println("Something went wrong!");
                        new DialogServerKO(getActivity()).show();
                    }
                });
    }

    public void setMarkers() {
        AgorAppApiManager
                .getService()
                .getAnnouncements(Integer.valueOf(prefs.getId()), prefs.getActiveToken())
                .enqueue(new retrofit2.Callback<ArrayList<Announcement>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Announcement>> call, Response<ArrayList<Announcement>> response) {
                        anuncis = response.body();
                        mMap.clear();
                        for (Announcement anunci : anuncis) {
                            if (!anunci.getStatus().equals("completed"))
                                buildMarker(anunci);
                        }
                        setShopMarkers();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Announcement>> call, Throwable t) {
                        System.out.println("Something went wrong!");
                        new DialogServerKO(getActivity()).show();
                    }
                });
    }

    public void buildMarker(Announcement anunci) {
        LatLng coords = new LatLng(anunci.getLatitude(), anunci.getLongitude());
        mMap.addMarker(new MarkerOptions()
                .position(coords)
                .title(anunci.getTitle())
                .snippet(String.valueOf(anunci.getReward()) + " AgoraCoins")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.announcement_marker))
        );

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return true;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                for (Announcement anunci : anuncis) {
                    // Get announcement by position
                    if (marker.getPosition().latitude == anunci.getLatitude() &&
                            marker.getPosition().longitude == anunci.getLongitude()) {
                        mListener.onAnnouncementSelected(anunci);
                    }
                }
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
