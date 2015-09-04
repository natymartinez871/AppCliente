package com.ayalamart.appcliente;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class Act_acercade extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act_acercade);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.act_acercade, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
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
}
