package com.example.eliteauto.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.eliteauto.R;
import com.example.eliteauto.adapter.VoitureAdapter;
import com.example.eliteauto.model.Voiture;
import com.example.eliteauto.network.ApiClient;
import com.example.eliteauto.network.ApiService;
import com.example.eliteauto.services.DisponibiliteService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VoitureAdapter voitureAdapter;
    private ApiService apiService;
    private FloatingActionButton fabFilter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startDisponibiliteService();
        setSupportActionBar(findViewById(R.id.toolbar));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this::fetchVoitures);

        fabFilter = findViewById(R.id.fabFilter);
        fabFilter.setOnClickListener(v -> {
            // TODO: Implémenter la logique de filtrage
            Toast.makeText(MainActivity.this, "Filtrage à implémenter", Toast.LENGTH_SHORT).show();
        });

        apiService = ApiClient.getClient().create(ApiService.class);

        fetchVoitures();
    }
    private void startDisponibiliteService() {
        Intent serviceIntent = new Intent(this, DisponibiliteService.class);
        startService(serviceIntent); // Démarre le service en arrière-plan
        Toast.makeText(this, "Service de disponibilité démarré", Toast.LENGTH_SHORT).show();
    }
    private void fetchVoitures() {
        swipeRefreshLayout.setRefreshing(true);
        apiService.getAllVoitures().enqueue(new Callback<List<Voiture>>() {
            @Override
            public void onResponse(Call<List<Voiture>> call, Response<List<Voiture>> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    voitureAdapter = new VoitureAdapter(response.body(), voiture -> {
                        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                        intent.putExtra("imageUrl", voiture.getImageUrl());
                        intent.putExtra("marqueModele", voiture.getMarque() + " " + voiture.getModele());
                        intent.putExtra("annee", String.valueOf(voiture.getAnnee()));
                        intent.putExtra("prix", voiture.getPrixParJour().toString());
                        intent.putExtra("nombrePortes", String.valueOf(voiture.getNombrePortes()));
                        intent.putExtra("typeCarburant", voiture.getTypeCarburant());
                        intent.putExtra("montantCaution", voiture.getMontantCaution().toString());
                        intent.putExtra("nombreChevaux", String.valueOf(voiture.getNombreChevaux()));
                        intent.putExtra("nombrePassagers", String.valueOf(voiture.getNombrePassagers()));

                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation(MainActivity.this,
                                        findViewById(R.id.imageVoiture),
                                        "imageVoiture");
                        startActivity(intent, options.toBundle());
                    });
                    recyclerView.setAdapter(voitureAdapter);
                } else {
                    Log.e("MainActivity", "Erreur : " + response.code());
                    Toast.makeText(MainActivity.this, "Erreur lors du chargement des voitures", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Voiture>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Log.e("MainActivity", "Erreur lors de la récupération des voitures", t);
                Toast.makeText(MainActivity.this, "Erreur réseau", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.login_button) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            return true;
        } else if (item.getItemId() == R.id.action_disconnect) {
            Toast.makeText(this, "Se déconnecter", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}