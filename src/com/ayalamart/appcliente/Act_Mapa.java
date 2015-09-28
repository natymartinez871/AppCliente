package com.ayalamart.appcliente;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class Act_Mapa extends FragmentActivity {

	private GoogleMap mapa; 
	private final LatLng TESSIES = new LatLng(10.467631, -66.829654);
	private ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act__mapa);


		pDialog = new ProgressDialog(this); 
		pDialog.setMessage("Por favor espere...");
		pDialog.show();
		
		mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mapa.addMarker(new MarkerOptions().position(new LatLng(
				10.467631, -66.829654)).title("Tessie's Restaurant"));
		mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(TESSIES, 15));
		
		hidePDialog();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.act__mapa, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.acerca_de_sett) {
			Intent intent_acercade = new Intent(getApplicationContext(), Act_acercade.class);
			startActivity(intent_acercade);
			return true;
		} 
		else if (id == R.id.menu_sett) {

		}
		else if (id == R.id.donde_estamos_sett) {
			Intent intent_map = new Intent(getApplicationContext(), Act_Mapa.class); 
			startActivity(intent_map);

		}
		else if (id == R.id.donde_estamos_sett) {

		}

		return super.onOptionsItemSelected(item);	

	}
	private void hidePDialog(){
		if (pDialog != null) {
			pDialog.dismiss();
			pDialog = null; 
		}
	}
}
