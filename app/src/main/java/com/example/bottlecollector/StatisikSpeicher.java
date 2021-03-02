package com.example.bottlecollector;

import android.content.Context;
import android.content.SharedPreferences;

public class StatisikSpeicher {

    public static String getGegangeneKilometer(Context cont){
        Context context = cont;
        SharedPreferences sharedPref = context.getSharedPreferences(cont.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int highScore = sharedPref.getInt(cont.getString(R.string.saved_gegangene_kilometer),0);
        return highScore+"km";
    }

    public  static void  setGeganeneKilometer(Context cont, int strecke){
        SharedPreferences sharedPref = cont.getSharedPreferences(cont.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(cont.getString(R.string.saved_gegangene_kilometer), strecke);
        editor.apply();
    }

}
