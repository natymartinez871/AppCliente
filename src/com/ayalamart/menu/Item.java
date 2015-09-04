package com.ayalamart.menu;

public class Item {
	public String murl_img_plato; 
	public String mnom_plato; 
	public String mdescrip_plato;
	public String mprecio_plato; 
	public String mtiempo_prep; 
	public String mserial_plato; 

	public Item(String url_img_plato, String nom_plato, String descrip_plato, String precio_plato, String serial_plato){
		super(); 
		murl_img_plato = url_img_plato; 
		mnom_plato = nom_plato; 
		mdescrip_plato = descrip_plato; 
		mprecio_plato = precio_plato; 
		mserial_plato = serial_plato; 
	}

	public String getImageUrl(){
		return murl_img_plato; 
	}
	public void setImageUrl(String url_img_plato){
		murl_img_plato = url_img_plato; 
	}

	public String getNomplato(){
		return mnom_plato; 
	}
	public void setNomplato(String nom_plato){
		mnom_plato = nom_plato; 
	}
	public String getDescripPlato(){
		return mdescrip_plato; 
	}
	public void setDescripPlato(String descrip_plato){
		mdescrip_plato = descrip_plato; 
	}
	public String getPrecioPlato(){
		return mprecio_plato; 
	}
	public void setPrecioPlato(String precio_plato){
		mprecio_plato = precio_plato; 
	}
	public String getSerialPlato(){
		return mserial_plato; 
	}
	public void setSerialPlato(String serial_plato){
		mserial_plato = serial_plato; 
	}

}
