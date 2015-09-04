package com.ayalamart.appcliente;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ayalamart.helper.GestionSesionesUsuario;
import com.google.android.gms.fitness.data.Session;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Act_login_2 extends AppCompatActivity {

	private AutoCompleteTextView email; 
	private EditText password; 
	private TextView signUpTextView;
	GestionSesionesUsuario sesion;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act__login);

		sesion = new GestionSesionesUsuario(getApplicationContext()); 

		email = (AutoCompleteTextView)findViewById(R.id.email);
		password = (EditText)findViewById(R.id.password); 

		signUpTextView = (TextView) findViewById(R.id.registrarse);
		signUpTextView.setPaintFlags(signUpTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		Linkify.addLinks(signUpTextView, Linkify.ALL);

		Toast.makeText(getApplicationContext(), "Status de Login de Usuario" + sesion.estaLogeadoelUsuario(), Toast.LENGTH_SHORT).show();

		if (sesion.estaLogeadoelUsuario()) {
			Intent intent_ppal = new Intent(getApplicationContext(), ActPrincipal.class); 
			intent_ppal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			intent_ppal.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			startActivity(intent_ppal);
			finish(); 
		}

		Button button_login = (Button)findViewById(R.id.email_sign_in_button); 		
		button_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String email_str = email.getText().toString();
				final String password_str = password.getText().toString(); 
				View focusView = null;

				if (validarCorreo(email_str)) {
					if (validarPassword(password_str)) {			

						sesion.crearSesionUSuario("nombre de la bd", email_str);

						Intent intent_ppal = new Intent(getApplicationContext(), ActPrincipal.class); 
						intent_ppal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
						intent_ppal.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
						startActivity(intent_ppal);
						finish(); 

						//						Toast inicio_sesion = Toast.makeText(getApplicationContext(), "Inició sesión", Toast.LENGTH_SHORT);
						//						inicio_sesion.show();

					}
					else {
						password.setError(getString(R.string.invalid_password));
						focusView = password; 

					}
				}
				else {
					email.setError(getString(R.string.invalid_email));
				}
			}
		});

		signUpTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent_signup = new Intent(getApplicationContext(), Act_Signup.class); 
				startActivity(intent_signup);
			}
		});
	}

	private boolean validarCorreo (String correo_str){
		String PATRON_CORREO = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"; 

		Pattern patron = Pattern.compile(PATRON_CORREO); 
		Matcher correlacionador = patron.matcher(correo_str); 
		return correlacionador.matches();	
	}
	private boolean validarPassword (String contrasena_str){
		if (contrasena_str != null && contrasena_str.length() > 5 ){
			return true; 
		}
		return false; 
	}

	//	@Override
	//	public void onBackPressed() {
	//		super.onBackPressed();
	//
	//		Toast no_habilitado = Toast.makeText(getApplicationContext(), "Aqui presionó backbutton" , Toast.LENGTH_SHORT);
	//		no_habilitado.show();
	//
	//		this.finish();
	//
	//	}


}
