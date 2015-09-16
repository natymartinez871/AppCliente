package com.ayalamart.adapter;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.ayalamart.appcliente.AppController;
import com.ayalamart.appcliente.R;
import com.ayalamart.modelo.Plato;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter{

	private Activity activity;
	private LayoutInflater inflater;
	private List<Plato> itemsPlato; 
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	private BtnClickListener mClickListener = null;
	
	public CustomListAdapter(Activity activity, List<Plato> itemsPlato, BtnClickListener listener) {
		this.activity = activity; 
		this.itemsPlato = itemsPlato; 
		this.mClickListener = listener;
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
		Button agregarplato = (Button)convertView.findViewById(R.id.agr_al_carrito_but); 

		Plato p = itemsPlato.get(position); 
		thumbNail.setImageUrl(p.getThumbnail(), imageLoader);
		tit_plato.setText(p.getTitulo());
		descrip_plato.setText(p.getDescripcion());
		precio_plato.setText(String.valueOf(p.getPrecio()));
		agregarplato.setTag(position);
		agregarplato.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mClickListener != null)
		            mClickListener.onBtnClick((Integer) v.getTag());     
			}
		});
	
		
		

		return convertView; 
	}
	
	public interface BtnClickListener {
	    public abstract void onBtnClick(int position);
	}

}
