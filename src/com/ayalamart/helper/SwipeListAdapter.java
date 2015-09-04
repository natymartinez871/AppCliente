package com.ayalamart.helper;

import java.util.List;

import com.ayalamart.appcliente.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SwipeListAdapter extends BaseAdapter {
	private Activity activity; 
	private LayoutInflater inflater; 
	private List<Platos_menu> listaplatos; 
	private String[] bgColors; 

	public SwipeListAdapter(Activity activity, List<Platos_menu> listaplatos) {
		this.activity = activity; 
		this.listaplatos = listaplatos; 
		bgColors = activity.getApplicationContext().getResources().getStringArray(R.array.color_palette); 
	}

	@Override
	public int getCount() {
		return listaplatos.size(); 
	}

	@Override
	public Object getItem(int location) {
		return listaplatos.get(location); 
	}

	@Override
	public long getItemId(int position) {
		return position; 
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (inflater == null) {
			inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		}
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fila_menu_lv, null); 
		}
		TextView serial_plato = (TextView)convertView.findViewById(R.id.tv_serial_plato); 
		TextView title_plato = (TextView)convertView.findViewById(R.id.tv_nom_plato);
		serial_plato.setText(String.valueOf(listaplatos.get(position).id));
		title_plato.setText(listaplatos.get(position).plato_del_menu);

		String color = bgColors[position % bgColors.length]; 
		serial_plato.setBackgroundColor(Color.parseColor(color));

		return convertView; 
	}

}
