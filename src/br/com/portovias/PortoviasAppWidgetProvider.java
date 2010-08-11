package br.com.portovias;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

public class PortoviasAppWidgetProvider extends AppWidgetProvider {

	public static boolean first = true;
	private static int[] appWidgetIds = new int[] {AppWidgetManager.INVALID_APPWIDGET_ID};

	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {		
		PortoviasAppWidgetProvider.appWidgetIds = appWidgetIds;
		updateView(context, appWidgetManager);
	}

	public static void updateView(Context context,
			AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		new WidgetUpdater(context, appWidgetManager, appWidgetIds).start();		
	}
	
	public static void updateView(Context context, AppWidgetManager appWidgetManager){
		updateView(context, appWidgetManager, appWidgetIds);
	}

}
