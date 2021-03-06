package com.ayalamart.appcliente;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ayalamart.appcliente.R.drawable;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Act_Micuenta extends AppCompatActivity {
	private static final int CAMERA_REQUEST = 1; 
	//private ImageButton boton_foto; 
	private ImageView fotousuario;  
	String mCurrentPhotoPath;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act__micuenta);

		Button boton_actualizarcuenta = (Button)findViewById(R.id.but_actualizar_micuenta); 
		boton_actualizarcuenta.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast errorprueba_1 = Toast.makeText(getApplicationContext(), "Cuenta actualizada con �xito", Toast.LENGTH_SHORT); 
				errorprueba_1.show();

			}
		});

	
		ImageView fotousuario = (ImageView)findViewById(R.id.fotousuarioIV);
		fotousuario.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast errorprueba_2 = Toast.makeText(getApplicationContext(), "Haga longclick para cambiar la foto ", Toast.LENGTH_SHORT); 
				errorprueba_2.show();

			}
		});
		fotousuario.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				Toast errorprueba_3 = Toast.makeText(getApplicationContext(), "Se hizo longclick ahora se puede pasar a la clase de montar foto", Toast.LENGTH_SHORT); 
				errorprueba_3.show();
				Intent camaraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
				startActivityForResult(camaraIntent, CAMERA_REQUEST);
				return false;

			}
		});


	}


	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
			Bitmap photo = (Bitmap)data.getExtras().get("data"); 
			fotousuario.setImageBitmap(photo);
		}


	}
//	private void dispatchTakePictureIntent(){
//		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		// Ensure that there's a camera activity to handle the intent
//		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//			// Create the File where the photo should go
//			File photoFile = null;
//			try {
//				photoFile = createImagefile();
//			} catch (IOException ex) {
//				// Error occurred while creating the File
//
//			}
//			// Continue only if the File was successfully created
//			if (photoFile != null) {
//				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//						Uri.fromFile(photoFile));
//				startActivityForResult(takePictureIntent, CAMERA_REQUEST);
//			}
//		}
//
//
//	}
//	private File createImagefile() throws IOException{
//		// Create an image file name
//		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//		String imageFileName = "JPEG_" + timeStamp + "_";
//		File storageDir = Environment.getExternalStoragePublicDirectory(
//				Environment.DIRECTORY_PICTURES);
//		File image = File.createTempFile(
//				imageFileName,  /* prefix */
//				".jpg",         /* suffix */
//				storageDir      /* directory */
//				);
//
//		// Save a file: path for use with ACTION_VIEW intents
//		mCurrentPhotoPath = "file:" + image.getAbsolutePath();
//		return image;
//
//
//	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.act__micuenta, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.acerca_de_sett) {
			Intent intent_acercade = new Intent(getApplicationContext(), Act_acercade.class);
			startActivity(intent_acercade);
			return true;
		} 
		else if (id == R.id.menu_sett) {

		}
		else if (id == R.id.donde_estamos_sett) {
			Intent intent_map = new Intent(getApplicationContext(), Act_Mapa.class); 
			startActivity(intent_map);

		}
		else if (id == R.id.mi_cuenta_sett) {
			Intent intent_micuenta = new Intent(getApplicationContext(), Act_Micuenta.class);
			startActivity(intent_micuenta);
		}

		return super.onOptionsItemSelected(item);	
	}
}
