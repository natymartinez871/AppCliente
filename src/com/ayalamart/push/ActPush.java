package com.ayalamart.push;

import com.ayalamart.helper.GestionSesionesUsuario;
import com.example.app42Sample.MessageActivity;
import com.shephertz.app42.paas.sdk.android.App42API;
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


	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String apikey = "f63ba0f76db8e93c8b85f4f140c7adc539965eced4adb0dd055ea37df5a34ff7"; 
		App42API.initialize(
				this,
				"f63ba0f76db8e93c8b85f4f140c7adc539965eced4adb0dd055ea37df5a34ff7",
				"cfef88516cd746711b09dc2040995ad813ddba1abeacea658a7659470abdfbfe");
		sesion = new GestionSesionesUsuario(getApplicationContext()); 

/*		HashMap<String, String> usuario = sesion.getDetallesUsuario(); 
		String nombre = usuario.get(GestionSesionesUsuario.nombre); 
		App42API.setLoggedInUser(nombre);

		PushNotificationService pushNotificationService = App42API.buildPushNotificationService(); 
		pushNotificationService.uploadApiKey(apikey, new App42CallBack() {

			@Override
			public void onSuccess(Object response) {
				PushNotification pushNotification = (PushNotification)response; 
				System.out.println(pushNotification);

			}

			@Override
			public void onException(Exception ex) {
				System.out.println("Exception Message"+ex.getMessage());

			}
		});
*/




	}


	@Override
	protected void onStart() {
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
	public void onPause() {
		super.onPause();
		App42GCMService.isActivtyActive = false;
		unregisterReceiver(mBroadcastReceiver);
	}

	public void onResume() {
		super.onResume();
		App42GCMService.isActivtyActive = true;
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
			Intent newIntent = new Intent(ActPush.this,
					MessageActivity.class);
			newIntent.putExtra(App42GCMService.ExtraMessage, message);
			startActivity(newIntent);
		}
	};


}
