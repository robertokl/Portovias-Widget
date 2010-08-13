package br.com.portovias;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

public class WidgetUpdater extends Thread {

	private Context context;
	private int[] appWidgetIds;
	private AppWidgetManager appWidgetManager;
	private static SimpleDateFormat sf = new SimpleDateFormat("dd/MM HH:mm:ss");

	public WidgetUpdater(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		this.context = context;
		this.appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, PortoviasAppWidgetProvider.class));;
		this.appWidgetManager = appWidgetManager;
	}

	public void run() {
		Log.i("Portovias Widget", "Ids: " + getAppWidgetIds());
		RemoteViews r = new RemoteViews(context.getPackageName(), R.layout.widget);
		try {
			r.setViewVisibility(R.id.ProgressBarLayour, View.VISIBLE);
			appWidgetManager.updateAppWidget(appWidgetIds, r);

			String username = PortoviasHelper.getUsername(context);
			String password = PortoviasHelper.getPassword(context);
			if (!username.equals("") && !(username == null) && !password.equals("") && !(password == null)) {
				PortoviasHelper.prepareUserAgent(context);
				try {
					String content = PortoviasHelper.getUrlContent(username, password);
					r.setTextViewText(R.id.ResultText, content);
					r.setTextViewText(R.id.LastUpdatedTextView, sf.format(Calendar.getInstance().getTime()));
				} catch (Exception e) {
					Log.e("Portovias Widget", "Sem conexao: " + e.getMessage());
				}
			}
		} catch (Exception e) {
			Log.e("Portovias Widget", "Erro estranho: " + e.getMessage());
		} finally {
			r.setOnClickPendingIntent(R.id.WidgetLayout, PortoviasHelper.createPendingIntent(context));
			r.setViewVisibility(R.id.ProgressBarLayour, View.GONE);
			appWidgetManager.updateAppWidget(appWidgetIds, r);
		}
	}

	private String getAppWidgetIds() {
		String s = "";
		for (int id : appWidgetIds) {
			s += Integer.valueOf(id) + ", ";
		}
		return s;
	}
}
