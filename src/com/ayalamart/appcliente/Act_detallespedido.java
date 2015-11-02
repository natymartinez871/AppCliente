package com.ayalamart.appcliente;




import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ayalamart.appcliente.R;
import com.ayalamart.helper.GestionPedidoUsuario;
import com.ayalamart.helper.GestionSesionesUsuario;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Act_detallespedido extends AppCompatActivity {

	GestionSesionesUsuario sesion; 
	TextView nombres; 
	TextView apellidos; 
	TextView detallespedido; 
	TextView totalpedido; 
	TextView numorden; 
	GestionPedidoUsuario sesion_P; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act_detallespedido);

		sesion_P = new GestionPedidoUsuario(getApplicationContext()); 
		sesion = new GestionSesionesUsuario(getApplicationContext()); 

		nombres = (TextView)findViewById(R.id.tv_nroplatos); 
		apellidos= (TextView)findViewById(R.id.textView2); 
		detallespedido = (TextView)findViewById(R.id.textView3); 
		totalpedido = (TextView)findViewById(R.id.textView4); 
		numorden = (TextView)findViewById(R.id.textView6); 

		HashMap<String, String> usuario = sesion.getDetallesUsuario(); 


		String nombre = usuario.get(GestionSesionesUsuario.nombre); 
		String apellido = usuario.get(GestionSesionesUsuario.apellido); 


		nombres.setText(nombre);
		apellidos.setText(apellido);
		final HashMap<String, String> pedido_hash = sesion_P.getDetallesPedido(); 
		String pedido_act = pedido_hash.get(GestionPedidoUsuario.Pedido);
		JSONObject pedidoJson_ob;
		try {
			pedidoJson_ob = new JSONObject(pedido_act);
			JSONArray PedidoJson_Arr = pedidoJson_ob.getJSONArray(null); 
			Double subtotal = 0.0; 
			for (int i = 0; i < PedidoJson_Arr.length(); i++) {
				JSONObject pedidosLot = PedidoJson_Arr.getJSONObject(i); 
				String NomPlato = (String)pedidosLot.get("nomplato"); 
				String Precio = (String) pedidosLot.get("precplato"); 
				String Cantidad = (String)pedidosLot.get("cantidad"); 
				Double sub = Double.parseDouble(Cantidad)*Double.parseDouble(Precio); 
				subtotal = sub +subtotal;
			}
			detallespedido.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas viverra nisl urna, at lobortis dolor egestas id.");
			totalpedido.setText("Bs."+subtotal.toString());
			numorden.setText("123456");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		

		




		
		

		Button cerrar = (Button)findViewById(R.id.button1); 
		cerrar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), Act_Principal.class);  
				startActivity(intent);	
			}
		});;

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {

				Intent intent2 = new Intent(Act_detallespedido.this, Act_Principal.class);  
				startActivity(intent2);
			}
		}, 90000);

	}
}
