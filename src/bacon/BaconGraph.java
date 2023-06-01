package bacon;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BaconGraph {
    //maps actor name to a list of their movies
    private Map<String, Node> nameToNode ;

    public BaconGraph(String fileName) {
        nameToNode = new HashMap<>();
        read(fileName);
    }

    public void read(String fileName) {
        Map<String, Node> movieToNode = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line ;
            Node actorNode = null;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("<a>")){
                    actorNode = new Node(line);
                    nameToNode.put(line, actorNode);
                } else {
                    Node movieNode = movieToNode.get(line);
                    if (movieNode == null){
                        movieNode = new Node(line);
                        movieToNode.put(line, movieNode);
                    }
                    actorNode.addConnection(movieNode);
                    movieNode.addConnection(actorNode);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Node> breadthFirstSearch(String goalName) throws IllegalArgumentException{

        LinkedList<Node> queue = new LinkedList<>();
        Map<Node, Node> visitedConnections = new HashMap<>();

        goalName = "<a>"+ goalName;
        if (!(nameToNode.containsKey(goalName))){
            throw new IllegalArgumentException("No such actor in our records!");
        }

        Node kevinB = nameToNode.get("<a>Bacon, Kevin (I)");
        queue.addFirst(kevinB);
        visitedConnections.put(kevinB, null);

        while (!queue.isEmpty()){
            Node currentNode = queue.pollFirst();
            if(currentNode.getName().equals(goalName)){
                return gatherPath(currentNode, visitedConnections);
            }
            for(Node n : currentNode.getConnections()){
                if(!(visitedConnections.containsKey(n))){
                    queue.add(n);
                    visitedConnections.put(n, currentNode);
                }
            }
        }
        return Collections.emptyList();
    }
    public List<Node> gatherPath(Node goalDestination, Map<Node, Node> visitedConnections){
        LinkedList<Node> path = new LinkedList<>();
        Node currentNode = goalDestination;
        while(currentNode != null){
            Node next = visitedConnections.get(currentNode); //gives parent node, closer to source
            path.addFirst(currentNode);
            currentNode = next;
        }
        return path;
    }
}


