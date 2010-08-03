package br.com.portovias;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public class PortoviasHelper {
	private static final String TAG = "PortoviasWidgetHelper";

	private static final int HTTP_STATUS_OK = 200;

	private static final String PREFS_NAME = "PortoviasWidget";

	private static byte[] sBuffer = new byte[512];

	private static String sUserAgent = null;

	private static String portoviasUrl = "http://path.heroku.com/?login=%s&password=%s&format=xml";


	public static String getUsername(Context context) {
		return getDbValue("username", context);
	}

	public static String getPassword(Context context) {
		return getDbValue("password", context);
	}

	private static String getDbValue(String key, Context context) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		return settings.getString(key, "");
	}

	public static void setUsername(String value, Context context) {
		setDbValue("username", value, context);
	}

	public static void setPassword(String value, Context context) {
		setDbValue("password", value, context);
	}

	private static void setDbValue(String key, String value, Context context) {
		Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static void prepareUserAgent(Context context) {
		try {
			// Read package name and version number from manifest
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			sUserAgent = String.format("%s/%s (Linux; Android)",
					info.packageName, info.versionName);

		} catch (NameNotFoundException e) {
			Log.e(TAG, "Couldn't find package information in PackageManager", e);
		}
	}

	protected static synchronized String getUrlContent(String login,
			String password) throws Exception {
		if (sUserAgent == null) {
			throw new Exception("User-Agent string must be prepared");
		}

		// Create client and set our specific user-agent string
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(String.format(portoviasUrl, login,
				password));
		request.setHeader("User-Agent", sUserAgent);

		try {
			HttpResponse response = client.execute(request);

			// Check if server response is valid
			StatusLine status = response.getStatusLine();
			if (status.getStatusCode() != HTTP_STATUS_OK) {
				throw new Exception("Invalid response from server: "
						+ status.toString());
			}

			// Pull content stream from response
			HttpEntity entity = response.getEntity();
			InputStream inputStream = entity.getContent();

			ByteArrayOutputStream content = new ByteArrayOutputStream();

			// Read response into a buffered stream
			int readBytes = 0;
			while ((readBytes = inputStream.read(sBuffer)) != -1) {
				content.write(sBuffer, 0, readBytes);
			}

			// Return result from buffered stream
			return new String(content.toByteArray());
		} catch (IOException e) {
			throw new Exception("Problem communicating with API", e);
		}
	}

}
