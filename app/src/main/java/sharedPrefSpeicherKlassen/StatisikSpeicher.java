package sharedPrefSpeicherKlassen;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.bottlecollector.R;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class StatisikSpeicher {

    SharedPreferences sharedPref;
    Context cont;
    NumberFormat nf = NumberFormat.getInstance();

    public StatisikSpeicher(Context cont){
        sharedPref = cont.getSharedPreferences(cont.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        this.cont = cont;
    }

    public void initSharedPref(){
        if(!sharedPref.contains(cont.getString(R.string.version_id)) || sharedPref.getInt(cont.getString(R.string.version_id), 0) != 1){
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(cont.getString(R.string.version_id), 1);

            if(!sharedPref.contains(cont.getString(R.string.beste_woche)) || !sharedPref.contains(cont.getString(R.string.beste_woche_meter))){
                editor.putString(cont.getString(R.string.beste_woche), "Jahr:0 Woche:0");
                editor.putInt(cont.getString(R.string.beste_woche_meter),0);
            }

            if(!sharedPref.contains(cont.getString(R.string.aktueller_tag_meter))){
                editor.putInt(cont.getString(R.string.aktueller_tag_meter), 0);
            }

            if(!sharedPref.contains(cont.getString(R.string.aktuelle_woche))){
                editor.putInt(cont.getString(R.string.aktuelle_woche), 0);
            }

            editor.apply();
        }
    }

    // Setter für die Statistikwerte
    public void setGeganeneMeterGesamt(int strecke){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(cont.getString(R.string.saved_gegangene_meter_gesamt), strecke);
        editor.apply();
    }

    public void setBesterTag(int strecke, LocalDate tag){
        SharedPreferences.Editor editor = sharedPref.edit();
        if(strecke > getBesterTagMeter()){
            editor.putInt(cont.getString(R.string.bester_dag_meter), strecke);
            editor.putString(cont.getString(R.string.bester_tag_datum), tag.toString());
            editor.apply();
        }
    }

    public void setBesteWoche(int strecke, int woche, int jahr){
        SharedPreferences.Editor editor = sharedPref.edit();
        if(strecke > getBesteWocheMeter()){
            editor.putString(cont.getString(R.string.beste_woche), "Jahr:"+jahr+" Woche:"+woche);
            editor.putInt(cont.getString(R.string.beste_woche_meter),strecke);
            editor.apply();
        }
    }

    public void setAktuellerTag(int strecke){
        SharedPreferences.Editor editor = sharedPref.edit();
        if(LocalDate.parse(getAktuellerTagDatum()) != LocalDate.now()){
            editor.putInt(cont.getString(R.string.aktueller_tag_meter), 0);
            editor.putString(cont.getString(R.string.aktueller_tag_datum), LocalDate.now().toString());
        }
        editor.putInt(cont.getString(R.string.aktueller_tag_meter), strecke);
        editor.apply();
    }

    public void setAktuelleWoche(int strecke){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(cont.getString(R.string.aktuelle_woche), strecke);
        editor.apply();
    }

    // Getter für die Statistikwerte
    public String getGegangeneMeterGesamt(){
        int highScore = sharedPref.getInt(cont.getString(R.string.saved_gegangene_meter_gesamt),0);
        return nf.format(highScore)+"m";
    }

    public String getBesterTag(){
        return getBesterTagDatum().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.getDefault()))+ System.lineSeparator() +nf.format(getBesterTagMeter())+"m";
    }

    public String getBesteWoche(){
        return sharedPref.getString(cont.getString(R.string.beste_woche), "Noch kein Highscore!") + System.lineSeparator() +nf.format(sharedPref.getInt(cont.getString(R.string.beste_woche_meter),0))+"m";
    }

    public String getAktuellerTag(){
        return nf.format(sharedPref.getInt(cont.getString(R.string.aktueller_tag_meter), 0))+"m";
    }

    public String getAktuelleWoche(){
        return nf.format(sharedPref.getInt(cont.getString(R.string.aktuelle_woche), 0))+"m";
    }

    // Hilfs Funktionen
    private LocalDate getBesterTagDatum(){
        return LocalDate.parse(sharedPref.getString(cont.getString(R.string.bester_tag_datum), LocalDate.now().toString()));
    }

    private int getBesterTagMeter(){
        return  sharedPref.getInt(cont.getString(R.string.bester_dag_meter), 0);
    }

    private int getBesteWocheMeter(){
        return sharedPref.getInt(cont.getString(R.string.beste_woche_meter),0);
    }

    private String getAktuellerTagDatum(){
        return sharedPref.getString(cont.getString(R.string.aktueller_tag_datum), LocalDate.now().toString());
    }

}
