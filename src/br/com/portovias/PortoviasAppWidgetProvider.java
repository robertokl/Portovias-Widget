package br.com.portovias;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

public class PortoviasAppWidgetProvider extends AppWidgetProvider {

	public static boolean first = true;
	private static SimpleDateFormat sf = new SimpleDateFormat("dd/MM hh:mm:ss");
	private static int[] appWidgetIds = new int[] {AppWidgetManager.INVALID_APPWIDGET_ID};

	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {		
		PortoviasAppWidgetProvider.appWidgetIds = appWidgetIds;
		if (first) {
			return;
		}
		updateView(context, appWidgetManager);
	}

	public static void updateView(Context context,
			AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		RemoteViews r = new RemoteViews(context.getPackageName(),
				R.layout.widget);
		r.setViewVisibility(R.id.ProgressBarLayour, View.VISIBLE);
		appWidgetManager.updateAppWidget(appWidgetIds, r);

		String username = PortoviasHelper.getUsername(context);
		String password = PortoviasHelper.getPassword(context);
		if (!username.equals("") && !(username == null) && !password.equals("")
				&& !(password == null)) {
			PortoviasHelper.prepareUserAgent(context);
			try {
				String content = PortoviasHelper.getUrlContent(username,
						password);
				r.setTextViewText(R.id.ResultText, content);
				r.setTextViewText(R.id.LastUpdatedTextView,
						sf.format(Calendar.getInstance().getTime()));
			} catch (Exception e) {
			}
		}
		r.setViewVisibility(R.id.ProgressBarLayour, View.GONE);
		
		Intent intent = new Intent(context, AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		r.setOnClickPendingIntent(R.id.WidgetLayout, pendingIntent);
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC, System.currentTimeMillis() + (60 * 1000), pendingIntent);	
		
		appWidgetManager.updateAppWidget(appWidgetIds, r);
		
	}
	
	public static void updateView(Context context, AppWidgetManager appWidgetManager){
		updateView(context, appWidgetManager, appWidgetIds);
	}

}
