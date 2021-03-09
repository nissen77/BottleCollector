package com.example.bottlecollector;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class StatisikSpeicher {

    public static String getGegangeneMeterGesamt(Context cont){
        Context context = cont;
        SharedPreferences sharedPref = context.getSharedPreferences(cont.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int highScore = sharedPref.getInt(cont.getString(R.string.saved_gegangene_meter_gesamt),0);
        return highScore+"m";
    }

    public  static void setGeganeneMeterGesamt(Context cont, int strecke){
        SharedPreferences sharedPref = cont.getSharedPreferences(cont.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(cont.getString(R.string.saved_gegangene_meter_gesamt), strecke);
        editor.apply();
    }

    public static void setBesterTag(Context cont, int strecke, LocalDate tag){
        SharedPreferences sharedPref = cont.getSharedPreferences(cont.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if(strecke > getBesterTagMeter(cont)){
            editor.putInt(cont.getString(R.string.best_day_meters), strecke);
            editor.putString(cont.getString(R.string.best_day_date), tag.toString());
            editor.apply();
        }
    }

    public static String getBesterTag(Context cont){
        SharedPreferences sharedPref = cont.getSharedPreferences(cont.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return getBesterTagDatum(cont).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.getDefault()))+" "+getBesterTagMeter(cont)+"m";
    }

    public static String getBesteWoche(Context cont){
        SharedPreferences sharedPref = cont.getSharedPreferences(cont.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getString(cont.getString(R.string.best_week), "Noch kein Highscore!");
    }

    public static void setBesteWoche(Context cont, int strecke, int woche){
        SharedPreferences sharedPref = cont.getSharedPreferences(cont.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if(!sharedPref.contains(cont.getString(R.string.best_week))){
            editor.putString(cont.getString(R.string.best_week), "0 0");
            editor.apply();
            return;

        }
        if(strecke > Integer.parseInt(getBesteWoche(cont).split(" ")[1])){
            editor.putString(cont.getString(R.string.best_week), woche+" "+strecke);
            editor.apply();
        }
    }

    // Hilfs Funktionen
    private static LocalDate getBesterTagDatum(Context cont){
        SharedPreferences sharedPref = cont.getSharedPreferences(cont.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return LocalDate.parse(sharedPref.getString(cont.getString(R.string.best_day_date), LocalDate.now().toString()));
    }

    private static int getBesterTagMeter(Context cont){
        SharedPreferences sharedPref = cont.getSharedPreferences(cont.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return  sharedPref.getInt(cont.getString(R.string.best_day_meters), 0);
    }

}
