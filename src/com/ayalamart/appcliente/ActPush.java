package com.ayalamart.appcliente;

import java.util.HashMap;

import com.ayalamart.helper.GestionSesionesUsuario;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42Log;
import com.shephertz.app42.push.plugin.App42GCMController;
import com.shephertz.app42.push.plugin.App42GCMService;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class ActPush extends Activity{
	String userName = null; 
	GestionSesionesUsuario sesion;
	private static final String GoogleProjectNo = "913405012262";

	//private static final String TextContent = "App42 Provides complete cloud API for application development in different SDKs e.g \n PushNotification \n LeaderBoard \n SocialService \n File Storage \n Custom Code";

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_push_prueba);
		
		App42API.initialize(
				this,
				"f63ba0f76db8e93c8b85f4f140c7adc539965eced4adb0dd055ea37df5a34ff7",
				"cfef88516cd746711b09dc2040995ad813ddba1abeacea658a7659470abdfbfe");
		//PushNotificationService pushNotificationService = App42API.buildPushNotificationService();   
		
		sesion = new GestionSesionesUsuario(getApplicationContext()); 

		HashMap<String, String> usuario = sesion.getDetallesUsuario(); 
		String nombre = usuario.get(GestionSesionesUsuario.nombre); 
		 App42Log.setDebug(true);
		App42API.setLoggedInUser(nombre);
		

	}

	@Override
	public void onStart() {
		super.onStart();
		if (App42GCMController.isPlayServiceAvailable(this)) {
			App42GCMController.getRegistrationId(ActPush.this,
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
		
		Intent intent_ppal = new Intent(getApplicationContext(), ActPrincipal.class); 
		startActivity(intent_ppal);
	
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
		if(!App42GCMController.isApp42Registerd(ActPush.this))
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
				App42GCMController.saveRegisterationSuccess(ActPush.this);
			}
		});
	}
	
}
