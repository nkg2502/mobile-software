package com.tsj.trackme;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	private GoogleMap googleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Fragment frag = getSupportFragmentManager().findFragmentById(R.id.map);
		SupportMapFragment smf = (SupportMapFragment) frag;
		googleMap = smf.getMap();
    
	    LocationManager locationManager;
	    String svcName= Context.LOCATION_SERVICE;
	    locationManager = (LocationManager)getSystemService(svcName);

	    Criteria criteria = new Criteria();
	    criteria.setAccuracy(Criteria.ACCURACY_FINE);
	    criteria.setPowerRequirement(Criteria.POWER_LOW);
	    criteria.setAltitudeRequired(false);
	    criteria.setBearingRequired(false);
	    criteria.setSpeedRequired(false);
	    criteria.setCostAllowed(true);
	    String provider = locationManager.getBestProvider(criteria, true);

	    Location l = locationManager.getLastKnownLocation(provider);

	    updateWithNewLocation(l);

	    locationManager.requestLocationUpdates(provider, 2000, 10, locationListener);
	}

	private void updateWithNewLocation(Location location) {
		TextView myLocationText;
		myLocationText = (TextView) findViewById(R.id.myLocationText);

		String latLongString = "No location found";
		String addressString = "No address found";

		if (location != null) {

			double lat = location.getLatitude();
			double lng = location.getLongitude();
			latLongString = "Lat:" + lat + "\nLong:" + lng;

			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			Geocoder gc = new Geocoder(this, Locale.getDefault());

			Toast.makeText(this, "Location " + lat+","+lng, Toast.LENGTH_LONG).show();
			
			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 17));
			googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Here"));


			if (!Geocoder.isPresent())
				addressString = "No geocoder available";
			else {
				try {
					List<Address> addresses = gc.getFromLocation(latitude,
							longitude, 1);
					StringBuilder sb = new StringBuilder();
					if (addresses.size() > 0) {
						Address address = addresses.get(0);

						for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
							sb.append(address.getAddressLine(i)).append("\n");

						sb.append(address.getLocality()).append("\n");
						sb.append(address.getPostalCode()).append("\n");
						sb.append(address.getCountryName());
					}
					addressString = sb.toString();
				} catch (IOException e) {
					Log.d("WHEREAMI", "IO Exception", e);
				}
			}
		}

		myLocationText.setText("Your Current Position is:\n" + latLongString
				+ "\n\n" + addressString);
	}

	private final LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			updateWithNewLocation(location);
		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

}
