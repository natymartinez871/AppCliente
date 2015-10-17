package com.ayalamart.appcliente;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ayalamart.adapter.CustomListAdapter;
import com.ayalamart.adapter.CustomListAdapter.BtnClickListener;
import com.ayalamart.helper.AppController;
import com.ayalamart.modelo.Plato;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Act_Menu extends Activity{

	private static final String TAG = Act_Menu.class.getSimpleName(); 

	private static String URL_Platos = "http://10.10.0.99:8080/Restaurante/rest/plato/getPlatosAll"; 
	private static String URL_Platos_N = "http://10.0.2.2:8080/Restaurante/rest/plato/getPlatosAll"; 
	private ProgressDialog pDialog;
	private List<Plato> listaPlato = new ArrayList<Plato>(); 
	private CustomListAdapter adapter; 
	private ListView listView; 
	private int j; 
	private TextView total; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act__menu);

		listView = (ListView)findViewById(R.id.LV_menu); 
		adapter = new CustomListAdapter(this, listaPlato, new BtnClickListener() {

			@Override
			public void onBtnClick(int position) {

				/*	Plato plato = new Plato(); 
				j = plato.getPrecio(); 
				String k = total.getText().toString(); 
				if (k != "00") {
					int l = Integer.parseInt(k); 
					j = j + l; 
					total.setText(j);
				}
				else{
					total.setText(k);
				}*/
			}
		}); 


		listView.setAdapter(adapter);
		pDialog = new ProgressDialog(this); 
		pDialog.setMessage("Cargando...");
		pDialog.show(); 


		JsonArrayRequest platoReq = new JsonArrayRequest(URL_Platos_N, new Response.Listener<JSONArray>() {

			public void onResponse(JSONArray response){
				Log.d(TAG, response.toString()); 
				hidePDialog();

				for (int i = 0; i < response.length(); i++) {
					try {
						final JSONObject obj = response.getJSONObject(i); 
						Plato plato = new Plato(); 
						plato.setTitulo(obj.getString("nomplato"));
						plato.setThumbnail(obj.getString("imgplato"));
						plato.setDescripcion(obj.getString("descplato"));
						plato.setPrecio(obj.getDouble("precplato"));
						listaPlato.add(plato);		
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				adapter.notifyDataSetChanged();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error){
				VolleyLog.d(TAG, "Error:" + error.getMessage());
				hidePDialog(); 
			}
		});
		AppController.getInstance().addToRequestQueue(platoReq);


		Button but_pagar = (Button)findViewById(R.id.but_finalizPedido); 
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
		/*
		listView = (ListView)findViewById(R.id.LV_menu); 
		adapter = new CustomListAdapter(this, listaPlato, new BtnClickListener() {

			@Override
			public void onBtnClick(int position) {

				Plato plato = new Plato(); 
				j = plato.getPrecio(); 
				String k = total.getText().toString(); 
				if (k != "00") {
					int l = Integer.parseInt(k); 
					j = j + l; 
					total.setText(j);
				}
				else{
					total.setText(k);
				}
			}
		});*/



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
