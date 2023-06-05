package bacon;

import java.util.List;
import java.util.Scanner;

public class Program {

    private String askName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("? ");
        String name = scanner.nextLine();
        name = name.strip();
        return name;
    }

    private void run(String fileName) {
        BaconGraph baconGraph = new BaconGraph(fileName);
        System.out.println("Write the name of the actor you want to get the Bacon number of or press q to quit!");
        while (true) {
            String name = askName();
            if (name.equalsIgnoreCase("q") || name.equalsIgnoreCase("quit")) {
                break;
            }
            try {
                List<Node> path = baconGraph.findPathFromKevinB(name);//breadthFirstSearch(name);
                if (path.isEmpty()) {
                    System.out.println("Oops! That person has no connection to Kevin Bacon, please try another name.");
                } else {
                    int steps = path.size() / 2;
                    String stepSteps = steps > 1 ? "steps" : "step";
                    System.out.printf("\"%s\" is %d %s away from Kevin B. The path is %s.\n", name, steps, stepSteps, path.toString().replaceAll("[\\[\\]]", ""));
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Program p = new Program();
        p.run("moviedata.txt");
    }
}
