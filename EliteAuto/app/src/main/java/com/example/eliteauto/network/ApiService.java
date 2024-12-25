package com.example.eliteauto.network;

import com.example.eliteauto.model.AvisProprietaire;
import com.example.eliteauto.model.Client;
import com.example.eliteauto.model.Voiture;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("clients/login")
    Call<Client> loginOrRegister(@Body Client client);

    @GET("voitures")
    Call<List<Voiture>> getAllVoitures();

    @GET("avis/avis-proprietaire/{proprietaireId}")
    Call<List<AvisProprietaire>> getAvisByProprietaireId(@Path("proprietaireId") Long proprietaireId);
}
