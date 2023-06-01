package bacon;
import java.util.List;
import java.util.Scanner;

public class Program {

    private BaconGraph baconGraph;

    private String askName(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("? ");
        String name = scanner.nextLine();
        return name;
    }

    private void run(String fileName){
        String name = "";
        baconGraph = new BaconGraph(fileName);
        while (true){
            name = askName();
            if(name.equalsIgnoreCase("q") || name.equalsIgnoreCase("quit")) {
                break;
            }
            try {
                List<Node> path = baconGraph.breadthFirstSearch(name);
                if (path.isEmpty()){
                    System.out.println("Oops! That person has no connection to Kevin Bacon, please try another name.");
                }else {
                    int steps = path.size()/2;
                    System.out.printf("\"%s\" is %d steps away from Kevin B. The path is %s.\n", name, steps, path.toString().replaceAll("\\[|\\]", ""));
                }
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Program p = new Program();
        p.run("moviedata.txt");
    }
}
