/*
 * This is a simple example of web-view in Android. 
 * In this example we can see the web page for a given URL. 
 * WebView is a view that display web pages inside your application. 
 * You can specify an HTML string and show it inside your application using WebView. 
 * WebView turns your application to a web application.
 */


package com.turntotech.samplewebview;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity {

   private EditText field;
   private WebView browser;

   @Override		
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
       Log.i("TurnToTech", "Project Name - SampleWebView");
      field = (EditText)findViewById(R.id.urlfield);
      
      //create an object of the class WebView
      browser = (WebView)findViewById(R.id.webView1);
      browser.setWebViewClient(new MyBrowser());
   }


   public void open(View view){
      String url = field.getText().toString();
      browser.getSettings().setLoadsImagesAutomatically(true);
      browser.getSettings().setJavaScriptEnabled(true);
      browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
      
      /*
 	  * In order to load a web url into the WebView , you need to call a method 
 	  * loadUrl(String url) of the WebView class, specifying the required url.
 	  */
      browser.loadUrl(url);

   }
   private class MyBrowser extends WebViewClient {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
         view.loadUrl(url);
         return true;
      }
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
   }

}