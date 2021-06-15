package belohnungsKlassen;

import com.example.bottlecollector.ListActivity;

import java.util.ArrayList;
import java.util.List;

import belohnungsKlassen.Gegenstand;
import belohnungsKlassen.Pet;

public class Belohnung {
    static List<Gegenstand> items = new ArrayList(){{
        add(new Gegenstand("Glas Flasche", 8, 40));
        add(new Gegenstand("PET Flasche", 15, 30));
        add(new Gegenstand("Dose", 25, 3));
    }};
    static List<Pet> pets = new ArrayList(){{
        add(new Pet("Hund", 1.5));
        add(new Pet("Katze", 2));
        add(new Pet("Nashorn", 12));
    }};

    public static List<String> belohnungeng(double entfernung, final double belohnungsWert){
        double bonusChance = 1.0;
        for(Pet p : pets){
            if(p.isActive()){
                bonusChance = p.getIncreaseDropChance();
            }
        }

        List<String> beute = new ArrayList<>();
        for(int i = 0; i < entfernung; i+=belohnungsWert) {
            for (Gegenstand g : items) {
                if (g.getDropChance() * bonusChance  >= Math.random() * 100) {
                    beute.add(g.getBezeichnung());
                    ListActivity.wert += g.getWertInCt();
                }
            }
        }
        return beute;
    }
}
