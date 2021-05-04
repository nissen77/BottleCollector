package sharedPrefSpeicherKlassen;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.bottlecollector.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import belohnungsKlassen.Belohnung;

public class GegenstandSpeicher {

    SharedPreferences sharedPref;
    Context cont;
    NumberFormat nf = NumberFormat.getInstance();

    public GegenstandSpeicher(Context cont){
        this.sharedPref = cont.getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
        this.cont = cont;
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
        String inventar = sharedPref.getString(cont.getString(R.string.inventar), "Fehler!!!!");
        if (inventar.equals("Fehelr!!!!")){
            erg.add(inventar);
            return erg;
        }

        inventar = inventar.substring(1,inventar.length() - 1).trim();
        String [] inventarArray = inventar.split(",");

        for (String s : inventarArray){
            erg.add(s);
        }

        return erg;
    }

}
