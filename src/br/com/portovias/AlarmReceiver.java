package br.com.portovias;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			AppWidgetManager appWidgetManager = AppWidgetManager
					.getInstance(context);
			PortoviasAppWidgetProvider.updateView(context, appWidgetManager);
		} catch(Exception e) {
			//???
		}
	}

}
