package com.safetify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class SelfDefence extends AppCompatActivity {

    RelativeLayout list1, list2, list3, list4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_defence);

        list1 = findViewById(R.id.list1);
        list2 = findViewById(R.id.list2);
        list3 = findViewById(R.id.list3);
        list4 = findViewById(R.id.list4);

        list1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelfDefence.this, StepsShow.class);
                String str = "List1";
                intent.putExtra("listName", str);
                startActivity(intent);
            }
        });
        list2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelfDefence.this, StepsShow.class);
                String str = "List2";
                intent.putExtra("listName", str);
                startActivity(intent);
            }
        });
        list3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelfDefence.this, StepsShow.class);
                String str = "List3";
                intent.putExtra("listName", str);
                startActivity(intent);
            }
        });
        list4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelfDefence.this, StepsShow.class);
                String str = "List4";
                intent.putExtra("listName", str);
                startActivity(intent);
            }
        });
    }
}