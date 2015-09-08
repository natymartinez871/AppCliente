package com.ayalamart.appcliente;

import java.util.HashMap;

import com.ayalamart.helper.GestionSesionesUsuario;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42Log;
import com.shephertz.app42.push.plugin.App42GCMController;
import com.shephertz.app42.push.plugin.App42GCMService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ActPrincipal extends AppCompatActivity {
	private TextView cerrarsesionTextview;
	GestionSesionesUsuario sesion; 
	private View progView;
	String userName = null; 
	private static final String GoogleProjectNo = "913405012262";




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act_principal);

		sesion = new GestionSesionesUsuario(getApplicationContext()); 
		App42API.initialize(
				this,
				"f63ba0f76db8e93c8b85f4f140c7adc539965eced4adb0dd055ea37df5a34ff7",
				"cfef88516cd746711b09dc2040995ad813ddba1abeacea658a7659470abdfbfe");

		HashMap<String, String> usuario = sesion.getDetallesUsuario(); 
		String nombre = usuario.get(GestionSesionesUsuario.nombre); 
		App42Log.setDebug(true);
		App42API.setLoggedInUser(nombre);

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

		Button button_pruebapush = (Button)findViewById(R.id.button1);
		button_pruebapush.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent_pruebapush = new Intent(getApplicationContext(), ActPush.class); 
				startActivity(intent_pruebapush);

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

		cerrarsesionTextview = (TextView)findViewById(R.id.tv_cerrarsesion); 
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if(sesion.estaLogeadoelUsuario()){
			finish(); 
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		if (App42GCMController.isPlayServiceAvailable(this)) {
			App42GCMController.getRegistrationId(ActPrincipal.this,
					GoogleProjectNo);

		} else {
			Log.i("App42PushNotification",
					"No valid Google Play Services APK found.");
		}
	}
	public void onStop() {
		super.onStop();
		App42GCMService.isActivtyActive = false;
	}
	public void onDestroy() {

		super.onDestroy();
		App42GCMService.isActivtyActive = false;
	}
	public void onReStart() {
		super.onRestart();

	}
	public void onResume() {
		super.onResume();
		String message = getIntent().getStringExtra(
				App42GCMService.ExtraMessage);
		if (message != null)
			Log.d("MainActivity-onResume", "Message Recieved :" + message);
		IntentFilter filter = new IntentFilter(
				App42GCMService.DisplayMessageAction);
		filter.setPriority(2);
		registerReceiver(mBroadcastReceiver, filter);

	}
	final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String message = intent
					.getStringExtra(App42GCMService.ExtraMessage);
			Log.i("MainActivity-BroadcastReceiver", "Message Recieved " + " : "
					+ message);
		}
	};

	public void onGCMRegistrationId(String gcmRegId) {
		// TODO Auto-generated method stub
		//responseTv.setText("Registration Id on GCM--" + gcmRegId);
		App42GCMController.storeRegistrationId(this, gcmRegId);
		if(!App42GCMController.isApp42Registerd(ActPrincipal.this))
			App42GCMController.registerOnApp42(this, gcmRegId, App42API.getLoggedInUser());
	}

	public void onApp42Response(final String responseMessage) {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				//responseTv.setText(responseMessage);
			}
		});
	}
	public void onRegisterApp42(final String responseMessage) {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				//responseTv.setText(responseMessage);
				App42GCMController.saveRegisterationSuccess(ActPrincipal.this);
			}
		});
	}
}
