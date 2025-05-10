package com.example.schoolapp.adminPart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolapp.R;

public class AddTeacherActivity extends AppCompatActivity {
    Button buttonReturn;
    Intent intentReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_teacher);
        intentReturn = new Intent(this, DashboardActivityMain.class);
        buttonReturn = findViewById(R.id.buttonReturn);

        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentReturn);
            }
        });

    }


}