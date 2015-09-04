package com.ayalamart.appcliente;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Act_Login<D> extends Activity implements LoaderCallbacks<Cursor> {

	private static final String DUMMY_CREDENTIALS = "user@test.com:hello";

	private UserLoginTask userLoginTask = null;
	private View loginFormView;
	private View progressView;
	private AutoCompleteTextView emailTextView;
	private EditText passwordTextView;
	private TextView signUpTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act__login);

		emailTextView = (AutoCompleteTextView) findViewById(R.id.email);
		//loadAutoComplete();

		passwordTextView = (EditText) findViewById(R.id.password);
		passwordTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
				if (id == EditorInfo.IME_NULL) {
					initLogin();
					return true;
				}
				return false;
			}
		});

		Button loginButton = (Button) findViewById(R.id.email_sign_in_button);
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				initLogin();
			}
		});

		loginFormView = findViewById(R.id.login_form);
		progressView = findViewById(R.id.login_progress);

		//adding underline and link to signup textview
		signUpTextView = (TextView) findViewById(R.id.registrarse);
		signUpTextView.setPaintFlags(signUpTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		Linkify.addLinks(signUpTextView, Linkify.ALL);

		signUpTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("LoginActivity", "Sign Up Activity activated.");

				Intent intent_signup = new Intent(getApplicationContext(), Act_Signup.class); 
				startActivity(intent_signup);
			}
		});
	}

	//
	//
	//      REVISAR ESTE LOADER 10/08/2015
	//
	//
	//	private void loadAutoComplete() {
	//		getLoaderManager().initLoader(0, null, (android.app.LoaderManager.LoaderCallbacks<D>) this);
	//
	//	}


	/**
	 * Validate Login form and authenticate.
	 */
	public void initLogin() {
		if (userLoginTask != null) {
			return;
		}

		emailTextView.setError(null);
		passwordTextView.setError(null);

		String email = emailTextView.getText().toString();
		String password = passwordTextView.getText().toString();

		boolean cancelLogin = false;
		View focusView = null;

		if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
			passwordTextView.setError(getString(R.string.invalid_password));
			focusView = passwordTextView;
			cancelLogin = true;
		}

		if (TextUtils.isEmpty(email)) {
			emailTextView.setError(getString(R.string.field_required));
			focusView = emailTextView;
			cancelLogin = true;
		} else if (!validarCorreo(email)) {
			emailTextView.setError(getString(R.string.invalid_email));
			focusView = emailTextView;
			cancelLogin = true;
		}

		if (cancelLogin) {
			// error in login
			focusView.requestFocus();
		} else {
			// show progress spinner, and start background task to login
			showProgress(true);
			userLoginTask = new UserLoginTask(email, password);
			userLoginTask.execute((Void) null);
		}
	}

	//	private boolean isEmailValid(String email) {
	//		//add your own logic
	//		if ( email.contains("@") != true) {
	//			Toast errorprueba = Toast.makeText(getApplicationContext(), "Debe ingresar una dirección de correo válida", Toast.LENGTH_SHORT);
	//			errorprueba.show();
	//			return false; 
	//
	//		} else {
	//
	//			return true; 
	//
	//		}
	//
	//	}

	private boolean validarCorreo (String correo_str){
		String PATRON_CORREO = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"; 

		Pattern patron = Pattern.compile(PATRON_CORREO); 
		Matcher correlacionador = patron.matcher(correo_str); 
		return correlacionador.matches();	
	}
	private boolean validarPassword (String contrasena_str){

		if (contrasena_str != null && contrasena_str.length() > 5 ){
			return true; 
		}
		return false; 

	}

	private boolean isPasswordValid(String password) {
		//add your own logic
		String passprueba = "123456"; 

		if (password != passprueba) {
			Toast errorprueba2 = Toast.makeText(getApplicationContext(), "La contraseña es incorrecta", Toast.LENGTH_SHORT); 
			errorprueba2.show();
			return true; 
		}
		else{
			//return password.length() > 4;
			return false; 

		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

			loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			loginFormView.animate().setDuration(shortAnimTime).alpha(
					show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
						}
					});

			progressView.setVisibility(show ? View.VISIBLE : View.GONE);
			progressView.animate().setDuration(shortAnimTime).alpha(
					show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							progressView.setVisibility(show ? View.VISIBLE : View.GONE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			progressView.setVisibility(show ? View.VISIBLE : View.GONE);
			loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}


	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(this,
				// Retrieve data rows for the device user's 'profile' contact.
				Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
						ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

				// Select only email addresses.
				ContactsContract.Contacts.Data.MIMETYPE +
				" = ?", new String[]{ContactsContract.CommonDataKinds.Email
						.CONTENT_ITEM_TYPE},

				// Show primary email addresses first. Note that there won't be
				// a primary email address if the user hasn't specified one.
				ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		List<String> emails = new ArrayList<String>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			emails.add(cursor.getString(ProfileQuery.ADDRESS));
			cursor.moveToNext();
		}

		addEmailsToAutoComplete(emails);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {

	}

	private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
		//Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
		ArrayAdapter<String> adapter =
				new ArrayAdapter<String>(Act_Login.this,
						android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

		emailTextView.setAdapter(adapter);
	}


	private interface ProfileQuery {
		String[] PROJECTION = {
				ContactsContract.CommonDataKinds.Email.ADDRESS,
				ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
		};

		int ADDRESS = 0;
		int IS_PRIMARY = 1;
	}

	/**
	 * Async Login Task to authenticate
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

		private final String emailStr;
		private final String passwordStr;

		UserLoginTask(String email, String password) {
			emailStr = email;
			passwordStr = password;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			//this is where you should write your authentication code
			// or call external service
			// following try-catch just simulates network access
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}

			//using a local dummy credentials store to authenticate
			String[] pieces = DUMMY_CREDENTIALS.split(":");
			if (pieces[0].equals(emailStr) && pieces[1].equals(passwordStr)) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			userLoginTask = null;
			//stop the progress spinner
			showProgress(false);

			if (success) {
				//  login success and move to main Activity here.
			} else {
				// login failure
				passwordTextView.setError(getString(R.string.incorrect_password));
				passwordTextView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			userLoginTask = null;
			showProgress(false);
		}
	}
}
