package com.example.eliteauto.services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.eliteauto.R;
import com.example.eliteauto.network.ApiClient;
import com.example.eliteauto.network.ApiService;
import com.example.eliteauto.model.Voiture;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisponibiliteService extends Service {

    private ApiService apiService;
    private ScheduledExecutorService scheduler;

    @Override
    public void onCreate() {
        super.onCreate();
        apiService = ApiClient.getClient().create(ApiService.class);

        // Create a notification to show that the service is running
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default")

                .setContentTitle("Disponibilité des voitures")
                .setContentText("Service en cours de vérification...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification = builder.build();

        // Start the service in the foreground
        startForeground(1, notification);

        // Schedule the periodic task to check availability
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::checkDisponibilite, 0, 10, TimeUnit.SECONDS);
    }

    private void checkDisponibilite() {
        apiService.getAllVoitures().enqueue(new Callback<List<Voiture>>() {
            @Override
            public void onResponse(Call<List<Voiture>> call, Response<List<Voiture>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Processing the response: Log the availability of each car
                    for (Voiture voiture : response.body()) {
                        Log.d("DisponibiliteService", "Voiture " + voiture.getMarque() + " " + voiture.getModele() + " disponible.");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Voiture>> call, Throwable t) {
                Log.e("DisponibiliteService", "Erreur lors de la vérification de la disponibilité des voitures", t);
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the scheduler and service when it's no longer needed
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
        }
        stopForeground(true);
        stopSelf();
    }
}
