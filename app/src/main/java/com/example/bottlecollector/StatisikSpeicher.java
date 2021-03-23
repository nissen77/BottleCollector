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

    SharedPreferences sharedPref;
    Context cont;

    public StatisikSpeicher(Context cont){
        sharedPref = cont.getSharedPreferences(cont.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        this.cont = cont;
    }

    public  StatisikSpeicher(){
    }

    // Setter für die Statistik werte
    public void setGeganeneMeterGesamt(int strecke){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(cont.getString(R.string.saved_gegangene_meter_gesamt), strecke);
        editor.apply();
    }

    public void setBesterTag(int strecke, LocalDate tag){
        SharedPreferences.Editor editor = sharedPref.edit();
        if(strecke > getBesterTagMeter()){
            editor.putInt(cont.getString(R.string.best_day_meters), strecke);
            editor.putString(cont.getString(R.string.best_day_date), tag.toString());
            editor.apply();
        }
    }

    public void setBesteWoche(int strecke, int woche, int jahr){
        SharedPreferences.Editor editor = sharedPref.edit();
        if(!sharedPref.contains(cont.getString(R.string.best_week))){
            editor.putString(cont.getString(R.string.best_week), "Jahr:0 Woche:0 0m");
            editor.apply();
            return;

        }
        if(strecke > Integer.parseInt(getBesteWoche().split(" ")[2].split("m")[0])){
            editor.putString(cont.getString(R.string.best_week), "Jahr:"+jahr+" Woche:"+woche+" "+strecke+"m");
            editor.apply();
        }
    }

    public void setAktuellerTag(int strecke){
        SharedPreferences.Editor editor = sharedPref.edit();
        if(!sharedPref.contains(cont.getString(R.string.aktueller_tag))){
            editor.putString(cont.getString(R.string.aktueller_tag), "0m");
            editor.apply();
            return;
        }
        editor.putString(cont.getString(R.string.aktueller_tag), strecke+"m");
        editor.apply();
    }

    public void setAktuelleWoche(int strecke){
        SharedPreferences.Editor editor = sharedPref.edit();
        if(!sharedPref.contains(cont.getString(R.string.aktuelle_woche))){
            editor.putString(cont.getString(R.string.aktuelle_woche), "0m");
            editor.apply();
            return;
        }
        editor.putString(cont.getString(R.string.aktuelle_woche), strecke+"m");
        editor.apply();
    }

    // Getter für die Statistik werte
    public String getGegangeneMeterGesamt(){
        int highScore = sharedPref.getInt(cont.getString(R.string.saved_gegangene_meter_gesamt),0);
        return highScore+"m";
    }

    public String getBesterTag(){
        return getBesterTagDatum().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.GERMAN))+"\n "+getBesterTagMeter()+"m";
    }

    public String getBesteWoche(){
        return sharedPref.getString(cont.getString(R.string.best_week), "Noch kein Highscore!");
    }

    public String getAktuellerTag(){
        return sharedPref.getString(cont.getString(R.string.aktueller_tag), "0m");
    }

    public String getAktuelleWoche(){
        return sharedPref.getString(cont.getString(R.string.aktuelle_woche), "0m");
    }

    // Hilfs Funktionen
    private LocalDate getBesterTagDatum(){
        return LocalDate.parse(sharedPref.getString(cont.getString(R.string.best_day_date), LocalDate.now().toString()));
    }

    private int getBesterTagMeter(){
        return  sharedPref.getInt(cont.getString(R.string.best_day_meters), 0);
    }

}
