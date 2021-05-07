public class Funct {
    private final boolean isPrim;
    private final int number;
    private int distanceAI;
    private int distanceAB;
    private int distanceBI;

    public Funct(int funct, int ab,int bi) {
        this.isPrim = true;
        this.number = funct;
        this.distanceAB=ab;
        this.distanceBI=bi;

    }

    public Funct( int funct, int ai) {
        this.isPrim = false;
        this.number = funct;
        this.distanceAI=ai;

    }
    public int getDistanceAI() {
        return distanceAI;
    }



    public int getDistanceAB() {
        return distanceAB;
    }



    public int getDistanceBI() {
        return distanceBI;
    }

    public int getNumber() {
        return number;
    }

    public double value( double x) {
        if (isPrim) {
            return ((distanceAB + distanceBI) - x);
        } else {
            return distanceAI + x;
        }
    }





}
