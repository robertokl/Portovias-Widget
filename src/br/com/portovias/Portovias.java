package br.com.portovias;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Portovias extends Activity implements OnClickListener{

	int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button submitButton = (Button) findViewById(R.id.Submit);
        submitButton.setOnClickListener(this);
        ((TextView) findViewById(R.id.Username)).setText(PortoviasHelper.getUsername(this));
        ((TextView) findViewById(R.id.Password)).setText(PortoviasHelper.getPassword(this));
        
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
    }
    
	public void onClick(View v) {
		 switch (v.getId()) {
           case R.id.Submit: {
        	   TextView username = (TextView)findViewById(R.id.Username);
        	   PortoviasHelper.setUsername(username.getText().toString(), this);
        	   TextView password = (TextView)findViewById(R.id.Password);
        	   PortoviasHelper.setPassword(password.getText().toString(), this);
        	   
        	   AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        	   appWidgetManager.updateAppWidget(mAppWidgetId, PortoviasAppWidgetProvider.updateView(this));;
        	   
        	   Intent resultValue = new Intent();
               resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
               setResult(RESULT_OK, resultValue);
               finish();
        	   break;
           }
       }
	}
}