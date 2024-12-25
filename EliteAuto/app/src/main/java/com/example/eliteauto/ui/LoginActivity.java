package com.example.eliteauto.ui;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eliteauto.R;
import com.example.eliteauto.model.Client;
import com.example.eliteauto.network.ApiClient;
import com.example.eliteauto.network.ApiService;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setPermissions("email", "public_profile");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                fetchEmailFromFacebook(accessToken);
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Login annulé", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "Erreur de login : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchEmailFromFacebook(AccessToken accessToken) {
        new GraphRequest(
                accessToken,
                "/me?fields=email",
                null,
                HttpMethod.GET,
                response -> {
                    try {
                        JSONObject jsonObject = response.getJSONObject();
                        String email = jsonObject.getString("email");
                        Log.d("FacebookEmail", "Email: " + email);

                        // Envoyer l'email au backend pour connecter ou inscrire l'utilisateur
                        loginOrRegisterClient(email);
                    } catch (Exception e) {
                        Log.e("FacebookEmail", "Erreur lors de la récupération de l'email: " + e.getMessage());
                        Toast.makeText(LoginActivity.this, "Erreur lors de la récupération de l'email.", Toast.LENGTH_SHORT).show();
                    }
                }
        ).executeAsync();
    }

    private void loginOrRegisterClient(String email) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Client client = new Client(email);

        // Appel à l'API pour connecter ou inscrire l'utilisateur
        Call<Client> call = apiService.loginOrRegister(client);
        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if (response.isSuccessful()) {
                    Client clientResponse = response.body();
                    Log.d("BackendResponse", "Client connecté ou inscrit: " + clientResponse.getEmail());
                    // Si la connexion ou l'inscription réussie, vous pouvez rediriger vers l'écran principal
                    Intent intent = new Intent(LoginActivity.this,DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("BackendResponse", "Erreur: " + response.code());
                    Toast.makeText(LoginActivity.this, "Erreur de connexion ou d'inscription", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                Log.e("BackendRequest", "Erreur lors de l'appel API: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Erreur réseau", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
