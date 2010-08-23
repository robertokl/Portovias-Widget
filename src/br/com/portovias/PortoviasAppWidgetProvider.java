package br.com.portovias;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PortoviasAppWidgetProvider extends AppWidgetProvider {

	public static boolean first = true;

	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		updateView(context, appWidgetManager);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("Portovias Widget", "Alarme recebido");
		try {
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			PortoviasAppWidgetProvider.updateView(context, appWidgetManager);
		} catch (Exception e) {
			Log.e("Portovias Widget", "Erro no alarme: " + e.getMessage());
		}
	}

	public static void updateView(Context context, AppWidgetManager appWidgetManager) {
		try {
			new WidgetUpdater(context, appWidgetManager).start();
		} catch (Exception e) {
			Log.e("Portovias Widget", "Erro na Thread!");
		}
	}
}
