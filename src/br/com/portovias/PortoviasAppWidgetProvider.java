package br.com.portovias;

import android.app.AlarmManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PortoviasAppWidgetProvider extends AppWidgetProvider {

	public static boolean first = true;
	private static int[] appWidgetIds = new int[] { AppWidgetManager.INVALID_APPWIDGET_ID };

	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		PortoviasAppWidgetProvider.appWidgetIds = appWidgetIds;
		
		long firstTime = System.currentTimeMillis();
		
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		am.setInexactRepeating(AlarmManager.RTC, firstTime + 90 * 1000, 90 * 1000, PortoviasHelper.createPendingIntent(context));
		
		updateView(context, appWidgetManager);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("Portovias Widget", "Alarme recebido");
		try {
			AppWidgetManager appWidgetManager = AppWidgetManager
					.getInstance(context);
			PortoviasAppWidgetProvider.updateView(context, appWidgetManager);
		} catch(Exception e) {
			Log.e("Portovias Widget", "Erro no alarme: " + e.getMessage());
		}
	}
	
	public static void updateView(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		new WidgetUpdater(context, appWidgetManager, appWidgetIds).start();
	}

	public static void updateView(Context context, AppWidgetManager appWidgetManager) {
		updateView(context, appWidgetManager, appWidgetIds);
	}

}
