package com.example.schoolapp;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

public class DashboardActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        LinearLayout linearLayout = findViewById(R.id.linear_layout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout noteLinear = findViewById(R.id.noteLinearLayout);

        TextView topBar = (TextView) findViewById(R.id.top_bar);
        topBar.setText("Dahami El Mansouri Taoufik");
        String[] textArray = {
                "T'as eu 20",
                "Bravo, tu as réussi!",
                "Excellent travail!",
                "Tu as bien progressé!",
                "Continue comme ça!",
                "Très bonne note!",
                "Tu peux être fier(e)!",
                "Objectif atteint!",
                "C’est du solide!",
                "Impressionnant!",
                "Bien joué!",
                "T'es sur la bonne voie!",
                "Rien à redire!",
                "Mission accomplie!",
                "Résultat parfait!",
                "Quel niveau!",
                "Tu assures!",
                "Travail propre!",
                "Maîtrise totale!",
                "C'est du haut niveau!",
                "Tu gères!",
                "Rigueur et excellence!",
                "Note méritée!",
                "Tu montes en puissance!",
                "Toujours mieux!",
                "Chaque point compte!",
                "Progrès constant!",
                "C’est du sérieux!",
                "Tu te dépasses!",
                "Rien ne t’arrête!"
        };
        //PARTIE NOTE
        for (String text: textArray)
        {
            CardView card = createCardView(this,text);
            linearLayout.addView(card);

        }
        //PARTIE DEVOIR
        String[] textArrayNote =
                {
                        "Devoir de Mathématiques",
                        "Contrôle d'Histoire",
                        "Évaluation de Physique",
                        "Examen de Français",
                        "Devoir de SVT",
                        "Test d’Anglais",
                        "Contrôle de Chimie",
                        "Interrogation de Géographie",
                        "Exercice de Technologie",
                        "Analyse Littéraire",
                        "QCM de Sciences",
                        "Projet de Philosophie",
                        "Rédaction en Français",
                        "Synthèse d’Histoire-Géo",
                        "Devoir de Statistiques",
                        "Exposé d’Économie",
                        "Étude de cas – SES",
                        "Travail pratique – Physique",
                        "Simulation d’Entretien (Anglais)",
                        "Évaluation de Programmation"
                };
        for (String text: textArrayNote)
        {
            CardView card = createCardView(this,text);
            noteLinear.addView(card);

        }

    }
    public void onMatiereClick(View v)
    {
        Intent intent = new Intent(this, SubjectListActivity.class );
        startActivity(intent);
    }
    public void onNameClick(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Logout");
        builder.setMessage("Are you sur to logout?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
    public CardView createCardView(Context context,String gradeOrDevoirDesc)
    {
        //card spawn
        CardView card = new CardView(context);
        // Convert dp to pixels
        int heightInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, context.getResources().getDisplayMetrics());
        int marginInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, context.getResources().getDisplayMetrics());
        int paddingInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, context.getResources().getDisplayMetrics());

        // LayoutParams with margin
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                heightInDp
        );
        params.setMargins(marginInDp, marginInDp, marginInDp, marginInDp);
        card.setLayoutParams(params);

        // CardView styling
        card.setRadius(10f);
        card.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        card.setCardElevation(5f);

        // Create the TextView
        TextView textView = new TextView(context);
        textView.setText(gradeOrDevoirDesc);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setPadding(paddingInDp, paddingInDp, paddingInDp, paddingInDp);
        textView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        card.setClickable(true);
        card.setFocusable(true); // utile pour accessibilité
        card.setForeground(ContextCompat.getDrawable(context, R.drawable.card_click_feedback));

        card.setOnClickListener(v -> {
            Toast.makeText(context, "Tu as cliqué : " + gradeOrDevoirDesc, Toast.LENGTH_SHORT).show();

        });

        card.addView(textView);

        return card;

    }
}