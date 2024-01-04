package com.example.theos.BordGameGraph;

import Graph.*;
import com.example.theos.Field;

import java.util.*;

public class BoardGraph {
    private Graph forwardGraph;
    private Graph backwardGraph;

    public Random rnd = new Random();

    public BoardGraph()
    {
        forwardGraph = new Graph();
        backwardGraph = new Graph();
    }

    /*
    Adds a Vertex to the Graph
    Gets generic Data
    */
    public <T> void addVertex(T data)
    {
        forwardGraph.addVertex(data);
        backwardGraph.addVertex(data);
    }

    /*
    Removes a Vertex
    The data to be Removed from the Hashmap
     */
    public <T> void removeVertex(T data)
    {
        forwardGraph.removeVertex(data);
        backwardGraph.removeVertex(data);
    }

    public <T> void addOneDirectionalEdge(T source, T target)
    {
        forwardGraph.addOneDirectionalEdge(source, target);
        backwardGraph.addOneDirectionalEdge(target, source);
    }

    public <T> void addOneDirectionalEdge(T source, T target, T weight)
    {
        forwardGraph.addOneDirectionalEdge(source, target, weight);
        backwardGraph.addOneDirectionalEdge(target, source, weight);
    }

    public <T> void addOneDirectionalEdge(T source, T target, T weight, T type)
    {
        forwardGraph.addOneDirectionalEdge(source, target, weight, type);
        backwardGraph.addOneDirectionalEdge(target, source, weight, type);
    }

    /*
    Removes a Edge
    The Data from the two Vertexes of which the edge should be deleted
     */
    public <T> void removeEdge(T source, T targed)
    {
        forwardGraph.removeEdge(source, targed);
        backwardGraph.removeEdge(targed, source);
    }

    public Field hopCountTraversal(Field root, int hops)
    {
        Field vertexData = root;
        int vertexListSize;
        if (hops > 0) {
            for (int i = 0; i < hops; i++) {
                vertexListSize = forwardGraph.getAdjacenctVertex(vertexData).size();
                if (vertexListSize > 1) {
                    switch (vertexData.getType()) {
                        case CrossoverField:
                            System.out.println("Crosing selection is not implemented jet!");
                            break;
                        case LadderField:
                            for (var item : forwardGraph.getAdjacenctVertexEdges(vertexData)) {
                                if (item.getType() == edgeType.NormalEdge && i < hops) {
                                    vertexData = (item.getSource() == vertexData) ? (Field) item.getTarget() : (Field) item.getSource();
                                    break;
                                }
                                if (item.getType() == edgeType.LadderEdge && i == hops-1) {
                                    vertexData = (item.getSource() == vertexData) ? (Field) item.getTarget() : (Field) item.getSource();
                                    break;
                                }
                            }
                            break;
                        default:
                            System.out.println("The Graph was not built how it should have been. THis state should not be possible");

                    }
                } else {
                    vertexData = forwardGraph.getAdjacenctVertex(vertexData).get(0);   // One way forward
                }
            }
            return vertexData;
        } else {
            if (hops == 0) return root;
            for (int i = hops; i <= 0; i++) {
                vertexListSize = backwardGraph.getAdjacenctVertex(vertexData).size();
                if (vertexListSize > 1) {
                    vertexData = backwardGraph.getAdjacenctVertex(vertexData).get(rnd.nextInt(vertexListSize)); // More than one way back
                } else {
                    vertexData = backwardGraph.getAdjacenctVertex(vertexData).get(0);   // One way back
                }
            }
            return vertexData;
        }
    }
        /*
        Implement HopCountTraversal Methode

        Kommentare

        Testing

        Check Graph Methode
         */

    public enum edgeType {
        NormalEdge,
        LadderEdge
    }
}
