package com.ayalamart.appcliente;




import java.util.HashMap;

import com.ayalamart.appcliente.R;
import com.ayalamart.helper.GestionSesionesUsuario;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act_detallespedido);
		
		sesion = new GestionSesionesUsuario(getApplicationContext()); 
		
		nombres = (TextView)findViewById(R.id.tv_nroplatos); 
		apellidos= (TextView)findViewById(R.id.textView2); 
		detallespedido = (TextView)findViewById(R.id.textView3); 
		totalpedido = (TextView)findViewById(R.id.textView4); 
		numorden = (TextView)findViewById(R.id.textView6); 
		
		HashMap<String, String> usuario = sesion.getDetallesUsuario(); 

		String name = usuario.get(GestionSesionesUsuario.KEY_NAME); 
		String email = usuario.get(GestionSesionesUsuario.KEY_EMAIL); 
		String nombre = usuario.get(GestionSesionesUsuario.nombre); 
		String apellido = usuario.get(GestionSesionesUsuario.apellido); 
		String correo = usuario.get(GestionSesionesUsuario.correo); 
		String telefono = usuario.get(GestionSesionesUsuario.telefono); 
		String cedula = usuario.get(GestionSesionesUsuario.cedula); 
		
		nombres.setText(nombre);
		apellidos.setText(apellido);
		detallespedido.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas viverra nisl urna, at lobortis dolor egestas id.");
		totalpedido.setText("Bs."+"123456");
		numorden.setText("123456");

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
