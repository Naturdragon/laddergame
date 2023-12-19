package com.example.theos;

import Graph.Graph;

public class TestingArea {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("Not Reachable");
        graph.addEdge("A", "B");
        graph.addEdge("C", "B");
        graph.addEdge("C", "A");
        graph.addEdge("C","D");
        graph.addEdge("D","E");


        System.out.println("breadthFirstTraversal: ");
        graph.breadthFirstTraversal("A").stream().forEach(x -> System.out.println(x));
        System.out.println("depthFirstTraversal: ");
        graph.depthFirstTraversal("A").stream().forEach(x -> System.out.println(x));
    }
}
