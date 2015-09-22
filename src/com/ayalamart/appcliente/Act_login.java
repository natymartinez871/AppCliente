package com.ayalamart.appcliente;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ayalamart.helper.GestionSesionesUsuario;

import android.app.ProgressDialog;
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

public class Act_login extends AppCompatActivity {

	private AutoCompleteTextView email; 
	private EditText password; 
	private TextView signUpTextView;
	GestionSesionesUsuario sesion;
	private ProgressDialog pDialog;
	private String urlJsonObj; 
	
    private static String TAG = Act_login.class.getSimpleName();

    
    String url = "http://192.168.0.103:8080/Restaurante/rest/getCliente/"; 

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
		
		pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        
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
						urlJsonObj = url + email_str; 
						sesion.getDetallesUsuario(); 
						sesion.iniciarSesionUsuario("nombre en la bd", email_str);
						showpDialog();
						JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET, 
								urlJsonObj, null, new Response.Listener<JSONObject>() {
						    
							@Override
						    public void onResponse(JSONObject response) {
						        Log.d(TAG, response.toString());

						        try {

						            String nombre = response.getString("nomCliente");
						            String apellido = response.getString("apeCliente");
						            String cedula = response.getString("cedCliente");
						            String correo = response.getString("emailCliente");
						            String status = response.getString("estatus");
						            String idcliente = response.getString("idCliente");
						            String telefono = response.getString("telCliente");
						            String clave = response.getString("passCliente");
						            
						            if (clave.equals(password_str)) {
						            	sesion.crearSesionUSuario(nombre, correo, nombre, apellido, cedula, correo, telefono);
						            	
						            	Intent intent_ppal = new Intent(getApplicationContext(), ActPrincipal.class); 
										intent_ppal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
										intent_ppal.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
										startActivity(intent_ppal);
										finish(); 
						            
						            }
						            else{
						            	Toast.makeText(getApplicationContext(), "Clave inválida!!", Toast.LENGTH_SHORT).show(); 
						            }
						            

						        } catch (JSONException e) {
						            e.printStackTrace();
						            Toast.makeText(getApplicationContext(),
						                    "Error: " + e.getMessage(),
						                    Toast.LENGTH_LONG).show();
						        }
						        hidepDialog();
						    }
						}, new Response.ErrorListener() {

						    @Override
						    public void onErrorResponse(VolleyError error) {
						        VolleyLog.d(TAG, "Error: " + error.getMessage());
						        Toast.makeText(getApplicationContext(),
						                error.getMessage(), Toast.LENGTH_SHORT).show();
						        // hide the progress dialog
						        hidepDialog();
						    }
						});

						// Adding request to request queue
						AppController.getInstance().addToRequestQueue(jsonObjReq);
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
				intent_signup.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				intent_signup.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				startActivity(intent_signup);
				finish(); 
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

	


private void showpDialog() {
    if (!pDialog.isShowing())
        pDialog.show();
}

private void hidepDialog() {
    if (pDialog.isShowing())
        pDialog.dismiss();
}

}
