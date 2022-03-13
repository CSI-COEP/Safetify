package com.safetify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class StepsShow extends AppCompatActivity {

    String str;
    ViewPager viewPager;
    TextView techName;
    Integer abc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_show);

        Intent intent = getIntent();

        str = intent.getStringExtra("listName");

        TextView name = findViewById(R.id.name);
        techName = findViewById(R.id.techName);

        viewPager = findViewById(R.id.viewPager);

        techName.setText("Technique 1");

        String texttext= "STEP1: If someone holds you from neck or shoulder\n\n" +
                "STEP2: Be calm and bring yourself to the attacker. At the same time , poke both of your thumbnails into the eyes. Attack with your entire body weight and press the nails deeply.Be quick and use 200% of your power and body weight.\n" +
                "STEP3: A close up of how the thumbs are poked into the eyes,\n\n" +
                "STEP4: Keep walking towards him and throw your entire body weight into his eyes.\n\n" +
                "STEP5: Finally grab his head and pull him towards you.\n\n" +
                "STEP6: When you pull him towards yourself,at the same time kick him with your knee in his stomach or lower abdomen or groin.Be quick.\n\n";

        name.setText(texttext);


        SliderAdapter adapter = new SliderAdapter(StepsShow.this);
        viewPager.setAdapter(adapter);



                if(str == "List1"){
                    techName.setText("Technique 1");
                    viewPager.setAdapter(adapter);
                }  if(str  == "Technique 2"){
                    SliderAdapter2 adapter1 = new SliderAdapter2(StepsShow.this);
                    viewPager.setAdapter(adapter1);
                }  if(str  == "Technique 3"){
                    SliderAdapter3 adapter2 = new SliderAdapter3(StepsShow.this);
                    viewPager.setAdapter(adapter2);
                } if(str == "Technique 4"){
                    SliderAdapter4 adapter3 = new SliderAdapter4(StepsShow.this);
                    viewPager.setAdapter(adapter3);
                }

            }



}