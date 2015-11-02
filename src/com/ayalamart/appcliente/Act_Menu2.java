package com.ayalamart.appcliente;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.ayalamart.helper.AppController;
import com.ayalamart.helper.GestionPedidoUsuario;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class Act_Menu2 extends Activity{

	private static final String TAG = Act_Menu2.class.getSimpleName(); 

	private static String URL_Platos_N = "http://10.10.0.99:8080/Restaurante/rest/plato/getPlatosAll"; 
	private static String URL_Platos = "http://10.0.2.2:8080/Restaurante/rest/plato/getPlatosAll"; 
	private ProgressDialog pDialog;
	GestionPedidoUsuario sesion_P; 
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	String cantidades_elegid;

	private int totalplatos;
	//
	//	private CharSequence descplato;
	//
	//	private CharSequence idplato;
	//
	//	private CharSequence nomplato;
	//
	//	private CharSequence precioplato;
	//
	//	private String urlplato;

	//private RelativeLayout layout_menu;

	private RelativeLayout layout_m_2; 
	private LinearLayout linear_menu_i;
	private LinearLayout linear_menu;
	private RelativeLayout layout_menu;
	private Spinner spinner_cant;
	private TextView descrip_plato;
	private TextView id_plato;
	private TextView tit_plato;
	private TextView precio_plato;
	private NetworkImageView imagen;
	private ScrollView scrollview_2;
	private LinearLayout linear_padre;

	private AdapterView<SpinnerAdapter> spinner_cant_i;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_m_2);

		sesion_P = new GestionPedidoUsuario(getApplicationContext()); 

		linear_menu = (LinearLayout)findViewById(R.id.linear_menu); 
		layout_menu = (RelativeLayout)findViewById(R.id.layout_pedido); 
		spinner_cant = (Spinner)findViewById(R.id.cantidad_spinner_2); 
		descrip_plato = (TextView)findViewById(R.id.descrip_plato_2); 
		id_plato = (TextView)findViewById(R.id.idplato_m2); 
		tit_plato = (TextView)findViewById(R.id.tit_plato_2); 
		precio_plato = (TextView)findViewById(R.id.precio_plato_2); 
		imagen = (NetworkImageView)findViewById(R.id.thumbnail); 
		scrollview_2 = (ScrollView)findViewById(R.id.scrollView_m2); 
		linear_padre = (LinearLayout)findViewById(R.id.linear_padre); 

		pDialog = new ProgressDialog(this); 
		pDialog.setMessage("Cargando...");
		pDialog.show(); 
		TextView TV_Subtotal = (TextView)findViewById(R.id.Total_2); 
		TV_Subtotal.setText("00.0");

		JsonArrayRequest platoReq = new JsonArrayRequest(URL_Platos_N, new Response.Listener<JSONArray>() {

			public void onResponse(JSONArray response){
				Log.d(TAG, response.toString()); 
				hidePDialog();
				final int totalplatos = response.length(); 
				for (int i = 0; i < totalplatos; i++) {
					try {
						final JSONObject obj = response.getJSONObject(i); 

						if (imageLoader == null) {
							imageLoader = AppController.getInstance().getImageLoader(); 
						}

						String descplato = obj.getString("descplato"); 
						String idplato = obj.getString("idplato"); 
						String precioplato = Double.toString(obj.getDouble("precplato")); 
						String nomplato = obj.getString("nomplato"); 
						String urlplato = obj.getString("imgplato"); 

						llenarMenu(totalplatos, i, descplato, idplato, precioplato, nomplato, urlplato); 

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error){
				totalplatos = 0; 
				VolleyLog.d(TAG, "Error:" + error.getMessage());
				hidePDialog(); 
			}
		});
		AppController.getInstance().addToRequestQueue(platoReq);

		if (!sesion_P.existePedido()) {
			TV_Subtotal.setText("00.0");

		}else{
			final HashMap<String, String> pedido_hash = sesion_P.getDetallesPedido(); 
			String subtotal = pedido_hash.get(GestionPedidoUsuario.Subtotal);
			TV_Subtotal.setText(subtotal.toString());
		}
		ScrollView scroll_menu = (ScrollView)findViewById(R.id.scrollView_m2); 

		for (int k = 0; k < scroll_menu.getChildCount(); k++) {
			scroll_menu.getChildAt(k); 
			try {
				datosSeleccionados();

			} catch (JSONException e) {
				e.printStackTrace();
			} 

		}


		Button but_pagar = (Button)findViewById(R.id.button_finalizarcompra_2); 
		but_pagar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent int_pagar = new Intent(getApplicationContext(), Act_RealizarPago.class); 
				int_pagar.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				int_pagar.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				startActivity(int_pagar);
				finish(); 

			}
		});
	}

	public void llenarMenu(int length_total, int j, String descplato2, String idplato2, String precioplato2, String nomplato2, String urlplato2){


		TextView descrip_plato_i = new TextView(getApplication()); 
		LayoutParams params_descplato = descrip_plato.getLayoutParams(); 
		descrip_plato_i.setLayoutParams(params_descplato);
		descrip_plato_i.setText(descplato2);
		Log.d(TAG, "descripcion plato 1" + j+ descplato2); 


		TextView id_plato_i = new TextView(getApplication());
		LayoutParams params_idplato = id_plato.getLayoutParams();
		id_plato_i.setLayoutParams(params_idplato);
		id_plato_i.setText(idplato2);
		Log.d(TAG, "idplato2 plato 1" + j + idplato2 ); 



		LayoutParams params_titplato = tit_plato.getLayoutParams(); 
		TextView tit_plato_i = new TextView(getApplication());
		tit_plato_i.setLayoutParams(params_titplato);
		tit_plato_i.setText(nomplato2);
		Log.d(TAG, "tit_plato plato 1" + j + nomplato2 );


		LayoutParams precio_plato_params = precio_plato.getLayoutParams(); 
		TextView precio_plato_i = new TextView(getApplication());
		precio_plato_i.setLayoutParams(precio_plato_params);
		precio_plato_i.setText(precioplato2);
		Log.d(TAG, "precio_plato plato 1" + j + precioplato2 );



		String urlPlato = urlplato2.toString(); 
		NetworkImageView imagen_i = new NetworkImageView(getApplication()); 
		imagen_i.setLayoutParams(imagen.getLayoutParams());
		imagen_i.setImageUrl(urlPlato, imageLoader);
		Log.d(TAG, "imagen plato 1" + j + urlplato2);


		Spinner spinner_cant_i = new Spinner(getApplication()); 
		spinner_cant_i.setLayoutParams(spinner_cant.getLayoutParams());
		ArrayAdapter<CharSequence> adapter_cant = ArrayAdapter.createFromResource(getApplicationContext(), R.array.cantidades, android.R.layout.simple_spinner_item); 
		adapter_cant.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_cant_i.setAdapter(adapter_cant);
		Log.d(TAG, "SPINNER"); 

		final LinearLayout linear_menu_i = new LinearLayout(getApplicationContext()); 
		linear_menu_i.setLayoutParams(linear_menu.getLayoutParams());

		final RelativeLayout layout_m_2 = new RelativeLayout(getApplicationContext()); 
		layout_m_2.setLayoutParams(layout_menu.getLayoutParams());	
		
		final ScrollView scroll_v_i = new ScrollView(getApplicationContext()); 
		scroll_v_i.setLayoutParams(scrollview_2.getLayoutParams());
		
		layout_m_2.addView(descrip_plato_i);
		Log.d(TAG, "LLENÓ DESCRIPCIÓN"); 
		layout_m_2.addView(tit_plato_i);
		layout_m_2.addView(imagen_i);
		layout_m_2.addView(spinner_cant_i);
		layout_m_2.addView(precio_plato_i);
		layout_m_2.addView(id_plato_i);
		linear_menu_i.addView(layout_m_2);
		scrollview_2.addView(linear_menu_i);

		addContentView(scrollview_2, linear_menu.getLayoutParams());


	}

	public final void datosSeleccionados() throws JSONException{

		TextView id_plato = (TextView)findViewById(R.id.idplato_m2); 
		String idplato_str = id_plato.getText().toString(); 
		Log.d(TAG, "SE LLENÓ EL TV"); 

		TextView tit_plato = (TextView)findViewById(R.id.tit_plato_2);
		String tit_plato_str = tit_plato.getText().toString(); 

		TextView precio_plato = (TextView)findViewById(R.id.precio_plato_2);
		String precio_plato_str = precio_plato.getText().toString();

		Spinner spinner_cant = (Spinner)findViewById(R.id.cantidad_spinner_2); 

		String dato_cantidades = spinner_cant_i.getSelectedItem().toString(); 

		Log.d(TAG, dato_cantidades); 

		if (dato_cantidades.equals("1")) {
			cantidades_elegid = "1";}
		else if (dato_cantidades.equals("2")) {
			cantidades_elegid = "2";}
		else if (dato_cantidades.equals("3")) {
			cantidades_elegid = "3";}
		else if (dato_cantidades.equals("4")) {
			cantidades_elegid = "4";}
		else if (dato_cantidades.equals("5")) {
			cantidades_elegid = "5";}
		else if (dato_cantidades.equals("6")) {
			cantidades_elegid = "6";}
		else if (dato_cantidades.equals("7")) {
			cantidades_elegid = "7";}
		else if (dato_cantidades.equals("8")) {
			cantidades_elegid = "8";}
		else if (dato_cantidades.equals("9")) {
			cantidades_elegid = "9";}
		else if (dato_cantidades.equals("10")){
			cantidades_elegid = "10";}
		else if (dato_cantidades.equals("11")){
			cantidades_elegid = "11";
		}else if (dato_cantidades.equals("12")){
			cantidades_elegid = "12";
		}else if (dato_cantidades.equals("13")){
			cantidades_elegid = "13";
		}else if (dato_cantidades.equals("14")){
			cantidades_elegid = "14";
		}else if (dato_cantidades.equals("15")){
			cantidades_elegid = "15";
		}else if (dato_cantidades.equals("16")){
			cantidades_elegid = "16";
		}else if (dato_cantidades.equals("17")){
			cantidades_elegid = "17";
		}else if (dato_cantidades.equals("18")){
			cantidades_elegid = "18";
		}else if (dato_cantidades.equals("19")){
			cantidades_elegid = "19";
		}else if (dato_cantidades.equals("20")){
			cantidades_elegid = "20";
		}

		 
		
		if (sesion_P.existePedido()) {
			final HashMap<String, String> pedido_hash = sesion_P.getDetallesPedido(); 
			String pedido_act = pedido_hash.get(GestionPedidoUsuario.Pedido);
			if (!pedido_act.equals(null)) {
				JSONObject pedidoJson_ob = new JSONObject(pedido_act); 
				JSONArray PedidoJson_Arr = pedidoJson_ob.getJSONArray(null); 
				Double subtotal = 0.0; 
				for (int i = 0; i < PedidoJson_Arr.length(); i++) {
					JSONObject pedidosLot = PedidoJson_Arr.getJSONObject(i); 
					String Precio = (String) pedidosLot.get("precplato"); 
					String Cantidad = (String)pedidosLot.get("cantidad"); 
					Double sub = Double.parseDouble(Cantidad)*Double.parseDouble(Precio); 
					subtotal = sub +subtotal; 
				}

				JSONObject pedido_masplato = new JSONObject(); 
				pedido_masplato.put("nomplato", tit_plato_str); 
				pedido_masplato.put("idplato", idplato_str); 
				pedido_masplato.put("cantidad", cantidades_elegid.toString()); 
				Double precioprueba = Double.parseDouble(precio_plato_str); 
				Double sub = precioprueba*Double.parseDouble(cantidades_elegid.toString()); 
				subtotal = sub +subtotal; 
				PedidoJson_Arr.put(pedido_masplato); 
				String pedido_str = PedidoJson_Arr.toString(); 
				sesion_P.crearPedidoUsuario(pedido_str, subtotal.toString());
				Log.d(TAG, pedido_str);
			}
			else{
				Double subtotal = 0.0; 
				JSONObject pedido_masplato = new JSONObject(); 
				pedido_masplato.put("nomplato", tit_plato_str); 
				pedido_masplato.put("idplato", idplato_str); 
				pedido_masplato.put("cantidad", cantidades_elegid.toString()); 
				Double precioprueba = Double.parseDouble(precio_plato_str); 
				Double sub = precioprueba*Double.parseDouble(cantidades_elegid.toString()); 
				subtotal = sub +subtotal; 
				JSONArray PedidoJson_Arr = new JSONArray();
				PedidoJson_Arr.put(pedido_masplato); 
				String pedido_str = PedidoJson_Arr.toString(); 
				sesion_P.crearPedidoUsuario(pedido_str, subtotal.toString());
				Log.d(TAG, pedido_str); 
			}}



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
