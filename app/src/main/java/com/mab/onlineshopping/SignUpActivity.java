package com.mab.onlineshopping;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mab.onlineshopping.Data.LoginUserController;
import com.mab.onlineshopping.Data.OnlineShoppingApi;
import com.mab.onlineshopping.Data.RegisterUserController;
import com.mab.onlineshopping.Data.UserPreferencesManager;
import com.mab.onlineshopping.Model.TokenResponse;
import com.mab.onlineshopping.Model.User;
import com.mab.onlineshopping.Model.UserResponse;

public class SignUpActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextInputEditText email;
    private TextInputEditText username;
    private TextInputEditText password;
    private AppCompatButton signUp;
    private ProgressBar progressBar;
    private ImageView bgImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViews();
        bgImg.setImageDrawable(getApplication().getResources().getDrawable(R.drawable.login_bg));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                register();
            }
        });
    }

    private void register() {
        final User user = new User();
        user.setEmail(email.getText().toString());
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());

        OnlineShoppingApi.RegisterUserCallBack registerUserCallBack = new OnlineShoppingApi.RegisterUserCallBack() {
            @Override
            public void onResponse(boolean successful, String errorDescription, UserResponse userResponse) {
                if(successful){
                    login(user);
                }
            }

            @Override
            public void onFailure(String cause) {
                Toast.makeText(getApplicationContext(),cause,Toast.LENGTH_LONG).show();
            }
        };

        RegisterUserController registerUserController = new RegisterUserController(registerUserCallBack);
        registerUserController.start(user);
    }

    private void login(final User user) {
        OnlineShoppingApi.LoginUserCallBack loginUserCallBack = new OnlineShoppingApi.LoginUserCallBack() {
            @Override
            public void onResponse(boolean successful, String errorDescription, TokenResponse tokenResponse) {
                if(successful){
                    UserPreferencesManager.getInstance(getApplicationContext()).putAccessToken(tokenResponse.getAccessToken());
                    UserPreferencesManager.getInstance(getApplicationContext()).putUsername(user.getUsername());
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
        loginUserController.start(user.getUsername(),user.getPassword());
    }

    private void findViews() {
        toolbar = findViewById(R.id.toolbar);
        email = findViewById(R.id.email_et);
        username = findViewById(R.id.username_et);
        password = findViewById(R.id.password_et);
        signUp = findViewById(R.id.sign_up_button);
        progressBar = findViewById(R.id.progress_bar);
        bgImg = findViewById(R.id.bg_img);
    }
}
