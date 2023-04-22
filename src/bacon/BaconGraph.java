package bacon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BaconGraph {

    //private Map<T, List<String>> graph;
    //private LinkedList<Node> actorNodes;
    private Map<String, Node> nameToNode ;
    //private Node kevinBacon;
    //private Map<Node, Node> graph;

    public BaconGraph(String fileName) {
        nameToNode = new HashMap<>();
        read(fileName);
        //actorNodes = new LinkedList<>();
        //this.graph = new HashMap<String, List<String>>();

    }

    //maps actor name to a list of their movies

    public void read(String fileName) {

        Map<String, Node> movieToNode = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line ;
            Node actorNode = null;

            while ((line = br.readLine()) != null) { //line = br.readLine()) != null
                if (line.startsWith("<a>")){
                    actorNode = new Node(line);
                    /*if (line.equals("<a>Bacon, Kevin")) {
                        actorNodes.addFirst(actorNode);
                        kevinBacon = actorNode;
                    }
                    else*/
                        //actorNodes.add(actorNode);
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
        } catch (IOException e) {
            e.printStackTrace();
        } /*finally {
            for (Map.Entry<String, Node> entry : movieToNode.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue().getConnections());
            }
        }*/
    }

    public void printNameToNode(){
        System.out.println("print name to node");
        for (Map.Entry<String, Node> entry : nameToNode.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue().getConnections());
        }
    }

    //public List getBacon(String name)

    public List<Node> breadthFirstSearch(String goalName) throws IllegalArgumentException{

        LinkedList<Node> queue = new LinkedList<>();
        Map<Node, Node> visitedConnections = new HashMap<>();

        goalName = "<a>"+ goalName;
        if (!(nameToNode.containsKey(goalName))){
            throw new IllegalArgumentException("No such actor in our records!");
        }

        Node kevinB = nameToNode.get("<a>Bacon, Kevin (I)");
        queue.addFirst(kevinB);
        //kevinB.visited(true);
        visitedConnections.put(kevinB, null);

        while (!queue.isEmpty()){
            Node currentNode = queue.pollFirst();
            if(currentNode.getName().equals(goalName)){
                return gatherPath(currentNode, visitedConnections);
            }
            for(Node n : currentNode.getConnections()){
                if(!(visitedConnections.containsKey(n))){ //!n.isVisited()
                    queue.add(n);
                    //n.visited(true);
                    visitedConnections.put(n, currentNode);
                }
            }
             //ifAbsent not necessary because of visited var in Node
            //parentNode = currentNode;

        }
        return Collections.emptyList();
    }
    public List<Node> gatherPath(Node goalDestination, Map<Node, Node> visitedConnections){
        LinkedList<Node> path = new LinkedList<>();
        Node currentNode = goalDestination;
        while(currentNode != null){
            Node next = visitedConnections.get(currentNode); //gives parent node, closer to source
            //Edge<T> edge = getEdgeBetween(next, currentNode); //points from source towards goal destination
            path.addFirst(currentNode);
            currentNode = next;
        }
        return path;
    }

}


