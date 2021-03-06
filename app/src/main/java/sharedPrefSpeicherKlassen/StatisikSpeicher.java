package sharedPrefSpeicherKlassen;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.bottlecollector.R;

import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class StatisikSpeicher {

    private static  StatisikSpeicher instance;
    private SharedPreferences sharedPref;
    private Context cont;
    private NumberFormat nf = NumberFormat.getInstance();

    private StatisikSpeicher(Context cont){
        sharedPref = cont.getSharedPreferences(cont.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        this.cont = cont;
    }

    public static StatisikSpeicher getInstance(Context cont){
        if(instance == null){
            instance = new StatisikSpeicher(cont);
        }
        return instance;
    }

    public void initSharedPref(){
        if(!sharedPref.contains(cont.getString(R.string.version_id)) || sharedPref.getInt(cont.getString(R.string.version_id), 0) != 1){
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(cont.getString(R.string.version_id), 1);

            LocalDate date = LocalDate.now();
            WeekFields weekFields = WeekFields.ISO;
            int woche =  date.get(weekFields.weekOfWeekBasedYear());

            if(!sharedPref.contains(cont.getString(R.string.beste_woche)) || !sharedPref.contains(cont.getString(R.string.beste_woche_meter))){
                editor.putString(cont.getString(R.string.beste_woche), "Jahr:"+LocalDate.now().getYear()+" Woche:"+woche);
                editor.putInt(cont.getString(R.string.beste_woche_meter),0);
            }

            if(!sharedPref.contains(cont.getString(R.string.aktueller_tag_meter)) || !sharedPref.contains(cont.getString(R.string.aktueller_tag_datum))){
                editor.putInt(cont.getString(R.string.aktueller_tag_meter), 0);
                editor.putString(cont.getString(R.string.aktueller_tag_datum), LocalDate.now().toString());
            }

            if(!sharedPref.contains(cont.getString(R.string.aktuelle_woche_meter)) || !sharedPref.contains(cont.getString(R.string.aktuelle_woche))){
                editor.putString(cont.getString(R.string.aktuelle_woche_meter), "0 0 0 0 0 0 0");
                editor.putInt(cont.getString(R.string.aktuelle_woche), woche);
            }

            if(!sharedPref.contains(cont.getString(R.string.gegangene_meter_gesamt))){
                editor.putInt(cont.getString(R.string.gegangene_meter_gesamt), 0);
            }

            editor.apply();
        }
    }

    public void checkDate(){
        SharedPreferences.Editor editor = sharedPref.edit();
        LocalDate date = LocalDate.now();
        WeekFields weekFields = WeekFields.ISO;
        int woche =  date.get(weekFields.weekOfWeekBasedYear());

        // prüft ob eine neue Woche begonnen hat
        if(woche != sharedPref.getInt(cont.getString(R.string.aktuelle_woche), 0)
                || LocalDate.parse(getAktuellerTagDatum()).getYear() != date.getYear()){
            setBesteWoche(getAktuelleWocheMeter(), sharedPref.getInt(cont.getString(R.string.aktuelle_woche), 0));
            editor.putString(cont.getString(R.string.aktuelle_woche_meter), "0 0 0 0 0 0 0");
            editor.putInt(cont.getString(R.string.aktuelle_woche), woche);
            editor.apply();
        }
        // prüft ob ein neuer Tag begonnen hat
        if(!LocalDate.parse(getAktuellerTagDatum()).isEqual(LocalDate.now())){
            setBesterTag(sharedPref.getInt(cont.getString(R.string.aktueller_tag_meter), 0), LocalDate.parse(getAktuellerTagDatum()));
            editor.putInt(cont.getString(R.string.aktueller_tag_meter), 0);
            editor.putString(cont.getString(R.string.aktueller_tag_datum), LocalDate.now().toString());
            editor.apply();
        }
    }

    // Setter für die Statistikwerte
    public void setGeganeneMeterGesamt(int strecke){
        SharedPreferences.Editor editor = sharedPref.edit();
        strecke += sharedPref.getInt(cont.getString(R.string.gegangene_meter_gesamt), 0);
        editor.putInt(cont.getString(R.string.gegangene_meter_gesamt), strecke);
        editor.apply();
    }

    public void setAktuellerTag(int strecke){
        SharedPreferences.Editor editor = sharedPref.edit();
        checkDate();
        int sum = sharedPref.getInt(cont.getString(R.string.aktueller_tag_meter), 0) + strecke;
        editor.putInt(cont.getString(R.string.aktueller_tag_meter), sum);
        editor.apply();
    }

    public void setAktuelleWoche(int strecke){
        LocalDate date = LocalDate.now();
        WeekFields weekFields = WeekFields.ISO;
        int woche =  date.get(weekFields.weekOfWeekBasedYear());

        SharedPreferences.Editor editor = sharedPref.edit();

        checkDate();

        // holt den wert des aktuellen tages und addiert die strecke drauf
        // speichert die daten wieder
        int tag = date.getDayOfWeek().getValue();
        String [] val = sharedPref.getString(cont.getString(R.string.aktuelle_woche_meter), "0").split(" ");
        int aktTag = Integer.parseInt(val[tag-1]) + strecke;
        val[tag-1] = Integer.toString(aktTag);

        StringBuilder sb = new StringBuilder();

        for(String s : val){
            sb.append(s);
            sb.append(" ");
        }

        editor.putString(cont.getString(R.string.aktuelle_woche_meter), sb.toString());
        editor.apply();

    }

    private void setBesterTag(int strecke, LocalDate tag){
        SharedPreferences.Editor editor = sharedPref.edit();
        if(strecke > getBesterTagMeter()){
            editor.putInt(cont.getString(R.string.bester_dag_meter), strecke);
            editor.putString(cont.getString(R.string.bester_tag_datum), tag.toString());
            editor.apply();
        }
    }

    private void setBesteWoche(int strecke, int woche){
        SharedPreferences.Editor editor = sharedPref.edit();
        if(strecke > getBesteWocheMeter()){

            editor.putString(cont.getString(R.string.beste_woche), "Jahr:"+LocalDate.now().getYear()+" Woche:"+woche);
            editor.putInt(cont.getString(R.string.beste_woche_meter),strecke);
            editor.apply();
        }
    }

    // Liefert die Werte für die Statistik Seite als Formatierte String zurück
    public String getGegangeneMeterGesamtFS(){
        int highScore = sharedPref.getInt(cont.getString(R.string.gegangene_meter_gesamt),0);
        return nf.format(highScore)+"m";
    }

    public String getBesterTagFS(){
        return getBesterTagDatum().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.getDefault()))+ System.lineSeparator() +nf.format(getBesterTagMeter())+"m";
    }

    public String getBesteWocheFS(){
        return sharedPref.getString(cont.getString(R.string.beste_woche), "Noch kein Highscore!") + System.lineSeparator() +nf.format(sharedPref.getInt(cont.getString(R.string.beste_woche_meter),0))+"m";
    }

    public String getAktuellerTagFS(){
        return nf.format(sharedPref.getInt(cont.getString(R.string.aktueller_tag_meter), 0))+"m";
    }

    public String getAktuelleWocheFS(){
        return nf.format(getAktuelleWocheMeter())+"m";
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

    private int getAktuelleWocheMeter(){
        int sum = 0;
        String [] erg = sharedPref.getString(cont.getString(R.string.aktuelle_woche_meter), "0").split(" ");
        for (String s : erg){
            sum += Integer.parseInt(s);
        }
        return sum;
    }

}
