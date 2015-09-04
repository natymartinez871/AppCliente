package com.ayalamart.push;

import java.util.HashMap;

import com.ayalamart.helper.GestionSesionesUsuario;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.push.PushNotification;
import com.shephertz.app42.paas.sdk.android.push.PushNotificationService;

import android.app.Activity;

public class ActPush extends Activity{
	String userName = null; 
	GestionSesionesUsuario sesion;
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String apikey = "b0a1f641830b54f3a763ff63deaebe9814634938db49fa7f7d9c4c0553eabe00"; 
		App42API.initialize(
				this,
				"b0a1f641830b54f3a763ff63deaebe9814634938db49fa7f7d9c4c0553eabe00",
				"b4acc69a879ddc742cd2e939ef8b5be5e2ad4f5ce02638dfa6e4286fa5e78b65");
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
		
		
		sesion = new GestionSesionesUsuario(getApplicationContext()); 
		if (sesion.estaLogeadoelUsuario()) {
			HashMap<String, String> usuario = sesion.getDetallesUsuario(); 
			String name = usuario.get(GestionSesionesUsuario.KEY_NAME); 
			String email = usuario.get(GestionSesionesUsuario.KEY_EMAIL); 
			String nombre = usuario.get(GestionSesionesUsuario.nombre); 
			String correo = usuario.get(GestionSesionesUsuario.correo); 
			String telefono = usuario.get(GestionSesionesUsuario.telefono); 
			String cedula = usuario.get(GestionSesionesUsuario.cedula); 
			
			
			
			
		}
		
	
	}






}
