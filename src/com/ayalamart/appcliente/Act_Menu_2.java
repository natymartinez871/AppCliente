package com.ayalamart.appcliente;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Act_Menu_2 extends Activity {

	protected void OnCreate(Bundle savedInstanceState ){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act__menu);
		
		
		final SwipeRefreshLayout swiperefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout); 
		
		
	//	ListView lv_menu = (ListView)findViewById(R.id.LV_menu); 
		ArrayList<Platos> arraydePlatos = new ArrayList<Act_Menu_2.Platos>(); 
//		final PlatosAdapter adapter = new PlatosAdapter(getApplicationContext(), arraydePlatos);
		
	//	lv_menu.setAdapter(adapter);
//		Platos newPlato = new Platos("hamburguesa", "skjfhaksdjfbkasjdfbkajdsbfkajdsbfkjdbf"); 
//		adapter.add(newPlato);

		
		swiperefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				swiperefreshLayout.setRefreshing(true);
				(new Handler()).postDelayed(new Runnable() {

					@Override
					public void run() {
						swiperefreshLayout.setRefreshing(false);

					}
				}, 3000); 
			}
		});


/*lv_menu.setOnScrollListener(new OnScrollListener() {
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (firstVisibleItem == 0) 
			swiperefreshLayout.setEnabled(true);
		else
			swiperefreshLayout.setEnabled(false);
	}
});*/

	}


	public void refreshplatos(){


		//JSONArray jsonarray = new JSONArray(); 
		//ArrayList<Platos> newPlato_JSON	= Platos.fromJson(jsonarray); 
		//adapter.addAll(newPlato_JSON);
	}




	public class Platos{
		public String nom_plato; 
		public String descrip_plato;
		//		public String precio_plato; 
		//		public String tiempo_prep; 
		//		public String serial_plato; 
		public Platos(String nom_plato, String descrip_plato ){

			//String precio_plato, String serial_plato
			this.nom_plato = nom_plato; 
			this.descrip_plato = descrip_plato; 
			//			this.precio_plato = precio_plato; 
			//			this.serial_plato = serial_plato; 
		}

	}
/*	public class PlatosAdapter extends ArrayAdapter<Platos>{
		public PlatosAdapter(Context context, ArrayList<Platos> plato){
			super(context, 0, plato); 
		}

		public View getView(int position, View convertView, ViewGroup parent){
			Platos plato = getItem(position); 
			if (convertView == null) 
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.fila_menu_2, parent, false); 
				//
				//				TextView tvSerialPlato = (TextView)findViewById(R.id.tv_serial_plato); 
				TextView tvNombrePlato = (TextView)findViewById(R.id.tv_nom_plato);
				TextView tvDescripcionPlato = (TextView)findViewById(R.id.tv_descrip_plato);
				//				TextView tvPrecioPlato = (TextView)findViewById(R.id.tv_precio_plato);

				//				tvSerialPlato.setText(plato.serial_plato); 
				tvNombrePlato.setText(plato.nom_plato);
				tvDescripcionPlato.setText(plato.descrip_plato);
				//				tvPrecioPlato.setText(plato.precio_plato);

				return convertView;
			
			 
		}
	}*/

}


