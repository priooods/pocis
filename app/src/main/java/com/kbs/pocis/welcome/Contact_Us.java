package com.kbs.pocis.welcome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kbs.pocis.R;

public class Contact_Us extends Fragment implements OnMapReadyCallback {

    GoogleMap map;
    MapView mapView;
    View view;
    ImageView btn_back_contact;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contact_us,container,false);

        btn_back_contact = view.findViewById(R.id.btn_back_contact);
        btn_back_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        mapView = view.findViewById(R.id.map);
        if (mapView != null){
            mapView.onCreate(savedInstanceState);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (map != null) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            LatLng latLng = new LatLng(-6.014128251076785, 105.95794399605921);
            map.addMarker(new MarkerOptions().position(latLng).title("PT. Krakatau Bandar Samudera"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }
    }
}
