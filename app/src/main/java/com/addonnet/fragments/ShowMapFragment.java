package com.addonnet.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.addonnet.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created by pita on 10/1/2016.
 */
public class ShowMapFragment extends Fragment implements OnMapReadyCallback
{
    private GoogleMap mGoogleMap;
    private View mRootView;

    public ShowMapFragment() {
        //Require empty constructor
    }

    /**
     * function to load googleMap. If googleMap is not created it will create it for you
     */
    private void initializeMap()
    {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mRootView = inflater.inflate(R.layout.fragment_map, container, false);
        initializeMap();
        return mRootView;
    }
}

