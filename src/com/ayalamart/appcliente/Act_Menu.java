package com.ayalamart.appcliente;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ayalamart.app.Net_menu;
import com.ayalamart.helper.Platos_menu;
import com.ayalamart.helper.SwipeListAdapter;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;



public class Act_Menu extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

	private String TAG = Act_Menu.class.getSimpleName(); 
	private String URL_PLATOS = "http://api.androidhive.info/json/imdb_top_250.php?offset="; 
	private SwipeRefreshLayout swiperefreshLayout; 
	private ListView listview; 
	private SwipeListAdapter adapter; 
	private List<Platos_menu> listaplatos; 
	private int offSet = 0; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act__menu);

		//listview = (ListView)findViewById(R.id.LV_menu); 
		swiperefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout); 

		listaplatos = new ArrayList<Platos_menu>(); 
		adapter = new SwipeListAdapter(this, listaplatos); 
		listview.setAdapter(adapter);

		swiperefreshLayout.setOnRefreshListener(this);

		swiperefreshLayout.post(new Runnable() {

			@Override
			public void run() {
				swiperefreshLayout.setRefreshing(true);
				fetchPlatos();

			}
		});

	}

	/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.act__menu, menu);
		return true;
	}
	 */
	/*	@Override
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
	 */
	@Override
	public void onRefresh() {
		fetchPlatos(); 

	}
	private void fetchPlatos(){
		swiperefreshLayout.setRefreshing(true);
		String url = URL_PLATOS + offSet; 

		JsonArrayRequest req = new JsonArrayRequest(url, 

				new Response.Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response){
				Log.d(TAG, response.toString()); 

				if(response.length() > 0 ){
					for (int i = 0; i < response.length(); i++) {
						try{
							JSONObject platoObj = response.getJSONObject(i); 

							int rank = platoObj.getInt("rank"); 
							String title = platoObj.getString("title"); 

							Platos_menu p = new Platos_menu(rank, title); 
							listaplatos.add(0, p); 

							if (rank >= offSet) 
								offSet = rank; 

						} catch (JSONException e) {
							Log.e(TAG, "JSON Parsing error:" + e.getMessage());
						}
					}
					adapter.notifyDataSetChanged();
				}
				swiperefreshLayout.setRefreshing(false);
			}	
		}, new Response.ErrorListener() {

			public void onErrorResponse(VolleyError error){
				Log.e(TAG, "Server error" + error.getMessage()); 
				Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show(); 
				swiperefreshLayout.setRefreshing(false);
			}
		}); 
		Net_menu.getInstance().addToRequestQueue(req);
	}	
}
