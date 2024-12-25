package com.example.eliteauto.ui;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eliteauto.R;
import com.example.eliteauto.adapter.AvisAdapter;
import com.example.eliteauto.model.AvisProprietaire;
import com.example.eliteauto.network.ApiClient;
import com.example.eliteauto.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvisActivity extends AppCompatActivity {
    private ApiService apiService;
    private RecyclerView recyclerView;
    private AvisAdapter avisAdapter;
    private Long proprietaireId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avis);

        recyclerView = findViewById(R.id.recyclerViewAvis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialisation de l'adaptateur avec une liste vide
        avisAdapter = new AvisAdapter(new ArrayList<>());
        recyclerView.setAdapter(avisAdapter);

        proprietaireId = getIntent().getLongExtra("proprietaireId", -1);
        Log.d(TAG, "Proprietaire ID récupéré : " + proprietaireId);

        apiService = ApiClient.getClient().create(ApiService.class);
        fetchAvis(proprietaireId);
        Button btnRetour = findViewById(R.id.btnRetour);
        btnRetour.setOnClickListener(v -> {
            // Retour à l'activité précédente
            finish();
        });
    }


    private void fetchAvis(Long proprietaireId) {
        Log.d(TAG, "Envoi de la requête pour récupérer les avis...");
        apiService.getAvisByProprietaireId(proprietaireId).enqueue(new Callback<List<AvisProprietaire>>() {
            @Override
            public void onResponse(Call<List<AvisProprietaire>> call, Response<List<AvisProprietaire>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<AvisProprietaire> avisList = response.body();
                    Log.d(TAG, "Nombre d'avis récupérés : " + avisList.size());

                    if (avisList.isEmpty()) {
                        Log.w(TAG, "Aucun avis trouvé pour le propriétaire ID : " + proprietaireId);
                        Toast.makeText(AvisActivity.this, "Aucun avis disponible", Toast.LENGTH_SHORT).show();
                    }

                    avisAdapter = new AvisAdapter(avisList);
                    recyclerView.setAdapter(avisAdapter);
                    Log.d(TAG, "Adaptateur attaché avec succès au RecyclerView.");
                } else {
                    Log.e(TAG, "Erreur lors de la récupération des avis : " + response.code());
                    Toast.makeText(AvisActivity.this, "Erreur lors de la récupération des avis", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AvisProprietaire>> call, Throwable t) {
                Log.e(TAG, "Erreur réseau : " + t.getMessage());
                Toast.makeText(AvisActivity.this, "Erreur réseau", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
