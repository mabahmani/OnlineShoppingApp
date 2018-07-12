package com.mab.onlineshopping;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mab.onlineshopping.Data.LoginUserController;
import com.mab.onlineshopping.Data.OnlineShoppingApi;
import com.mab.onlineshopping.Data.UserPreferencesManager;
import com.mab.onlineshopping.Model.TokenResponse;

public class SignInActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextInputEditText username;
    private TextInputEditText password;
    private AppCompatButton signIn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        findViews();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                login();
            }
        });
    }

    private void login(){
        OnlineShoppingApi.LoginUserCallBack loginUserCallBack = new OnlineShoppingApi.LoginUserCallBack() {
            @Override
            public void onResponse(boolean successful, String errorDescription, TokenResponse tokenResponse) {
                if(successful){
                    UserPreferencesManager.getInstance(getApplicationContext()).putAccessToken(tokenResponse.getAccessToken());
                    UserPreferencesManager.getInstance(getApplicationContext()).putUsername(username.getText().toString());
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(
                            new Intent("login")
                    );
                    finish();
                }
            }

            @Override
            public void onFailure(String cause) {
                Toast.makeText(getApplicationContext(),cause,Toast.LENGTH_LONG).show();
            }
        };

        LoginUserController loginUserController = new LoginUserController(loginUserCallBack);
        loginUserController.start(username.getText().toString(),password.getText().toString());
    }

    private void findViews() {
        username = findViewById(R.id.username_et);
        password = findViewById(R.id.password_et);
        signIn = findViewById(R.id.sign_in_button);
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progress_bar);
    }
}
