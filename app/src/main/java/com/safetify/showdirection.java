package com.safetify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class showdirection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdirection);

        Intent book = getIntent();
        String currentLatLng = book.getStringExtra("lat");
        String latLng = book.getStringExtra("lng");
        String header = book.getStringExtra("header");

        WebView w = (WebView) findViewById(R.id.webView1);
        TextView webHeader = findViewById(R.id.webHeader);
        ImageView backbtn = findViewById(R.id.backbtn);

        webHeader.setText(header);

        // loading http://www.google.com url in the the WebView.
        w.loadUrl("http://maps.google.com/maps?" + "saddr="+currentLatLng + "&daddr="+latLng);


        // this will enable the javascript.
        w.getSettings().setJavaScriptEnabled(true);

        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        w.setWebViewClient(new WebViewClient());

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}