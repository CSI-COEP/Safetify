package com.safetify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class FirstAid extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_aid);

        String strs = "The Important Rules of First Aid\n" +
                "Here are the rules of first aid emergency care in nutshell. If time's a'wastin', don't worry. Just look over this Top 10 checklist, and you'll be prepared to begin your first aid care for real!\n" +
                "\n" +
                "Shout for help! Don't be afraid to use your lungs and shout for help as soon as you begin first aid measures. Keep shouting for help until you know you've been heard and action has been taken. Professional help can't come soon enough—if it's needed!\n" +
                "Assess the situation and scout the territory. If possible, ask the injured person what happened. Can she speak? Can she tell you how serious the accident is? Also, look around and make sure that performing first aid isn't going to be hazardous to your health. Are there any exposed wires near the injured person? Are there toxic fumes or flames? Is the ice hard enough for you to walk on or the water calm enough to jump in? In short, make sure you aren't in any danger before you start first aid. You won't be much help if you get injured, too.\n" +
                "Determine if the accident warrants a visit to a hospital—or simply a cleansing and a Band-Aid. If the injured person can talk, great. If the person simply needs stitches, don't call for an ambulance, just make a trip to the emergency room. But if he or she is unconscious, you need to make that 911 call. Check those important ABCs: Are the airways clear? Is he or she breathing? What about circulation? Is there a pulse? And, most importantly, it's up to you to decide whether or not to move the injured person. Sometimes this can't be helped. Once you've decided that you can safely walk on the ice or run past the flames, you might have to save the person in jeopardy by pulling or carrying them to safety, away from flames, thin ice, or toxic fumes. Here's a good rule to remember: don't move a person if there isn't a life-or-death reason to do so. You might cause more harm than good.\n" +
                "Ouch!\n" +
                "Don't move an injured person if you don't have to. As long as you're not in a burning building or drowning at sea, it is best to let a person lie where he or she is. If the victim has back, head, or neck injuries, moving him or her can make the injuries worse or even cause permanent damage or death.\n" +
                "\n" +
                "If you are trained and certified in CPR and a person is choking or cannot breathe, begin CPR right away. If you are not trained in CPR, do not attempt to resuscitate. You can break the ribs or puncture the lungs, for example, and if the person is choking, you can actually force the object further down his or her throat! If you don't know CPR, use mouth-to-mouth resuscitation techniques or for choking, use the Heimlich Maneuver. Also, take the injured person's pulse and loosen his or her clothes to make breathing easier.\n" +
                "Stop the bleeding. If the injured person is bleeding, apply direct, even pressure with a cloth and your hands to slow the flow. (To protect yourself against HIV and other infections while in direct contact with blood, don't forget to practice the universal guidelines for preventing infection.) Lift up a bleeding limb if it doesn't cause substantial additional pain. Make and apply a tourniquet only as a last resort. (See How to Treat Wounds and Stop Bleeding for details on using a tourniquet.)\n" +
                "First Aids\n" +
                "\n" +
                "CPR is short for Cardiopulmonary Resuscitation. When administered immediately to a patient suffering cardiac or respiratory distress, CPR can save a life. However, it's best to take a course to learn CPR. It's not safe to rely solely on the knowledge you gain from reading a book. You need to be certified in order to perform CPR correctly and save lives.";
        TextView txtview = findViewById(R.id.txtview);
        txtview.setText(strs);
    }
}