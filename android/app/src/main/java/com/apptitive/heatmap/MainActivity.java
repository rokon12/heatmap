package com.apptitive.heatmap;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements GoogleMap.OnMapLoadedCallback {

    private GoogleMap mMap;
    private final static List<LatLng> latLngs = new ArrayList<>();

    static {
        latLngs.add(new LatLng(23.795181, 90.401640)); // Apptitive
        latLngs.add(new LatLng(23.791767, 90.401263)); // Therap
        latLngs.add(new LatLng(23.796133, 90.404174)); // Jantrik
        latLngs.add(new LatLng(23.793080, 90.409110)); // NewsCred
        latLngs.add(new LatLng(23.781491, 90.399992)); // BrainStation-23
    }

    private void addHeatMap(GoogleMap googleMap) {
        if (googleMap != null) {
            HeatmapTileProvider heatmapTileProvider = new HeatmapTileProvider.Builder().data(latLngs).build();
            googleMap.addTileOverlay(new TileOverlayOptions().tileProvider(heatmapTileProvider));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.heatmap);
        mMap = mapFragment.getMap();
        mMap.setOnMapLoadedCallback(this);
    }

    @Override
    public void onMapLoaded() {
        addHeatMap(mMap);
        CameraUpdate position = CameraUpdateFactory.newLatLng(new LatLng(23.791767, 90.401263));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(10f);
        mMap.moveCamera(position);
        mMap.animateCamera(zoom);
    }
}
