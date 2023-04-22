package bacon;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Node {
    private final String name;
    //private boolean visited;
    private List<Node> connections;

    public Node(String name) {
        this.name = name;
        connections = new ArrayList<>();
    }

    /*public void visited(boolean v){
        visited = v;
    }*/

    public void addConnection(Node node){
        connections.add(node);
    }

    public String getName(){return name;}

    public List<Node> getConnections(){return connections;}

    @Override
    public String toString() {
        return  name ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return name.equals(node.name) ; //&& connections.equals(node.connections)
    }

    @Override
    public int hashCode() {
        return Objects.hash(name); //,connections
    }

    /*public boolean isVisited() {return visited;
    }*/
}
