package Graph;

import java.util.*;

public class Graph {
    private Map<Vertex, List<Vertex>> AdjacencyList; // AdjacencyList for the Graph

    public Graph()
    {
        AdjacencyList = new HashMap<Vertex, List<Vertex>>();
    }

    public Map<Vertex, List<Vertex>> getAdjacencyList()
    {
        return AdjacencyList;
    }

    public void setAdjacencyList(Map<Vertex, List<Vertex>> adjList)
    {
        AdjacencyList = adjList;
    }

    /*
    Adds a Vertex to the Graph
    Gets generic Data
    */
    public <T> void addVertex(T data)
    {
        AdjacencyList.putIfAbsent(new Vertex(data), new ArrayList<Vertex>()); // putIfAbsent only adds to the Hashmap if it does not already exist
    }

    /*
    Removes a Vertex
    The data to be Removed from the Hashmap
     */
    public <T> void removeVertex(T data)
    {
        Vertex vert = new Vertex(data);
        AdjacencyList.values().stream().forEach(e -> e.remove(vert));
        AdjacencyList.remove(vert);
    }

    /*
    Creates an Edge between two Vertexes
    The Data of the two Vertexes to be connected
     */
    public <T> void addEdge(T Data1, T Data2)
    {
        Vertex vert1 = new Vertex(Data1);
        Vertex vert2 = new Vertex(Data2);
        AdjacencyList.get(vert1).add(vert2);
        AdjacencyList.get(vert2).add(vert1);
    }

    /*
    Removes a Edge
    The Data from the two Vertexes of which the edge should be deleted
     */
    public <T> void removeEdge(T Data1, T Data2)
    {
        Vertex vert1 = new Vertex(Data1);
        Vertex vert2 = new Vertex(Data2);
        List<Vertex> eVert1 = AdjacencyList.get(vert1);
        List<Vertex> eVert2 = AdjacencyList.get(vert2);
        if (eVert1 != null) eVert1.remove(vert2);
        if (eVert2 != null) eVert2.remove(vert1);
    }

    /*
    Returns the adjacent Vertexies
    The Data of the Vertex from which the adjacent vertexes should be returned
     */
    public <T> List<Vertex> getAdjacenctVertex(T Data){
        return AdjacencyList.get(new Vertex((Data)));
    }

    /*
    Traverses the Graph first in the deph than in the with
    The Data for the root (start) of the search
     */
    public <T> Set<T> depthFirstTraversal(T root){
        Set<T> visited = new LinkedHashSet<T>();
        Stack<T> stack = new Stack<T>();
        stack.push(root);
        while (!stack.isEmpty()) {
            T vertexData = stack.pop();
            if (!visited.contains(vertexData)) {
                visited.add(vertexData);
                for (Vertex v : this.getAdjacenctVertex(vertexData)) {
                    stack.push((T)v.getData());
                }
            }
        }
        return visited;
    }

    /*
    Traverses the Graph first in the with than in the depth
    The Data for the root (start) of the search
     */
    public <T> Set<T> breadthFirstTraversal(T root) {
        Set<T> visited = new LinkedHashSet<T>();
        Queue<T> queue = new LinkedList<T>();
        queue.add(root);
        visited.add(root);
        while (!queue.isEmpty()) {
            T vertexData = queue.poll();
            for (Vertex v : this.getAdjacenctVertex(vertexData)) {
                if (!visited.contains(v.getData())) {
                    visited.add((T)v.getData());
                    queue.add((T)v.getData());
                }
            }
        }
        return visited;
    }
}
