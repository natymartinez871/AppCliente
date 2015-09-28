package com.ayalamart.appcliente;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ayalamart.helper.AppController;
import com.ayalamart.helper.GestionSesionesUsuario;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Act_Micuenta extends AppCompatActivity {



	private ProgressDialog pDialog;

	private String URlJson; 
	private String urlActualizarCliente = "http://10.10.0.99:8080/Restaurante/rest/updateCliente/";
	String Nac_doc; 
	String OP_doc; 
	GestionSesionesUsuario sesion;
	private AutoCompleteTextView nombreusuarioTV; 
	private AutoCompleteTextView apellidousuarioTV; 
	private AutoCompleteTextView cedulausuarioTV;
	private AutoCompleteTextView correousuarioTV; 
	private AutoCompleteTextView telefonousuarioTV; 
	private static String TAG = Act_Micuenta.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act__micuenta);

		sesion = new GestionSesionesUsuario(getApplicationContext()); 

		if (sesion.estaLogeadoelUsuario()) {
			final HashMap<String, String> usuario = sesion.getDetallesUsuario(); 
			String name = usuario.get(GestionSesionesUsuario.KEY_NAME); 
			String email = usuario.get(GestionSesionesUsuario.KEY_EMAIL); 
			String nombre = usuario.get(GestionSesionesUsuario.nombre); 
			String apellido = usuario.get(GestionSesionesUsuario.apellido); 
			final String correo_str = usuario.get(GestionSesionesUsuario.correo); 
			String telefono = usuario.get(GestionSesionesUsuario.telefono); 
			String cedula = usuario.get(GestionSesionesUsuario.cedula); 
			final String clave_str = usuario.get(GestionSesionesUsuario.contrasena); 

			nombreusuarioTV = (AutoCompleteTextView)findViewById(R.id.mc_nombre); 
			apellidousuarioTV = (AutoCompleteTextView)findViewById(R.id.mc_apellido); 
			cedulausuarioTV = (AutoCompleteTextView)findViewById(R.id.mc_cedula); 
			correousuarioTV = (AutoCompleteTextView)findViewById(R.id.mc_correo); 
			telefonousuarioTV = (AutoCompleteTextView)findViewById(R.id.mc_telefono);


			nombreusuarioTV.setText(nombre);
			apellidousuarioTV.setText(apellido);
			cedulausuarioTV.setText(cedula);
			correousuarioTV.setText(correo_str);
			telefonousuarioTV.setText(telefono);


			final Spinner nac_spinner = (Spinner)findViewById(R.id.nacionalidad_spinner); 
			ArrayAdapter<CharSequence> adapter_nac = ArrayAdapter.createFromResource(getApplicationContext(), R.array.nacionalidades, android.R.layout.simple_spinner_item); 
			adapter_nac.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			nac_spinner.setAdapter(adapter_nac);

			final Spinner operador = (Spinner)findViewById(R.id.operador_spinner); 
			ArrayAdapter<CharSequence> adapter_op = ArrayAdapter.createFromResource(getApplicationContext(), R.array.operadores, android.R.layout.simple_spinner_item); 
			adapter_op.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
			operador.setAdapter(adapter_op);




			pDialog = new ProgressDialog(this);
			pDialog.setMessage("Porfavor espere...");
			pDialog.setCancelable(false);

			Button boton_actualizarcuenta = (Button)findViewById(R.id.but_actualizar_micuenta); 
			boton_actualizarcuenta.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					showpDialog();

					final String name = "nombre_ejm"; 
					final String email = "correo_ejm"; 
					final String nombre_act =  nombreusuarioTV.getText().toString();
					final String apellido_act = apellidousuarioTV.getText().toString();
					final String correo_act = correousuarioTV.getText().toString();
					final String telefono_act = telefonousuarioTV.getText().toString();
					final String cedula_act = cedulausuarioTV.getText().toString();



					String dato_nac = nac_spinner.getSelectedItem().toString(); 
					Log.d(TAG, dato_nac); 
					final String datov = "V-"; 
					if (dato_nac.equals(datov))
					{	Nac_doc = "V-"; }
					else { Nac_doc = "E-"; }
					
					String dato_op = operador.getSelectedItem().toString(); 
					
					if (dato_op.equals("0416")) 
						OP_doc = "0416"; 
						else if (dato_op.equals("0412")) {
						OP_doc = "0412"; 	
						}else if (dato_op.equals("0414")) {
							OP_doc = "0414"; 
						}else if (dato_op.equals("0424")) {
							OP_doc = "0424"; 
						}else if (dato_op.equals("0426")) {
							OP_doc = "0426"; 
						}else if (dato_op.equals("0212")) {
							OP_doc = "0212"; 
						}
					
					 
					String cedula_act_n = Nac_doc + cedula_act;  
					String telef_act_tot = OP_doc + telefono_act; 

					Long idcliente = new Long(0); 
					JSONObject cliente_act = new JSONObject(); 
					try {
						cliente_act.put("apeCliente", apellido_act);
						cliente_act.put("cedCliente",  cedula_act_n); 
						cliente_act.put("emailCliente", correo_act); 
						cliente_act.put("estatus", "1");
						cliente_act.put("idCliente", idcliente);
						cliente_act.put("nomCliente", nombre_act);
						cliente_act.put("passCliente", clave_str);
						cliente_act.put("telCliente", telef_act_tot);
						
						 
						


					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.d(TAG, "ERROR DE JSON"); 
					} 


					URlJson = urlActualizarCliente + correo_str; 
					JsonObjectRequest jsonObjReq1 = new JsonObjectRequest(URlJson, cliente_act, new Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject jsonObject) {

						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							VolleyLog.d(TAG, "Error: " + error.getMessage());

							Log.d(TAG, "Error: " + error.getMessage()); 
							/*Toast.makeText(getApplicationContext(),
									error.getMessage(), Toast.LENGTH_SHORT).show();
							 */
							// hide the progress dialog
							hidepDialog();

						}
					}) ; 


					AppController.getInstance().addToRequestQueue(jsonObjReq1);
					sesion.crearSesionUSuario(name, email, nombre_act, apellido_act, cedula_act, correo_act, telefono_act, clave_str);

					Log.d(TAG, "LOGRO ACTUALIZAR"); 
					hidepDialog();

					Toast errorprueba_1 = Toast.makeText(getApplicationContext(), "Cuenta actualizada con éxito", Toast.LENGTH_SHORT); 
					errorprueba_1.show();

				}
			});


		}



	}




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.act__micuenta, menu);
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
	private void showpDialog() {
		if (!pDialog.isShowing())
			pDialog.show();
	}

	private void hidepDialog() {
		if (pDialog.isShowing())
			pDialog.dismiss();
	}
}
