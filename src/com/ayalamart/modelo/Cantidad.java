package com.ayalamart.modelo;

public class Cantidad {


	private String cantidad; 


	public Cantidad(){
	}
	public Cantidad(String cantidad){
		this.cantidad = cantidad; 
	}
	public String getCantidad(){
		return cantidad; 
	}
	public void setCantidad(String cantidad){
		this.cantidad = cantidad; 
	}
}