package com.ayalamart.appcliente;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ayalamart.adapter.CustomListAdapter;
import com.ayalamart.adapter.PostAdapter;
import com.ayalamart.adapter.PostData;
import com.ayalamart.helper.AppController;
import com.ayalamart.helper.GestionPedidoUsuario;
import com.ayalamart.helper.GestionSesionesUsuario;
import com.ayalamart.modelo.Plato;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Act_Menu extends ListActivity {

	private static final String TAG = Act_Menu.class.getSimpleName(); 

	private static String URL_Platos_platauto = "http://10.10.0.99:8080/Restaurante/rest/plato/getPlatosAll"; 
	private static String URL_Platos = "http://10.0.2.2:8080/Restaurante/rest/plato/getPlatosAll"; 
	private ProgressDialog pDialog;

	private List<Plato> listaPlato = new ArrayList<Plato>(); 
	private CustomListAdapter adapter_L; 
	private ListView listView; 
	private int j; 
	private TextView total; 
	private JSONArray base ; 
	private PostAdapter adapter;
	private ArrayList<PostData> data;
	String idplato; 
	String nombrePlato; 
	String descripcionPlato; 
	String precioPlato; 
	GestionPedidoUsuario sesion_P;
	GestionSesionesUsuario sesion; 

	private String fecha; 
	private String hora;

	private double iva; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act__menu);
		

		sesion_P = new GestionPedidoUsuario(getApplicationContext()); 
		sesion = new GestionSesionesUsuario(getApplicationContext()); 
		
		sesion_P.borrarPedido();
		
		pDialog = new ProgressDialog(this); 
		pDialog.setMessage("Cargando...");
		pDialog.show(); 
		JSONArray base = new JSONArray(); 

		Calendar rightnow =Calendar.getInstance();
		SimpleDateFormat fechaact = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		SimpleDateFormat horaact = new SimpleDateFormat("HH:mm:ss");
		fecha = fechaact.format(rightnow.getTime());
		hora = horaact.format(rightnow.getTime());

		data = new ArrayList<PostData>();

		llenarLV_M(savedInstanceState);

		final Handler h = new Handler();
		final int delay = 20000; //milliseconds
		h.postDelayed(new Runnable(){
			public void run(){
				try {
					revisarSelecciones();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (!sesion_P.existePedido()) 

				{}
				else{
					final HashMap<String, String> pedido = sesion_P.getDetallesPedido(); 
					String subtotal = pedido.get(GestionPedidoUsuario.Subtotal);

				}
				h.postDelayed(this, delay);
			}
		}, delay);

		Button but_pagar = (Button)findViewById(R.id.but_finalizPedido); 
		but_pagar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {


				try {
					revisarSelecciones();
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.d(TAG, "revisarselecciones" + e); 
				}


				Intent int_pagar = new Intent(getApplicationContext(), Act_RealizarPago.class); 
				int_pagar.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				int_pagar.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				startActivity(int_pagar);
				finish(); 

			}
		});


	}
	public void revisarSelecciones() throws JSONException{
		final HashMap<String, String> usuario = sesion.getDetallesUsuario(); 
		if (adapter.haveSomethingSelected()) {
			JSONObject detalles = new JSONObject(); 
			JSONObject pc = new JSONObject(); 
			JSONObject det_pedido = new JSONObject(); 
			JSONObject pedido = new JSONObject(); 
			JSONObject clienteOBJ = new JSONObject(); 
			JSONObject platos_pedido = new JSONObject(); 
			Double subtotal = 0.0; 
			for (int j = 0; j < data.size(); j++) {
				String datosel = data.get(j).getNombres();
				if (data.get(j).getChecked())
				{
					
						JSONObject plato_menu = base.getJSONObject(j);
						
						// String precioplato_str = plato_menu.getString("precplato").toString(); 
						// platos_pedido.put("precplato", precioplato_str); 
						platos_pedido = (JSONObject) plato_menu.get("plato"); 
						det_pedido.put("Estatus", 0); 
						det_pedido.put("cantidad", 1); 
						Log.d(TAG, "Objeto JSON de platomenu"+ plato_menu.toString()); 
						Log.d(TAG, "objeto JSON de pedido" + pedido.toString()); 
						String precio_i = plato_menu.get("precplato").toString(); 
						subtotal = Double.valueOf(precio_i).doubleValue() + subtotal; 
						det_pedido.put("plato", platos_pedido);
						
						

							String nombre = usuario.get(GestionSesionesUsuario.nombre); 
							String apellido = usuario.get(GestionSesionesUsuario.apellido); 
							String correo_str = usuario.get(GestionSesionesUsuario.correo); 
							//String telefono = usuario.get(GestionSesionesUsuario.telefono); 
							String cedula = usuario.get(GestionSesionesUsuario.cedula); 
							//String clave_str = usuario.get(GestionSesionesUsuario.contrasena); 
							String idcliente = usuario.get(GestionSesionesUsuario.idcliente); 
							//String tipocliente = usuario.get(GestionSesionesUsuario.tipocliente); 

							clienteOBJ.put("nomCliente", nombre); 
							clienteOBJ.put("apeCliente", apellido); 
							clienteOBJ.put("cedCliente", cedula); 
							clienteOBJ.put("idcliente", idcliente); 
							clienteOBJ.put("emailcliente", correo_str); 

							pedido.put("fechapedido", fecha);
							pedido.put("horapedido", hora); 
							pedido.put("subtotal", subtotal); 
							iva = subtotal/0.12; 
							pedido.put("iva", iva); 

							pedido.put("cliente", clienteOBJ); 

							det_pedido.put("pedido", pedido); 
							
							detalles.put("detalles", det_pedido); 
							
							pc.put("pedido", pedido); 
							pc.put("detalles", detalles) ; 
							sesion_P.crearPedidoUsuario(pc.toString(), Double.toString(subtotal));
							
							Log.d("TAG", "importante" +  pc.toString()); 
						
							

					 
				}else
					Log.d(TAG, "Plato no seleccionado" + datosel); 
			}
			try {

				String nombre = usuario.get(GestionSesionesUsuario.nombre); 
				String apellido = usuario.get(GestionSesionesUsuario.apellido); 
				String correo_str = usuario.get(GestionSesionesUsuario.correo); 
				//String telefono = usuario.get(GestionSesionesUsuario.telefono); 
				String cedula = usuario.get(GestionSesionesUsuario.cedula); 
				//String clave_str = usuario.get(GestionSesionesUsuario.contrasena); 
				String idcliente = usuario.get(GestionSesionesUsuario.idcliente); 
				//String tipocliente = usuario.get(GestionSesionesUsuario.tipocliente); 

				clienteOBJ.put("nomCliente", nombre); 
				clienteOBJ.put("apeCliente", apellido); 
				clienteOBJ.put("cedCliente", cedula); 
				clienteOBJ.put("idcliente", idcliente); 
				clienteOBJ.put("emailcliente", correo_str); 

				pedido.put("fechapedido", fecha);
				pedido.put("horapedido", hora); 
				pedido.put("subtotal", subtotal); 
				iva = subtotal/0.12; 
				pedido.put("iva", iva); 

				pedido.put("cliente", clienteOBJ); 

				sesion_P.crearPedidoUsuario(pedido.toString(), Double.toString(subtotal));
				Log.d(TAG , "arregloped: " + pedido.toString()); 
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			//			Double total = subtotal*0.35 + subtotal; 
			//			Log.d(TAG, total.toString()); 
		}
	}
	public void llenarLV_M(final Bundle savedInstanceState){
		JsonArrayRequest platoReq = new JsonArrayRequest(URL_Platos_platauto, new Response.Listener<JSONArray>() {

			public void onResponse(JSONArray response){
				Log.d(TAG, response.toString()); 

				try {
					for (int i = 0; i < response.length(); i++) {

						final JSONObject obj = response.getJSONObject(i); 
						String nomplato = obj.getString("nomplato"); 
						String descplato = obj.getString("descplato"); 
						String urlImg = obj.getString("imgplato"); 
						String precioplato = obj.getString("precplato"); 

						data.add(new PostData(nomplato, descplato, urlImg, precioplato, false));

					}} catch (JSONException e) {
						e.printStackTrace();
					}

				base = response;
				hidePDialog(); 
				llenarmenu(savedInstanceState); 
				hidePDialog();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error){
				VolleyLog.d(TAG, "Error:" + error.getMessage());
				hidePDialog(); 
			}
		});
		adapter = new PostAdapter(Act_Menu.this, data);
		AppController.getInstance().addToRequestQueue(platoReq);
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArrayList("savedData", data);
		super.onSaveInstanceState(outState);
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		adapter.setCheck(position);
	}
	public void llenarmenu(Bundle savedInstanceState){
		if (savedInstanceState == null){
			adapter = new PostAdapter(Act_Menu.this, data);

		} else{
			data = savedInstanceState.getParcelableArrayList("savedData");
			adapter = new PostAdapter(Act_Menu.this, data);
			hidePDialog();
		}
		setListAdapter(adapter);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		hidePDialog();
	}
	private void hidePDialog(){
		if (pDialog != null) {
			pDialog.dismiss();
			pDialog = null; 
		}
	}

}
