package com.ayalamart.helper;

import java.util.HashMap;

import com.ayalamart.appcliente.Act_login_2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class GestionSesionesUsuario {

	SharedPreferences pref_ap; 
	Editor editor_ap; 
	Context context_ap;
	int PRIVATE_MODE = 0; 

	private static final String PREFER_NAME = "AndroidPrueba"; 
	private static final String USUARIO_LOGGEADO = "EstaLogeadoelUsuario"; 
	public static final String KEY_NAME = "name"; 
	public static final String KEY_EMAIL = "email"; 

	public GestionSesionesUsuario(Context context_c){
		this.context_ap = context_c; 
		pref_ap = context_ap.getSharedPreferences(PREFER_NAME, PRIVATE_MODE); 
		editor_ap = pref_ap.edit();	
	}

	public void crearSesionUSuario(String name, String email){
		editor_ap.putBoolean(USUARIO_LOGGEADO, true); 
		editor_ap.putString(PREFER_NAME, name); 
		editor_ap.putString(KEY_EMAIL, email); 
		editor_ap.commit(); 		
	}

	public boolean verificarLogin(){
		if (!this.estaLogeadoelUsuario()) {
			Intent i_login = new Intent(context_ap, Act_login_2.class); 
			i_login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			i_login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			context_ap.startActivity(i_login);
			return true; 
		}
		return false;
	}

	public HashMap<String, String> getDetallesUsuario() {
		HashMap<String, String> usuario = new HashMap<String, String>();
		usuario.put(KEY_NAME, pref_ap.getString(KEY_NAME, null)); 
		usuario.put(KEY_NAME, pref_ap.getString(KEY_EMAIL, null)); 
		return usuario; 
	}
	
	public void cerrarSesionUsuario(){
		editor_ap.clear(); 
		editor_ap.commit(); 
		
		Intent i_login = new Intent(context_ap, Act_login_2.class); 
		i_login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		i_login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		context_ap.startActivity(i_login);
	}

	public boolean estaLogeadoelUsuario(){
		return pref_ap.getBoolean(USUARIO_LOGGEADO, false); 
	}

}
