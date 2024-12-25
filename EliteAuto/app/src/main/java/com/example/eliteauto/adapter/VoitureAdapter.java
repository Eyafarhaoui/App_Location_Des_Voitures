package com.example.eliteauto.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.eliteauto.R;
import com.example.eliteauto.model.Voiture;
import com.example.eliteauto.model.Disponibilite;
import com.example.eliteauto.ui.AvisActivity; // Import AvisActivity

import java.util.List;

public class VoitureAdapter extends RecyclerView.Adapter<VoitureAdapter.VoitureViewHolder> {

    private List<Voiture> voitures;
    private OnItemClickListener listener;

    public VoitureAdapter(List<Voiture> voitures, OnItemClickListener listener) {
        this.voitures = voitures;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VoitureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voiture, parent, false);
        return new VoitureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoitureViewHolder holder, int position) {
        Voiture voiture = voitures.get(position);

        holder.textMarqueModele.setText(voiture.getMarque() + " " + voiture.getModele());
        holder.textPrix.setText(voiture.getPrixParJour() + " €/jour");

        // Formatage des disponibilités
        String disponibilitesFormatted = formatDisponibilites(voiture.getDisponibilites());
        holder.textDisponibilites.setText("Disponibilités : " + disponibilitesFormatted);

        // Chargement de l'image avec Glide
        Glide.with(holder.itemView.getContext())
                .load(voiture.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageVoiture);

        holder.buttonConsulter.setOnClickListener(v -> listener.onItemClick(voiture));

        // Add OnClickListener for "View Avis" button
        holder.btnViewAvis.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(),AvisActivity.class);
            intent.putExtra("proprietaireId", Long.valueOf( voiture.getProprietaireId())); // Pass the proprietaireId
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return voitures.size();
    }

    // Méthode pour formater les disponibilités
    private String formatDisponibilites(List<Disponibilite> disponibilites) {
        if (disponibilites == null || disponibilites.isEmpty()) {
            return "Aucune";
        }

        StringBuilder disponibilitesBuilder = new StringBuilder();
        for (Disponibilite dispo : disponibilites) {
            disponibilitesBuilder.append("Du ")
                    .append(dispo.getDateDebutDisponibilite())
                    .append(" au ")
                    .append(dispo.getDateFinDisponibilite())
                    .append("\n");
        }

        return disponibilitesBuilder.toString().trim();
    }

    public interface OnItemClickListener {
        void onItemClick(Voiture voiture);
    }

    public static class VoitureViewHolder extends RecyclerView.ViewHolder {

        private TextView textMarqueModele, textPrix, textDisponibilites;
        private ImageView imageVoiture;
        private Button buttonConsulter;
        private Button btnViewAvis; // Declare the btnViewAvis button

        public VoitureViewHolder(@NonNull View itemView) {
            super(itemView);
            textMarqueModele = itemView.findViewById(R.id.textMarqueModele);
            textPrix = itemView.findViewById(R.id.textPrix);
            textDisponibilites = itemView.findViewById(R.id.textDisponibilites); // Add this field
            imageVoiture = itemView.findViewById(R.id.imageVoiture);
            buttonConsulter = itemView.findViewById(R.id.buttonConsulter);
            btnViewAvis = itemView.findViewById(R.id.btnViewAvis); // Initialize the button here
        }
    }
}
