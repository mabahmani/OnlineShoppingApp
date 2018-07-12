package com.mab.onlineshopping;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Toast;

import com.mab.onlineshopping.Data.UserPreferencesManager;
import com.mab.onlineshopping.Model.Product;

public class MainActivity extends AppCompatActivity {

    private  AppCompatButton signIn;
    private  AppCompatButton signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        if (UserPreferencesManager.getInstance(getApplicationContext()).getAccessToken() == null) {

            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, SignInActivity.class);
                    startActivity(i);
                }
            });
            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, SignUpActivity.class);
                    startActivity(i);
                }
            });

            LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(
                    broadcastReceiver, new IntentFilter("login")
            );
        }

        else {
            Intent i = new Intent(MainActivity.this, ProductsActivity.class);
            startActivity(i);
            finish();
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent i = new Intent(MainActivity.this,ProductsActivity.class);
            startActivity(i);
            finish();
        }
    };

    private void findViews(){

        signIn = findViewById(R.id.sign_in_button);
        signUp = findViewById(R.id.sign_up_button);
    }
}
