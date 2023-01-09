package com.santara.osmdroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    WebView wbv;
    Double LatTujuan=0.0,LongTujuan=0.0,LatAsal=0.0,LongAsal=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        wbv = (WebView) findViewById(R.id.MyWeb);
        wbv.getSettings().setJavaScriptEnabled(true);
        WebSettings settings = wbv.getSettings();
        settings.setDomStorageEnabled(true);

        Intent intent = getIntent();
        LatTujuan=intent.getDoubleExtra("LatTujuan",0);
        LongTujuan = intent.getDoubleExtra("LongTujuan",0);
        LatAsal = intent.getDoubleExtra("LatAsal",0);
        LongAsal = intent.getDoubleExtra("LongAsal",0);

        wbv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
               if (URLUtil.isNetworkUrl(url)){
                   return false;
               }
               if (appInstallOrNot(null)){
                   Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                   startActivity(intent);
               }else{

               }
               return true;
            }
        });

        wbv.loadUrl("https://www.google.co.id/maps?"+"saddr="+LatAsal+","+LongAsal+"&daddr="+LatTujuan+","+LongTujuan);
        Toast.makeText(this, "Loading Direction, Please Wait", Toast.LENGTH_LONG).show();
    }

    private boolean appInstallOrNot(String uri){
        PackageManager pm = getPackageManager();
        try{
            pm.getPackageInfo(uri,PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}