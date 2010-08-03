package br.com.portovias;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

public class PortoviasAppWidgetProvider extends AppWidgetProvider {
	
	
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    	appWidgetManager.updateAppWidget(appWidgetIds, updateView(context));
    }
    
    public static RemoteViews updateView(Context context){
    	RemoteViews r = new RemoteViews(context.getPackageName(), R.layout.widget);
 
    	String username = PortoviasHelper.getUsername(context);
    	String password = PortoviasHelper.getPassword(context);
    	if(!username.equals("") && !(username == null) && !password.equals("") && !(password == null)){
			PortoviasHelper.prepareUserAgent(context);
			try {
				String content = PortoviasHelper.getUrlContent(username, password);
				r.setTextViewText(R.id.ResultText, content);
			} catch (Exception e) {
			}
    	}
    	return r;
    }

}
