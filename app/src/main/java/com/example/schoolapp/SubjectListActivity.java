package com.example.schoolapp;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubjectListActivity extends AppCompatActivity {


    // Due date for Physics 2
    List<Subject> subjects = DataManager.getInstance().getSubjects();

    // Add subjects to the list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_subject_list);
        GridLayout gridLayout = findViewById(R.id.subjectGrid);
        LayoutInflater inflater = LayoutInflater.from(this);


        //Fait basculer tout ce qui a besoin d'une urgence.
        for(Subject subjectCheck: subjects)
        {
            subjectCheck.CheckEmergency();
            if(subjectCheck.getEmergency())
            {
                Log.d("TEST EMERGENCY READING","IT IS URGENT");
            }
        }
        for (Subject subject : subjects) {

            // Inflate the card layout
            View card = inflater.inflate(R.layout.subject_card, gridLayout, false);


            TextView subjectName = card.findViewById(R.id.subjectName);
            subjectName.setText(subject.getName());


            if (subject.getEmergency()) {

                subjectName.setBackgroundColor(Color.parseColor("#FF0000"));


            } else {
                // Set the default background color (you can set it to any other color or drawable if needed)
                subjectName.setBackgroundColor(Color.WHITE); // Or use a specific color code, like Color.WHITE
            }

            // Optional: add a click listener
            card.setOnClickListener(v -> {
                // Handle click event
            });

            // Add the card to the GridLayout
            gridLayout.addView(card);
        }
    }



}

