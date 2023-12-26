package com.example.theos;

import Graph.Graph;

import java.util.Enumeration;

public class TestingArea {
    public static void main(String[] args) {

        /*
        Dice standardDie = new Dice();
        Dice specialDie = new Dice(new int[]{-3,-3,6,6,6,7});

        System.out.println("Standard Die's 10 rolls");
        for (int i = 0;i < 10; i++) {
            System.out.println(standardDie.rollDie());
        }

        System.out.println("\nSpecial Die's 10 rolls");
        for (int i = 0;i < 10; i++) {
            System.out.println(specialDie.rollDie());
        }
        */


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
