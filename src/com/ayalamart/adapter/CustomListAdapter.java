package com.ayalamart.adapter;

import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.ayalamart.appcliente.R;
import com.ayalamart.helper.AppController;
import com.ayalamart.helper.GestionPedidoUsuario;
import com.ayalamart.modelo.Plato;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CustomListAdapter extends BaseAdapter implements SpinnerAdapter{

	private Activity activity;
	private LayoutInflater inflater;
	private List<Plato> itemsPlato; 
	GestionPedidoUsuario sesion_P; 
	
	

	
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	// private BtnClickListener mClickListener = null;
	private final String[] cantidadValores = new String[] { "0", "1", "2", "3", "4",
			"5", "6", "7", "8", "9", "10", "11", "13", "14", "15" };
	

	public CustomListAdapter(Activity activity, List<Plato> itemsPlato) {
		this.activity = activity; 
		this.itemsPlato = itemsPlato; 

	}

	@Override
	public int getCount() {
		return itemsPlato.size(); 
	}
	@Override
	public Object getItem(int location) {
		return itemsPlato.get(location); 
	}	
	@Override
	public long getItemId(int position) {
		return position; 
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null ) {
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		if (convertView == null ) {
			convertView = inflater.inflate(R.layout.fila_menu, null); 
		}
		if (imageLoader == null) {
			imageLoader = AppController.getInstance().getImageLoader(); 
		}
		NetworkImageView thumbNail = (NetworkImageView) convertView
				.findViewById(R.id.thumbnail); 
		TextView tit_plato = (TextView)convertView.findViewById(R.id.tit_plato); 
		TextView descrip_plato = (TextView)convertView.findViewById(R.id.descrip_plato); 
		TextView precio_plato = (TextView)convertView.findViewById(R.id.precio_plato); 
		//Button agregarplato = (Button)convertView.findViewById(R.id.agr_al_carrito_but); 
		Spinner cant_comprar = (Spinner)convertView.findViewById(R.id.cantidad_spinner); 

 

		ArrayAdapter<String> quantityAdapter = new ArrayAdapter<String>(
				activity, android.R.layout.simple_spinner_item,
				cantidadValores);
		quantityAdapter
		.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cant_comprar.setAdapter(quantityAdapter);
		cant_comprar.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				// int getPosition = (Integer)parent.getTag(); 
			try {
				//Spinner sp = (Spinner) parent;
				final HashMap<String, String> pedido = sesion_P.getDetallesPedido(); 
				String pedido_act = pedido.get(GestionPedidoUsuario.Pedido);
				if (!pedido_act.equals(null)) {
					JSONObject pedidoJson_ob = new JSONObject(pedido_act); 
					JSONArray PedidoJson_Arr = pedidoJson_ob.getJSONArray("pedido"); 
					Double subtotal = 0.0; 
					for (int i = 0; i < PedidoJson_Arr.length(); i++) {
						JSONObject pedidosLot = PedidoJson_Arr.getJSONObject(i); 
						String Precio = (String) pedidosLot.get("precplato"); 
						String Cantidad = (String)pedidosLot.get("cantidad"); 
						Double sub = Double.parseDouble(Cantidad)*Double.parseDouble(Precio); 
						subtotal = sub +subtotal; 
					}
					JSONObject pedido_masplato = new JSONObject(); 
					pedido_masplato.put("nomplato", "plato prueba"); 
					Double precioprueba = 9.0; 
					pedido_masplato.put("cantidad", cantidadValores[pos].toString()); 
					Double sub = precioprueba*Double.parseDouble(cantidadValores[pos].toString()); 
					subtotal = sub +subtotal; 
					PedidoJson_Arr.put(pedido_masplato); 
					String pedido_str = PedidoJson_Arr.toString(); 
					
					sesion_P = new GestionPedidoUsuario(activity); 
					sesion_P.crearPedidoUsuario(pedido_str, subtotal.toString());
				}
				else{
					Double subtotal = 0.0; 
					JSONObject pedido_masplato = new JSONObject(); 
					pedido_masplato.put("nomplato", "plato prueba"); 
					Double precioprueba = 9.0; 
					pedido_masplato.put("cantidad", cantidadValores[pos].toString()); 
					Double sub = precioprueba*Double.parseDouble(cantidadValores[pos].toString()); 
					subtotal = sub +subtotal; 
					JSONArray PedidoJson_Arr = new JSONArray(); 
					PedidoJson_Arr.put(pedido_masplato); 
					String pedido_str = PedidoJson_Arr.toString(); 
					
					sesion_P = new GestionPedidoUsuario(activity); 
					sesion_P.crearPedidoUsuario(pedido_str, subtotal.toString());
				}
				
				
			} catch (ArrayIndexOutOfBoundsException  e) {
				  Log.d("Error in spinner",e.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				Log.d("customAdapter", "getPosition: "+ pos); 
				
				
			}
			

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			
				
			}
		});

		/*final Spinner nac_spinner = (Spinner)findViewById(R.id.nacionalidad_spinner_singup); 
		ArrayAdapter<CharSequence> adapter_nac = ArrayAdapter.createFromResource(getApplicationContext(), R.array.nacionalidades, android.R.layout.simple_spinner_item); 
		adapter_nac.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		nac_spinner.setAdapter(adapter_nac);
		 * */


		Plato p = itemsPlato.get(position); 
		thumbNail.setImageUrl(p.getThumbnail(), imageLoader);
		tit_plato.setText(p.getTitulo());
		descrip_plato.setText(p.getDescripcion());
		precio_plato.setText(String.valueOf(p.getPrecio()));
		/*	agregarplato.setTag(position);
		agregarplato.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mClickListener != null)
		            mClickListener.onBtnClick((Integer) v.getTag());     
			}
		});
		 */

		return convertView; 
	}

//
//	public interface BtnClickListener {
//		public abstract void onBtnClick(int position);
//	}

}