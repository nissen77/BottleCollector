package sharedPrefSpeicherKlassen;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.bottlecollector.ListActivity;
import com.example.bottlecollector.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import belohnungsKlassen.Belohnung;
import belohnungsKlassen.Gegenstand;

public class GegenstandSpeicher {

    private  static GegenstandSpeicher instance;
    private SharedPreferences sharedPref;
    private Context cont;
    private NumberFormat nf = NumberFormat.getInstance();

    private GegenstandSpeicher(Context cont){
        this.sharedPref = cont.getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
        this.cont = cont;
    }

    public static GegenstandSpeicher getInstance(Context cont){
        if(instance == null){
            instance = new GegenstandSpeicher(cont);
        }
        return instance;
    }

    public void speicherDaten(List<String> gegenstaende){
        if(sharedPref.contains(cont.getString(R.string.inventar))){
            gegenstaende.addAll(ladeDaten());
        }
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(cont.getString(R.string.inventar), gegenstaende.toString());
        editor.apply();
    }

    public List<String> ladeDaten(){
        List<String> erg = new ArrayList<>();
        String inventar = sharedPref.getString(cont.getString(R.string.inventar), " Noch keine Flaschen gesammelt:(");
        if (inventar.equals(" Noch keine Flaschen gesammelt:(")){
            erg.add(inventar);
            return erg;
        }

        inventar = inventar.substring(1,inventar.length() - 1);
        String [] inventarArray = inventar.split(",");

        for (String s : inventarArray){
            erg.add(s.trim());
        }

        return erg;
    }

    public void speicherGeld(int val){
        if(sharedPref.contains(cont.getString(R.string.geldGesamt))){
            val += ladeGeld();
        }
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(cont.getString(R.string.geldGesamt), val);
        editor.apply();
    }

    public int ladeGeld(){
        return sharedPref.getInt(cont.getString(R.string.geldGesamt), 0);
    }

}
