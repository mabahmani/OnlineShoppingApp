package com.mab.onlineshopping.Data;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferencesManager {
    private static UserPreferencesManager instance = null;
    private SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor = null;

    public  static UserPreferencesManager getInstance(Context context){
        if (instance == null){
            instance = new UserPreferencesManager(context);
        }
        return instance;
    }

    private UserPreferencesManager(Context context){
        sharedPreferences = context.getSharedPreferences("user_auth",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getUsername(){
        return sharedPreferences.getString("username",null);
    }
    public void putUsername(String username){
        editor.putString("username",username);
        editor.apply();
    }
    public String getAccessToken(){
        return sharedPreferences.getString("access_token",null);
    }

    public void putAccessToken(String accessToken){
        editor.putString("access_token",accessToken);
        editor.apply();
    }

    public void clear(){
        editor.clear();
        editor.apply();
    }
}
