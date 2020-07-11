package com.sanim.articular.Activity;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sanim.articular.Model.LatitudeLongitude;
import com.sanim.articular.R;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private AutoCompleteTextView etcity;
    private Button btnSearch;
    private List<LatitudeLongitude> latitudeLongitudeList;
    Marker markerName;
    CameraUpdate center,zoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        etcity=findViewById(R.id.etCity);
        btnSearch=findViewById(R.id.btnSearch);

        fillArrayListAndSetAdapter();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etcity.getText().toString())){
                    etcity.setError("Please enter a place Name");
                    return;
                }

                int position =SearchArrayList(etcity.getText().toString());
                if(position>-1)
                    loadMap(position);
                else
                    Toast.makeText(MapsActivity.this, "Location Not Found by Name : "
                            +etcity.getText().toString(), Toast.LENGTH_SHORT).show();
            }

            private void loadMap(int position) {
                if(markerName!=null){
                    markerName.remove();
                }
                double latitude=latitudeLongitudeList.get(position).getLat();
                double longiture=latitudeLongitudeList.get(position).getLon();
                String marker=latitudeLongitudeList.get(position).getMarker();
                center=CameraUpdateFactory.newLatLng(new LatLng(latitude,longiture));
                zoom=CameraUpdateFactory.zoomTo(17);
                markerName=mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longiture))
                        .title(marker));
                mMap.moveCamera(center);
                mMap.animateCamera(zoom);
                mMap.getUiSettings().setZoomControlsEnabled(true);
            }
        });
    }

    private void fillArrayListAndSetAdapter() {
        latitudeLongitudeList = new ArrayList<>();
        latitudeLongitudeList.add(new LatitudeLongitude(27.7134481,85.3241922,"Articular Office"));
        latitudeLongitudeList.add(new LatitudeLongitude(27.7289889,85.304085,"Articular Shop "));
        latitudeLongitudeList.add(new LatitudeLongitude(27.7149518,85.2904165,"Articular Venue"));

        String[] data = new String[latitudeLongitudeList.size()];

        for (int i = 0; i < data.length; i++){
            data[i] = latitudeLongitudeList.get(i).getMarker();
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<>(
                MapsActivity.this,
                android.R.layout.simple_list_item_1,
                data
        );
        etcity.setAdapter(adapter);
        etcity.setThreshold(1);
    }


    public int SearchArrayList(String name){
        for (int i=0;i<latitudeLongitudeList.size();i++){
            if(latitudeLongitudeList.get(i).getMarker().contains(name)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        center = CameraUpdateFactory.newLatLng(new LatLng(27.7172,85.3240));
        zoom = CameraUpdateFactory.zoomTo(14);
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }

}
