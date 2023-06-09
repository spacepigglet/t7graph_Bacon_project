package bacon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BaconGraph {
    private static final String KEVIN_BACON = "<a>Bacon, Kevin (I)";
    private static final String ACTOR_SIGNIFIER = "<a>";
    //maps actor name to their node
    private final Map<String, Node> actorToNode;

    public BaconGraph(String fileName) {
        actorToNode = new HashMap<>();
        read(fileName);
    }

    /**
     * Reads names of actors and movies/tv shows from a file containing data from imdb.
     * Creates nodes containing the name and its connections to other nodes.
     * Stores actor names and maps them to their respective nodes in the map actorToNode.
     *
     * @param fileName: the name of the file containing the data about actors and movies.
     *                  Each name is on its own line.
     *                  All actors start with "<a>" and all movies start with <t>.
     */
    private void read(String fileName) {
        Map<String, Node> movieToNode = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            Node actorNode = null;

            while ((line = br.readLine()) != null) {
                if (line.startsWith(ACTOR_SIGNIFIER)) {
                    actorNode = new Node(line);
                    actorToNode.put(line, actorNode);
                } else {
                    Node movieNode = movieToNode.get(line);
                    if (movieNode == null) {
                        movieNode = new Node(line);
                        movieToNode.put(line, movieNode);
                    }
                    assert actorNode != null; //if file is not structured correctly actorNode could be null...
                    //add connection both ways
                    actorNode.addConnection(movieNode);
                    movieNode.addConnection(actorNode);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds the path from Kevin Bacon to goalActor. Contains Nodes of actors and movies.
     *
     * @param goalActor: the actor the user wants to know the Bacon path of
     * @return List<Node>, the path of nodes in order from Kevin Bacon to goalActor
     * @throws IllegalArgumentException If the goal does not exist in the graph
     */
    public List<Node> findPathFromKevinB(String goalActor) throws IllegalArgumentException {
        goalActor = ACTOR_SIGNIFIER + goalActor; //all actors' names begin with <a>

        Node kevinB = actorToNode.get(KEVIN_BACON);
        Node goalActorNode = actorToNode.get(goalActor);
        if (goalActorNode == null || kevinB == null) {
            throw new IllegalArgumentException("No such actor in our records!");
        }
        return breadthFirstSearch(kevinB, goalActorNode);
    }

    /**
     * Gives one of the shortest paths from the origin node to another node in a graph.
     *
     * @param origin: the node the user wants to know the path from.
     * @param goal:   the node the user wants to know the path to.
     * @return A list of Nodes in the order of the shortest path from origin to goal node.
     */
    private List<Node> breadthFirstSearch(Node origin, Node goal) {
        assert origin != null;
        assert goal != null;

        LinkedList<Node> queue = new LinkedList<>();
        Map<Node, Node> visitedConnections = new HashMap<>();

        queue.addFirst(origin);
        visitedConnections.put(origin, null);

        while (!queue.isEmpty()) {
            Node currentNode = queue.pollFirst();
            if (currentNode.equals(goal)) {
                return gatherPath(currentNode, visitedConnections);
            }
            for (Node n : currentNode.getConnections()) {
                if (!(visitedConnections.containsKey(n))) {
                    queue.add(n);
                    visitedConnections.put(n, currentNode);
                }
            }
        }
        return Collections.emptyList();
    }

    private List<Node> gatherPath(Node goalDestination, Map<Node, Node> visitedConnections) {
        LinkedList<Node> path = new LinkedList<>();
        Node currentNode = goalDestination;
        while (currentNode != null) {
            Node next = visitedConnections.get(currentNode); //gives parent node, closer to source
            path.addFirst(currentNode);
            currentNode = next;
        }
        return path;
    }
}


