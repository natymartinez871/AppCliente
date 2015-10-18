package com.ayalamart.appcliente;


import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Locale;

import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.ayalamart.helper.AppController;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

public class Act_RealizarPago extends AppCompatActivity {
	private ProgressDialog pDialog;
	String url_pago = "https://api.instapago.com/payment/"; 
	String body_encod; 
	String KeyId = "0AA3DD33-5A8E-4088-8104-FC0F3A1A7AF5"; 
	String PublicKeyId = "e449f92c01e7421f1d52011e9c674ba0"; 
	Double Amount = 123456.00; 
	String Description = "Pago+prueba"; 
	JSONObject dataRX; 




	private static String TAG = Act_RealizarPago.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act__realizar_pago);
		pDialog = new ProgressDialog(this); 

		ScrollView scrollPago = (ScrollView)findViewById(R.id.scrollView_pago_realizado); 
		scrollPago.setVisibility(View.GONE);

		Button but_pagar = (Button)findViewById(R.id.but_pagar); 
		but_pagar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				AutoCompleteTextView cardholder = (AutoCompleteTextView)findViewById(R.id.etNombreTDC); 
				String CardHolder = cardholder.getText().toString(); 
				CardHolder = CardHolder.replace(" ", "+"); 

				AutoCompleteTextView cardholderID = (AutoCompleteTextView)findViewById(R.id.etCedulaTDC);
				String CardHolderId = cardholderID.getText().toString(); 

				AutoCompleteTextView cardnumber = (AutoCompleteTextView)findViewById(R.id.etNroTDC); 
				String CardNumber = cardnumber.getText().toString(); 

				AutoCompleteTextView cvc = (AutoCompleteTextView)findViewById(R.id.etCVCTDC);
				String CVC = cvc.getText().toString(); 

				String ExpirationDate = "10%2F2021"; 
				String StatusId = "2"; 
				String IP = buscarIP(); 
				String OrderNumber = "123456"; 


				body_encod =  "KeyId" + "=" + KeyId + "&" + "PublicKeyId" + "=" + PublicKeyId + "&" + "Amount" + "=" + Amount + "&" +
						"Description" + "=" + Description + "&" + "CardHolder" + "=" + CardHolder + "&" + "CardHolderId" + "=" + CardHolderId + "&" + 
						"CardNumber" + "=" + CardNumber + "&" + "CVC" + "=" + CVC + "&" + "ExpirationDate" + "=" + ExpirationDate  + "&" + "StatusId" + "=" + StatusId 
						+ "&" + "IP" + "=" + IP + "&" + "OrderNumber" + "=" + OrderNumber ; 
				Log.d(TAG, body_encod.toString()); 

				pDialog.setMessage("Cargando...");
				pDialog.show(); 

				StringRequest pagoReq = new StringRequest(Request.Method.POST,
						url_pago,
						new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {

						Log.d(TAG, response); 
						String strResponse = response;
						try {
							JSONObject objresp = new JSONObject(strResponse);
							//Log.d("My App", objresp.toString());
							int maxLogSize = 1000;
							for(int i = 0; i <= objresp.toString().length() / maxLogSize; i++) {
								int start = i * maxLogSize;
								int end = (i+1) * maxLogSize;
								end = end > objresp.toString().length() ? objresp.toString().length() : end;
								Log.v(TAG, objresp.toString().substring(start, end));
							}
							String id = objresp.getString("id"); //Código del pago (Máx. 32 caracteres)
							String message = objresp.getString("message"); // Descripción de la respuesta (Máx. 200 caracteres)
							String lote = objresp.getString("lote"); // 
							String sequence = objresp.getString("sequence"); // Número de identificación bancario universal (Máx. 12 caracteres)
							String approval = objresp.getString("approval"); //Número de aprobación bancaria (Máx. 6 caracteres)
							String responsecode = objresp.getString("responsecode");  //
							String ordernumber = objresp.getString("ordernumber"); // Número de orden indicado por el comercio
							String deferred = objresp.getString("deferred");  //
							String code= objresp.getString("code"); //Código del respuesta del pago (Máx. 3 caracteres)
							String voucher = objresp.getString("voucher"); //HTML que contiene el recibo del pago que debe ser mostrado en la confirmación del pago al cliente
							String success = objresp.getString("success"); //Indica si fue procesado el pago (true o false)
							String reference = objresp.getString("reference"); //Número de referencia del pago (Máx. 6 caracteres)
							/*Log.d(TAG + "VOUCHER", voucher); 
							Log.d(TAG + "id", id);
							Log.d(TAG + "message", message);
							Log.d(TAG + "lote", lote);
							Log.d(TAG + "sequence", sequence);
							Log.d(TAG + "approval", approval);
							Log.d(TAG + "responsecode", responsecode);
							Log.d(TAG + "ordernumber", ordernumber);
							Log.d(TAG + "deferred", deferred);
							Log.d(TAG + "code", code);
							Log.d(TAG + "success", success);
							Log.d(TAG + "reference", reference);*/ 
							if (message.equals("The CardNumber field is not a valid credit card number.")) {
								Toast.makeText(getApplicationContext(), "Numero de tarjeta inválido", Toast.LENGTH_SHORT).show(); 
							}
							if (code.equals("403")) {
								Toast.makeText(getApplicationContext(), "Pago rechazado por el banco", Toast.LENGTH_SHORT).show(); 
							}else if (code.equals("500")) {
								Toast.makeText(getApplicationContext(), "Error interno del servidor", Toast.LENGTH_SHORT).show(); 

							}else if (code.equals("201")) {
								showpDialog();
								ScrollView scrollPago_i = (ScrollView)findViewById(R.id.scrollView_pago); 
								scrollPago_i.setVisibility(View.GONE);

								ScrollView scrollPago_j = (ScrollView)findViewById(R.id.scrollView_pago_realizado); 
								scrollPago_j.setVisibility(View.VISIBLE);

								WebView htmlWebView = (WebView)findViewById(R.id.wv_voucher); 
								htmlWebView.loadData(Html.fromHtml(voucher).toString(), "text/html", null);

								hidepDialog();
							}else if (code.equals("503")) {
								Toast.makeText(getApplicationContext(), "Error al procesar la información. Revise los datos ingresados e intente de nuevo.", Toast.LENGTH_SHORT).show(); 

							}

							/*if (success.equals("true")) {

								TextView htmlTextView = (TextView)findViewById(R.id.tv_voucher);						
								htmlTextView.setText(Html.fromHtml(Html.fromHtml(voucher).toString())); 
								Log.d(TAG + "HTML", Html.fromHtml(voucher).toString()); 

							}else{
								showpDialog();
								ScrollView scrollPago_i = (ScrollView)findViewById(R.id.scrollView_pago); 
								scrollPago_i.setVisibility(View.GONE);

								ScrollView scrollPago_j = (ScrollView)findViewById(R.id.scrollView_pago_realizado); 
								scrollPago_j.setVisibility(View.VISIBLE);

								WebView htmlWebView = (WebView)findViewById(R.id.wv_voucher); 
								htmlWebView.loadData(Html.fromHtml(voucher).toString(), "text/html", null);
								hidepDialog();

								TextView htmlTextView = (TextView)findViewById(R.id.tv_voucher);
								htmlTextView.setText(Html.fromHtml(voucher));
								Log.d(TAG + "HTML", Html.fromHtml(voucher).toString()); 

							}
							 */

							hidepDialog();
						} catch (Throwable t) {
							Log.e(TAG , "Could not parse, malformed JSON: \"" + strResponse + "\"");
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d("Volley", "Error: " + error.getMessage());
						error.printStackTrace();
						hidepDialog(); 
					}
				}) {

					@Override
					public String getBodyContentType() {
						return "application/x-www-form-urlencoded; charset=UTF-8";
					}
					@Override
					public byte[] getBody() throws AuthFailureError {
						// TODO Auto-generated method stub
						return body_encod.getBytes();
					}
					/*@Override
					protected String getParamsEncoding() {
						return "utf-8";
					}*/

				};
				AppController.getInstance().addToRequestQueue(pagoReq);	
			}
		});

		//here 
		Button but_guardarVoucher = (Button)findViewById(R.id.but_guardarvoucher);
		but_guardarVoucher.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				takeScreenshot();
			}
		});

		Button but_continuar = (Button)findViewById(R.id.but_continuar); 
		but_continuar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent_pagolisto = new Intent(getApplicationContext(), Act_detallespedido.class); 
				intent_pagolisto.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				intent_pagolisto.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				startActivity(intent_pagolisto);
				finish(); 

			}
		});

	}
	private void takeScreenshot() {
		Date now = new Date();
		android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

		try {
			// image naming and path  to include sd card  appending name you choose for file
			String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";
			String mPath2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()  + "/" + now + ".jpg"; 
			// create bitmap screen capture
			View v1 = getWindow().getDecorView().getRootView();
			WebView v2 = (WebView)findViewById(R.id.wv_voucher); 
			v2.setDrawingCacheEnabled(true);
			Bitmap bitmap = Bitmap.createBitmap(v2.getDrawingCache());
			v2.setDrawingCacheEnabled(false);

			File imageFile = new File(mPath2);

			FileOutputStream outputStream = new FileOutputStream(imageFile);
			int quality = 100;
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
			outputStream.flush();
			outputStream.close();

			openScreenshot(imageFile);
		} catch (Throwable e) {
			// Several error may come out with file handling or OOM
			e.printStackTrace();
		}
	}
	private void openScreenshot(File imageFile) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(imageFile);
		intent.setDataAndType(uri, "image/*");
		startActivity(intent);
	}
	private String buscarIP(){
		try {
			WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			int ipAddress = wifiInfo.getIpAddress();
			return String.format(Locale.getDefault(), "%d.%d.%d.%d",
					(ipAddress & 0xff), (ipAddress >> 8 & 0xff),
					(ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
		} catch (Exception ex) {
			Log.e(TAG, ex.getMessage());
			return null;
		}
	}

	/*	public void autorizarpago(){

		if(!objresp.toString.equals(null)){
			showpDialog();
			StringRequest pagoReq = new StringRequest(Request.Method.POST,
					urlencoded,
					new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					VolleyLog.v("Response:%n %s", response);
					String strResponse = response;
					try {
						JSONObject objresp = new JSONObject(strResponse);
						Log.d("My App", objresp.toString());
						String Success = objresp.getString("success"); //Indica si fue procesado el pago (true o false)
						String Message = objresp.getString("message"); // Descripción de la respuesta (Máx. 200 caracteres)
						String Id = objresp.getString("id"); //Código del pago (Máx. 32 caracteres)
						String Code = objresp.getString("code"); //Código del respuesta del pago (Máx. 3 caracteres)
						String Reference = objresp.getString("reference"); //Número de referencia del pago (Máx. 6 caracteres)
						String Voucher = objresp.getString("voucher"); //HTML que contiene el recibo del pago que debe ser mostrado en la confirmación del pago al cliente
						String OrderNo = objresp.getString("ordernumber"); // Número de orden indicado por el comercio
						String Sequence = objresp.getString("sequence"); // Número de identificación bancario universal (Máx. 12 caracteres)
						String Approval = objresp.getString("approval"); //Número de aprobación bancaria (Máx. 6 caracteres)
						 objresp.getString("lote"); // 
						objresp.getString("responsecode");  //
						objresp.getString("deferred");  //

					} catch (Throwable t) {
						Log.e(TAG , "Could not parse, malformed JSON: \"" + strResponse + "\"");
					}
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					VolleyLog.d("Volley", "Error: " + error.getMessage());
					error.printStackTrace();
					hidepDialog(); 
				}
			}) {

				@Override
				public String getBodyContentType() {
					return "application/x-www-form-urlencoded; charset=UTF-8";
				}
			};

			AppController.getInstance().addToRequestQueue(pagoReq);
		}else{
			Toast.makeText(getApplicationContext(), "No se ha iniciado el proceso de compra" , Toast.LENGTH_SHORT).show();
		}

	}*/
	/*
	public void verdatadepago(String data){
		// TextView htmlTextView = (TextView)findViewById(R.id.html_text);
		// htmlTextView.setText(Html.fromHtml(data));
		}*/ 


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.act__realizar_pago, menu);
		return true;


	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showpDialog() {
		if (!pDialog.isShowing())
			pDialog.show();
	}

	private void hidepDialog() {
		if (pDialog.isShowing())
			pDialog.dismiss();
	}
}