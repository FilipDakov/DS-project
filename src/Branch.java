public class Branch {
    private final int pointA;
    private final int pointB;
    private final int maxDistance;
    private final int abDistance;

    Branch(int a,int b, int maxDistance,int ABDistance){
        this.pointA= a;
        this.pointB=b;
        this.maxDistance=maxDistance;
        this.abDistance =ABDistance;
    }

    public int getPointA() {
        return pointA;
    }


    public int getPointB() {
        return pointB;
    }


    public int getDistance() {
        return maxDistance;
    }


    public int getAbDistance() {
        return abDistance;
    }

}
