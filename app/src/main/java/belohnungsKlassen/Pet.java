package belohnungsKlassen;

public class Pet {
    private String name;
    private double increaseDropChance;
    private int level;
    private int levelOld;
    private boolean active;
    private double mileStone;
    private double dropIncrease;

    public Pet(String name, double increaseDropChance){
        this.name = name;
        this.increaseDropChance = increaseDropChance;
        this.level = 1;
        this.active = false;
        this.levelOld = this.level;
    }

    public double getIncreaseDropChance() {
        if(this.level != this.levelOld){
            this.levelOld = this.level;
            increaseDropChance();
        }
        return increaseDropChance / 100 + 1;
    }

    public void setIncreaseDropChance(double increaseDropChance) {
        this.increaseDropChance = increaseDropChance;
    }

    public void increaseDropChance(){
        if(getLevel()%10 == 0){
            setIncreaseDropChance(getIncreaseDropChance() * this.mileStone);
        }else{
            setIncreaseDropChance(getIncreaseDropChance() + this.dropIncrease);
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if(level <= 30){
            this.level = level;
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
