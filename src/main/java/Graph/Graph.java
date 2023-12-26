package Graph;

import javafx.animation.PathTransition;

import java.util.*;

public class Graph {
    private Map<Vertex, List<Edge>> AdjacencyList; // AdjacencyList for the Graph

    public Graph()
    {
        AdjacencyList = new HashMap<Vertex, List<Edge>>();
    }

    public Map<Vertex, List<Edge>> getAdjacencyList()
    {
        return AdjacencyList;
    }

    public void setAdjacencyList(Map<Vertex, List<Edge>> adjList)
    {
        AdjacencyList = adjList;
    }

    /*
    Adds a Vertex to the Graph
    Gets generic Data
    */
    public <T> void addVertex(T data)
    {
        AdjacencyList.putIfAbsent(new Vertex(data), new ArrayList<Edge>()); // putIfAbsent only adds to the Hashmap if it does not already exist
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

        Edge edge = new Edge(Data1, Data2);
        AdjacencyList.get(vert1).add(edge);
        AdjacencyList.get(vert2).add(edge);
    }

    public <T> void addEdge(T data1, T data2, T weight)
    {
        Vertex vert1 = new Vertex(data1);
        Vertex vert2 = new Vertex(data2);

        Edge edge = new Edge(data1, data2, weight);
        AdjacencyList.get(vert1).add(edge);
        AdjacencyList.get(vert2).add(edge);
    }

    public <T> void addOneDirectionalEdge(T source, T target){
        Vertex Source = new Vertex(source);
        Edge edge = new Edge(source, target);
        AdjacencyList.get(Source).add(edge);
    }

    public <T> void addOneDirectionalEdge(T source, T target, T weight){
        Vertex Source = new Vertex(source);
        Edge edge = new Edge(source, target, weight);
        AdjacencyList.get(Source).add(edge);
    }

    /*
    Removes a Edge
    The Data from the two Vertexes of which the edge should be deleted
     */
    public <T> void removeEdge(T Data1, T Data2)
    {
        Vertex vert1 = new Vertex(Data1);
        Vertex vert2 = new Vertex(Data2);

        List<Edge> eVert1 = AdjacencyList.get(vert1);
        List<Edge> eVert2 = AdjacencyList.get(vert2);

        if (eVert1 != null) eVert1.remove(vert2);
        if (eVert2 != null) eVert2.remove(vert1);
    }

    /*
    Returns the adjacent Vertexies
    The Data of the Vertex from which the adjacent vertexes should be returned
     */
    public <T> List<Edge> getAdjacenctVertex(T Data){
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
                for (Edge v : this.getAdjacenctVertex(vertexData)) {
                    if (v.getSource() == vertexData) {
                        stack.push((T) v.getTarget());
                    }else {
                        stack.push((T) v.getSource());
                    }
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

            for (Edge v : this.getAdjacenctVertex(vertexData)) {
                if (!visited.contains(v.getSource())) {
                    visited.add((T)v.getSource());
                    queue.add((T)v.getSource());
                }
                if (!visited.contains(v.getTarget())) {
                    visited.add((T)v.getTarget());
                    queue.add((T)v.getTarget());
                }
            }
        }
        return visited;
    }
}
