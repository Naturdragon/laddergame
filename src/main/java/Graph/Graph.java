package Graph;

import java.util.*;

public class Graph {
    private Map<Vertex, List<Vertex>> AdjacencyList;

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

    public <T> void addVertex(T data)
    {
        AdjacencyList.putIfAbsent(new Vertex(data), new ArrayList<Vertex>());
    }

    public <T> void removeVertex(T data)
    {
        Vertex vert = new Vertex(data);
        AdjacencyList.values().stream().forEach(e -> e.remove(vert));
        AdjacencyList.remove(vert);
    }

    public <T> void addEdge(T Data1, T Data2)
    {
        Vertex vert1 = new Vertex(Data1);
        Vertex vert2 = new Vertex(Data2);
        AdjacencyList.get(vert1).add(vert2);
        AdjacencyList.get(vert2).add(vert1);
    }

    public <T> void removeEdge(T Data1, T Data2)
    {
        Vertex vert1 = new Vertex(Data1);
        Vertex vert2 = new Vertex(Data2);
        List<Vertex> eVert1 = AdjacencyList.get(vert1);
        List<Vertex> eVert2 = AdjacencyList.get(vert2);
        if (eVert1 != null) eVert1.remove(vert2);
        if (eVert2 != null) eVert2.remove(vert1);
    }
    public <T> List<Vertex> getAdjacenctVertex(T Data){
        return AdjacencyList.get(new Vertex((Data)));
    }

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
