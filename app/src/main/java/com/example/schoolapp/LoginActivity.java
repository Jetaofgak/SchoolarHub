package com.example.schoolapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolapp.adminPart.DashboardActivityMain;
import com.example.schoolapp.studentPart.DashboardActivityStudentMain;
import com.example.schoolapp.teacherPart.DashboardActivityTeacherMain;

public class LoginActivity extends AppCompatActivity {
    EditText passwordField;
    Button loginButton;
    Intent intent,intent2,intent3,intent4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        passwordField = findViewById(R.id.passwordField);
        loginButton = findViewById(R.id.loginButton);
        intent = new Intent(this, SignUpActivity.class);
        intent2 = new Intent(this, DashboardActivityMain.class);
        intent3 = new Intent(this, DashboardActivityStudentMain.class);
        intent4 = new Intent(this, DashboardActivityTeacherMain.class);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordField.getText().toString();

                // Now you can use the password value!
                if(password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                } else if(password.equals("admin")) {

                    startActivity(intent2);
                }
                else if(password.equals("stud"))
                {
                    startActivity(intent3);
                }
                else if(password.equals("teach"))
                {
                    startActivity(intent4);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void onTextClick(View view)
    {

        startActivity(intent);
    }


}