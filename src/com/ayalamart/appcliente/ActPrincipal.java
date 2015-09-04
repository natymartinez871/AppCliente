package com.ayalamart.appcliente;

import com.ayalamart.helper.GestionSesionesUsuario;

import android.accounts.Account;
import android.accounts.OnAccountsUpdateListener;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActPrincipal extends AppCompatActivity {
	private TextView cerrarsesionTextview;
	GestionSesionesUsuario sesion; 
	private View progView;
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act_principal);

		sesion = new GestionSesionesUsuario(getApplicationContext()); 

		Toast.makeText(getApplicationContext(), "Status de Login de Usuario" + sesion.estaLogeadoelUsuario(), Toast.LENGTH_SHORT).show();

		if (sesion.verificarLogin()) {
			finish(); 
		}

		progView = findViewById(R.id.app_pal_progress_bar); 

		final Button buton_map = (Button) findViewById(R.id.but_mapa);

		buton_map.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent_map = new Intent(getApplicationContext(), Act_Mapa.class); 
				startActivity(intent_map);

			}
		});



		Button button_acercade = (Button) findViewById(R.id.but_acercade);
		button_acercade.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent_acercade = new Intent(getApplicationContext(), Act_acercade.class);
				startActivity(intent_acercade);

			}
		});

		Button button_micuenta = (Button)findViewById(R.id.but_micuenta); 
		button_micuenta.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent_micuenta = new Intent(getApplicationContext(), Act_Micuenta.class);
				startActivity(intent_micuenta);

			}
		});

		cerrarsesionTextview = (TextView)findViewById(R.id.cerrarsesion); 
		cerrarsesionTextview.setPaintFlags(cerrarsesionTextview.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		Linkify.addLinks(cerrarsesionTextview, Linkify.ALL);

		cerrarsesionTextview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				sesion.cerrarSesionUsuario();
			}
		});
		
		Button button_menu = (Button)findViewById(R.id.but_menu); 
		button_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent_menu = new Intent(getApplicationContext(), Act_Menu_2.class);
				startActivity(intent_menu);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.act_principal, menu);
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
		else if (id == R.id.mi_cuenta_sett) {
			Intent intent_micuenta = new Intent(getApplicationContext(), Act_Micuenta.class);
			startActivity(intent_micuenta);
		}

		return super.onOptionsItemSelected(item);	

	}

}
