package belohnungsKlassen;

public class Gegenstand {
    protected String bezeichnung;
    private double dropChance;
    private int wertInCt;

    public Gegenstand(String bezeichnung, int wertInCt, double dropChance){
        this.dropChance = dropChance;
        this.wertInCt = wertInCt;
        this.bezeichnung = bezeichnung;
    }

    public double getDropChance() {
        return dropChance;
    }

    public void setDropChance(double dropChance) {
        this.dropChance = dropChance;
    }

    public int getWertInCt() {
        return wertInCt;
    }

    public void setWertInCt(int wertInCt) {
        this.wertInCt = wertInCt;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }
}
