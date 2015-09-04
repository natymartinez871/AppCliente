package com.ayalamart.appcliente;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ayalamart.helper.GestionSesionesUsuario;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Act_Signup extends Activity {
	private EditText contrasena; 
	private EditText conf_contrasena; 
	private EditText nombre;
	private EditText cedula;
	private EditText correo;
	private EditText telefono;
	GestionSesionesUsuario sesion; 
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act_signup);
		
		sesion = new GestionSesionesUsuario(getApplicationContext()); 

		nombre = (EditText)findViewById(R.id.nombre_signup); 
		cedula = (EditText)findViewById(R.id.cedula_signup); 
		correo = (EditText)findViewById(R.id.email_signup); 
		telefono = (EditText)findViewById(R.id.telefono_signup); 
		contrasena = (EditText)findViewById(R.id.contrasena_signup); 
		conf_contrasena = (EditText)findViewById(R.id.et_confirm_contrasena);

		//declarando el mensaje de error, y colocandole el mismo color de fondo para esconderlo 
		final TextView mensaje_error = (TextView)findViewById(R.id.alerta_contr_no_match); 
		mensaje_error.setBackgroundColor(Color.parseColor("#96B497"));

		Button button_signup = (Button) findViewById(R.id.but_signup);
		button_signup.setOnClickListener(new OnClickListener() {		

			@Override
			public void onClick(View v) {	

				final String nombre_str = nombre.getText().toString(); 
				final String cedula_str = cedula.getText().toString(); 
				final String correo_str = correo.getText().toString(); 
				final String telefono_str = telefono.getText().toString();
				final String contrasena_str = contrasena.getText().toString(); 
				final String conf_contrasena_str = conf_contrasena.getText().toString();

				if (validarDatos(nombre_str, cedula_str, correo_str, telefono_str)) {
					if (validarPassword(contrasena_str)) {
						//si el password es valido, entra al siguiente ciclo 

						if (!contrasena_str.equals(conf_contrasena_str)) {
							
							String str_nomatch = "La contraseña no coincide";
							mensaje_error.setBackgroundColor(Color.parseColor("#CC5D4C"));
							mensaje_error.setText(str_nomatch);

						}
						else{

							//si las dos contraseñas proporcionadas son iguales, se muestra un Toast

							Toast mensaje_contrasena = Toast.makeText(getApplicationContext(), "Las contraseñas coinciden", Toast.LENGTH_LONG);
							mensaje_contrasena.show();

							String match = " ";
							mensaje_error.setBackgroundColor(Color.parseColor("#96B497"));
							mensaje_error.setText(match);
							
							sesion.crearSesionUSuario(nombre_str, correo_str);
							
							//					aqui deberia colocar la transicion de que logro registrarse 
							Intent intent_ppal = new Intent(getApplicationContext(), ActPrincipal.class); 
							intent_ppal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
							intent_ppal.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
							startActivity(intent_ppal);
							finish(); 
						}

					}

					else{

						// si el password no es valido, entonces mostrara el error de password invalido. 
						contrasena.setError("Contraseña invalida. Debe como mínimo 5 caracteres o ser no nula");

					}
				}

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

	private boolean validarDatos (String nombre_str, String cedula_str, String correo_str, String telefono_str ){

		if (nombre_str != null && cedula_str != null && telefono_str != null && correo_str != null ){
			return true; 
		}
		return false; 
	}

}
