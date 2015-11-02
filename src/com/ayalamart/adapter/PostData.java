package com.ayalamart.adapter;

import android.os.Parcel;
import android.os.Parcelable;

public class PostData implements Parcelable {

	private String titulo;
	private boolean escogido;
	private String descripcion;
	private String precio; 
	private String url; 
	
	

	public PostData(String nomPlato, String descPlato, String urlImgPlato, String precioPlato, boolean checked) {
		this.titulo = nomPlato;
		this.escogido = checked;
		this.descripcion = descPlato; 
		this.url = urlImgPlato; 
		this.precio = precioPlato; 

	}
	
	public PostData(Parcel in){
		this.titulo= in.readString();
		this.descripcion = in.readString(); 
		this.precio = in.readString(); 
		this.url = in.readString(); 
		this.escogido = in.readInt() == 1 ? true:false;
	}

	public void setChecked(boolean value) {
		this.escogido = value;
	}

	public boolean getChecked() {
		return escogido;
	}

	public String getNombres() {
		return titulo;
	}

	public void setNombres(String Nombres) {
		this.titulo = Nombres;
	}
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String Descripciones) {
		this.descripcion = Descripciones;
	}
	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String Precios) {
		this.precio = Precios;
	}
	public String getURL() {
		return url;
	}

	public void setURL(String URLS) {
		this.url = URLS;
	}
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(getNombres());
		dest.writeString(getDescripcion());
		dest.writeString(getPrecio());
		dest.writeString(getURL());
		dest.writeInt(getChecked() ? 1 : 0);
	}

	public static final Parcelable.Creator<PostData> CREATOR = new Parcelable.Creator<PostData>() {
		public PostData createFromParcel(Parcel in) {
			return new PostData(in);
		}

		public PostData[] newArray(int size) {
			return new PostData[size];
		}
	};
}
