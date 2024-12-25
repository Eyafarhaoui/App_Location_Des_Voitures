package com.example.eliteauto.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eliteauto.R;
import com.example.eliteauto.model.AvisProprietaire;
import java.util.List;


import androidx.recyclerview.widget.RecyclerView;

public class AvisAdapter extends RecyclerView.Adapter<AvisAdapter.AvisViewHolder> {
    private List<AvisProprietaire> avisList;

    public AvisAdapter(List<AvisProprietaire> avisList) {
        this.avisList = avisList;
    }

    @Override
    public AvisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_avis, parent, false);
        return new AvisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AvisViewHolder holder, int position) {
        AvisProprietaire avis = avisList.get(position);
        holder.bind(avis);
    }

    @Override
    public int getItemCount() {
        return avisList.size();
    }

    public static class AvisViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewAvis;

        public AvisViewHolder(View itemView) {
            super(itemView);
            textViewAvis = itemView.findViewById(R.id.textAvis);
        }

        public void bind(AvisProprietaire avis) {
            textViewAvis.setText(avis.getContenu()); // Display review comment
        }
    }
}

