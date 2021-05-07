import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Graph {

    // Adjacency list representation of the
    // connected edges
    private final List<List<Node>> adj;

    //closest to each node
    private final int[] closestOne;
    private final int[] closestTwo;
    private final int[] closestThree;
    private final int[] closestFour;
    private final int[] closestFive;
    private final List<int[]> closest;

    //best distance and branch
    private double distanceX;
    private double lowestY;
    private Branch bestBranch;

    Graph(int[] weights) {
        adj = new ArrayList<>();
        this.distanceX = 0;
        this.lowestY = 1000;
        // Initialize list for every node
        for (int i = 0; i < 6; i++) {
            List<Node> item = new ArrayList<>();
            adj.add(item);
        }

        // Inputs for the DPQ graph
        adj.get(0).add(new Node(1, weights[0]));
        adj.get(0).add(new Node(4, weights[1]));

        adj.get(1).add(new Node(2, weights[2]));
        adj.get(1).add(new Node(5, weights[3]));
        adj.get(1).add(new Node(0, weights[0]));

        adj.get(2).add(new Node(3, weights[4]));
        adj.get(2).add(new Node(1, weights[2]));

        adj.get(3).add(new Node(4, weights[5]));
        adj.get(3).add(new Node(2, weights[4]));

        adj.get(4).add(new Node(5, weights[6]));
        adj.get(4).add(new Node(0, weights[1]));
        adj.get(4).add(new Node(3, weights[5]));

        adj.get(5).add(new Node(4, weights[6]));
        adj.get(5).add(new Node(1, weights[3]));


        closestOne = this.getShortestPath(0).clone();

        closestTwo = this.getShortestPath(1).clone();
        closestThree = this.getShortestPath(2).clone();
        closestFour = this.getShortestPath(3).clone();
        closestFive = this.getShortestPath(4).clone();
        int[] closestSix = this.getShortestPath(5).clone();

        closest = Arrays.asList(closestOne, closestTwo, closestThree, closestFour, closestFive, closestSix);

    }


    //create branches,
    //check for branches to exclude
    //check for best position
    public void checkForBranchToExclude() {
        Branch branchOneTwo = new Branch(1, 2, getBranchDistance(0, 1), closestOne[1]);
        Branch branchOneFive = new Branch(1, 5, getBranchDistance(0, 4), closestOne[4]);
        Branch branchTwoThree = new Branch(2, 3, getBranchDistance(1, 2), closestTwo[2]);
        Branch branchTwoSix = new Branch(2, 6, getBranchDistance(1, 5), closestTwo[5]);
        Branch branchThreeFour = new Branch(3, 4, getBranchDistance(2, 3), closestThree[3]);
        Branch branchFourFive = new Branch(4, 5, getBranchDistance(3, 4), closestFour[4]);
        Branch branchFiveSix = new Branch(5, 6, getBranchDistance(4, 5), closestFive[5]);


        List<Branch> branches = Arrays.asList(branchOneTwo, branchOneFive, branchTwoThree, branchTwoSix, branchThreeFour, branchFourFive, branchFiveSix);
        List<Double> list = Arrays.asList(branchOneTwo.getDistance() + (closestOne[1] / 2.0), branchOneFive.getDistance() + (closestOne[4] / 2.0)
                , branchTwoThree.getDistance() + (closestTwo[2] / 2.0),
                branchTwoSix.getDistance() + (closestTwo[5] / 2.0), branchThreeFour.getDistance() + (closestThree[3] / 2.0)
                , branchFourFive.getDistance() + (closestFour[4] / 2.0), branchFiveSix.getDistance() + (closestFive[5] / 2.0));

        double min = list.stream().min(Double::compareTo).get();
        int i = 7;
        for (Branch branch : branches) {
            if (branch.getDistance() < min) {
                getMinValue(branch);
                i--;
            }
        }

        System.out.printf("%d ребра са изключени от по-нататъчно изследване.%n", i);
    }


    public void printResult() {
        System.out.printf("Най-доброто място е върху реброто <%d,%d>, на %.2f километра от връх %d"
                , bestBranch.getPointA(), bestBranch.getPointB(), distanceX, bestBranch.getPointA());
    }

    private void getMinValue(Branch branch) {
        List<Funct> functions = new ArrayList<>();
        List<Funct> functionsPrim = new ArrayList<>();
        boolean prim = false;
        for (int i = 1; i < 7; i++) {
            functions.add(new Funct(i, closest.get(i - 1)[branch.getPointA() - 1]));
        }
        for (int i = 1; i < 7; i++) {
            functionsPrim.add(new Funct(i, closest.get(branch.getPointA() - 1)[branch.getPointB() - 1],
                    closest.get(i - 1)[branch.getPointB() - 1]));
        }


        List<Funct> orderFunction = functions.stream().sorted((e1, e2) -> e2.getDistanceAI() - e1.getDistanceAI()).collect(Collectors.toList());

        int tempF = orderFunction.get(0).getNumber();

        if ((orderFunction.get(0).getDistanceAI() == orderFunction.get(1).getDistanceAI())) {
            if ((orderFunction.get(0).getDistanceAI() == ((functionsPrim.get(orderFunction.get(0).getNumber() - 1).getDistanceAB())
                    + functionsPrim.get(orderFunction.get(0).getNumber() - 1).getDistanceBI()))) {
                prim = true;
                tempF = functionsPrim.get(orderFunction.get(0).getNumber() - 1).getNumber();

            }
            if ((orderFunction.get(0).getDistanceAI() == ((functionsPrim.get(orderFunction.get(1).getNumber() - 1).getDistanceAB())
                    + functionsPrim.get(orderFunction.get(1).getNumber() - 1).getDistanceBI()))) {
                prim = true;
                tempF = functionsPrim.get(orderFunction.get(1).getNumber() - 1).getNumber();
            }
        } else if (orderFunction.get(0).getDistanceAI() == (functionsPrim.get(orderFunction.get(0).getNumber() - 1).getDistanceAB() + functionsPrim.get(orderFunction.get(0).getNumber() - 1).getDistanceBI())) {
            prim = true;
            tempF = functionsPrim.get(orderFunction.get(0).getNumber() - 1).getNumber();
        }
        double tempLow = orderFunction.get(0).value(0);
        double tempX = 0;
        for (double x = 0.5; x <= branch.getAbDistance(); x += 0.5) {

            if (!prim) {
                if (functionsPrim.get(tempF - 1).value(x) == functions.get(tempF - 1).value(x)) {
                    prim = true;

                    if (tempLow > functionsPrim.get(tempF - 1).value(x)) {
                        tempLow = functionsPrim.get(tempF - 1).value(x);
                        tempX = x;
                    }
                }
            } else {
                for (Funct f : functions) {
                    if (f.value(x) == functionsPrim.get(tempF - 1).value(x)) {
                        prim = false;
                        if (functionsPrim.get(tempF - 1).value(x) < tempLow) {
                            tempLow = functionsPrim.get(tempF - 1).value(x);
                            tempX = x;
                        }
                        tempF = f.getNumber();
                        break;
                    }
                }

                if (prim && (tempLow > functionsPrim.get(tempF - 1).value(x))) {
                    tempLow = functionsPrim.get(tempF - 1).value(x);
                    tempX = x;
                }
            }

        }

        if (tempLow < lowestY) {
            lowestY = tempLow;
            distanceX = tempX;
            bestBranch = branch;
        }

    }


    private int findMaxFromArray(int[] arr) {
        return Arrays.stream(arr).max().getAsInt();
    }

    private int getBranchDistance(int a, int b) {
        Dijkstra dpq = new Dijkstra(6);
        Dijkstra dpq1 = new Dijkstra(6);
        dpq.dijkstra(adj, a);
        int[] closestPathA = dpq.dist;

        dpq1.dijkstra(adj, b);
        int[] closestPathB = dpq1.dist;
        for (int i = 0; i < closestPathA.length; i++) {
            if (closestPathA[i] > closestPathB[i]) {
                closestPathA[i] = closestPathB[i];
            }

        }
        return findMaxFromArray(closestPathA);
    }

    private int[] getShortestPath(int from) {
        Dijkstra dpq = new Dijkstra(6);
        dpq.dijkstra(this.adj, from);
        return dpq.dist;
    }


}
