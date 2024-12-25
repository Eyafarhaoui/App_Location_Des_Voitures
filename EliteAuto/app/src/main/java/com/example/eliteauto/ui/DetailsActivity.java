package com.example.eliteauto.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.eliteauto.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageView imageVoiture = findViewById(R.id.imageVoiture);
        TextView textMarqueModele = findViewById(R.id.textMarqueModele);
        TextView textPrix = findViewById(R.id.textPrix);
        TextView textAnnee = findViewById(R.id.textAnnee);
        TextView textNombrePortes = findViewById(R.id.textNombrePortes);
        TextView textTypeCarburant = findViewById(R.id.textTypeCarburant);
        TextView textMontantCaution = findViewById(R.id.textMontantCaution);
        TextView textNombreChevaux = findViewById(R.id.textNombreChevaux);
        TextView textNombrePassagers = findViewById(R.id.textNombrePassagers);
        MaterialButton buttonReserver = findViewById(R.id.buttonReserver);

        // Récupérer les données passées par l'intent
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String marqueModele = getIntent().getStringExtra("marqueModele");
        String annee = getIntent().getStringExtra("annee");
        String prix = getIntent().getStringExtra("prix");
        String nombrePortes = getIntent().getStringExtra("nombrePortes");
        String typeCarburant = getIntent().getStringExtra("typeCarburant");
        String montantCaution = getIntent().getStringExtra("montantCaution");
        String nombreChevaux = getIntent().getStringExtra("nombreChevaux");
        String nombrePassagers = getIntent().getStringExtra("nombrePassagers");

        // Afficher les données dans les vues
        Glide.with(this).load(imageUrl).into(imageVoiture);
        textMarqueModele.setText(marqueModele);
        textPrix.setText(prix + " €/jour");
        textAnnee.setText(annee);
        textNombrePortes.setText(nombrePortes + " portes");
        textTypeCarburant.setText(typeCarburant);
        textMontantCaution.setText("Caution : " + montantCaution + " €");
        textNombreChevaux.setText(nombreChevaux + " ch");
        textNombrePassagers.setText(nombrePassagers + " passagers");

        buttonReserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implémenter la logique de réservation
                Toast.makeText(DetailsActivity.this, "Réservation à implémenter", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}