package com.example.theos.BordGameGraph;

import Graph.*;
import com.example.theos.Field;
import com.example.theos.GameBoard;
import com.example.theos.Player;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.*;

public class BoardGraph {
    private Graph forwardGraph;
    private Graph backwardGraph;

    private Random rnd = new Random();

    public BoardGraph()
    {
        /*
        Initialises two Graphs. One for forwards movememnt and one for backwards movement. We decided to use two graphs because a graph does not have a direction, our game does.
         */
        forwardGraph = new Graph();
        backwardGraph = new Graph();
    }

    /*
    Adds a Vertex to both Graphs
    Takes generic Data
    */
    public <T> void addVertex(T data)
    {
        forwardGraph.addVertex(data);
        backwardGraph.addVertex(data);
    }

    /*
    Removes a Vertex from both graphs
    The data to be Removed from the Hashmap
     */
    public <T> void removeVertex(T data)
    {
        forwardGraph.removeVertex(data);
        backwardGraph.removeVertex(data);
    }

    /*
    Adds an edge which goes in both directions

     */
    public <T> void addEdge(T source, T target)
    {
        forwardGraph.addOneDirectionalEdge(source, target);
        backwardGraph.addOneDirectionalEdge(target, source);
    }

    public <T> void addEdge(T source, T target, Weight weight)
    {
        forwardGraph.addOneDirectionalEdge(source, target, weight);
        backwardGraph.addOneDirectionalEdge(target, source, weight);
    }

    public <T> void addEdge(T source, T target, T weight, edgeType type)
    {
        forwardGraph.addOneDirectionalEdge(source, target, new Weight(weight, type));
        backwardGraph.addOneDirectionalEdge(target, source, new Weight(weight, type));
    }

    public <T> void addOneDirectionalEdgeForward(T source, T target)
    {
        forwardGraph.addOneDirectionalEdge(source, target);
    }

    public <T> void addOneDirectionalEdgeForward(T source, T target, Weight weight)
    {
        forwardGraph.addOneDirectionalEdge(source, target, weight);
    }

    public <T> void addOneDirectionalEdgeForward(T source, T target, T weight, edgeType type)
    {
        forwardGraph.addOneDirectionalEdge(source, target, new Weight(weight, type));
    }

    public <T> void addOneDirectionalEdgeBackward(T source, T target)
    {
        backwardGraph.addOneDirectionalEdge(target, source);
    }

    public <T> void addOneDirectionalEdgeBackward(T source, T target, Weight weight)
    {
        backwardGraph.addOneDirectionalEdge(source, target, weight);
    }

    public <T> void addOneDirectionalEdgeBackward(T source, T target, T weight, edgeType type)
    {
        backwardGraph.addOneDirectionalEdge(source, target, new Weight(weight, type));
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

    /*
    Used to traverse the graph in both directions. Takes care of Lader Fields and Crossings
     */
    public Field hopCountTraversal(Field root, int hops)
    {
        if (hops == 0) return root; // Whenn a zero got roled nothing needs to be done. Return the current field

        Field vertexData = root;
        int vertexListSize;
        Weight tmpWeigth;
        if (hops > 0) { // Checks which graph should be used

            /*
            1. Checks if current field is a crossing.
                    Crossing -> Event for selection
                    Normal Field Select Next Field
                    A Ladder Field here should not be possible. Because it will only be there when it triggers and after that the new field will be the current field.
            2. Checks if the new Field is a ladder field.
                    new Field will become the target Field of the Ladder (Ladder will be deselected)

             */
            for (int i = 0; i < hops; i++) {
                vertexListSize = forwardGraph.getAdjacenctVertex(vertexData).size();
                if (vertexListSize > 0) {
                    if (vertexData.getType() == Field.fieldType.CrossoverField) {
                        return vertexData;  // Crossing
                    } else {
                        if (vertexData.getType() == Field.fieldType.LadderField)
                            System.out.println("!! This should not Happen !!");
                        for (var item : forwardGraph.getAdjacenctVertexEdges(vertexData)) {
                            tmpWeigth = (Weight) item.getWeight();
                            if (tmpWeigth.getType() == edgeType.NormalEdge) {
                                vertexData = (item.getSource() == vertexData) ? (Field) item.getTarget() : (Field) item.getSource();
                                break;
                            }
                        }
                    }


                    if (vertexData.getType() == Field.fieldType.LadderField) {
                        if (i == hops - 1) { // Last loop
                            for (var item : forwardGraph.getAdjacenctVertexEdges(vertexData)) {
                                tmpWeigth = (Weight) item.getWeight();
                                if (tmpWeigth.getType() == edgeType.LadderEdge) {
                                    vertexData = (item.getSource() == vertexData) ? (Field) item.getTarget() : (Field) item.getSource();
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    return vertexData;   // End of Graph
                }
            }


            return vertexData;
        } else {

            for (int i = hops; i < 0; i++) {
                vertexListSize = backwardGraph.getAdjacenctVertex(vertexData).size();
                if (vertexListSize > 0) {
                    if (vertexListSize > 1) {
                        vertexData = backwardGraph.getAdjacenctVertex(vertexData).get(rnd.nextInt(vertexListSize)); // More than one way back
                    } else {
                        vertexData = backwardGraph.getAdjacenctVertex(vertexData).get(0);   // One way back
                    }
                } else {
                    return vertexData;  // End of Graph
                }
            }
            return vertexData;
        }
    }


    /*


    Primar Methode: This methode is to be called before the hopCountTraversal. I also handels the Crossings, which hopCountTraversal does not.
     */
    public SequentialTransition getAnimationPathFromGraph(Field root, int hops, int animationOffsetX, int animationOffsetY, Player currentPlayer, GameBoard gameBord)
    {
        SequentialTransition seqtrans = new SequentialTransition();

        // see below code block
        PathTransition pathTrans = new PathTransition();
        pathTrans.setPath(new Line(root.getX() - animationOffsetX, root.getY() - animationOffsetY, root.getX() - animationOffsetX, root.getY() - animationOffsetY));
        pathTrans.setDuration(Duration.ZERO);
        seqtrans.getChildren().add(pathTrans);
        // Adds a basic non Moving Transsition. This allows the returning of the seqtrans at any point after the code block above.

        if (hops == 0) return seqtrans; // Nothing Happens

        Path standartPath = new Path(); // Path to be used for normale movement
        standartPath.getElements().add(new MoveTo(root.getX() - animationOffsetX, root.getY() - animationOffsetY));
        double standartDurration = 0;

        Path ladderPath = new Path(); // Path to be used for normale movement
        double ladderDurration = 0;

        Field vertexData = root;
        int vertexListSize;
        Weight tmpWeigth;
        if (hops > 0) { // Checks which graph should be used
            for (int i = 0; i < hops; i++) {
                /*
                // Does not work Properly
                if(backwardGraph.getAdjacenctVertex(vertexData).size() == 1 && backwardGraph.getAdjacenctVertex(vertexData).get(0).getType() == Field.fieldType.CrossoverField ){ // Checks if the Last field was a crossing Field
                    standartPath.getElements().clear(); // clear the default point
                    Field fieldcrossing = backwardGraph.getAdjacenctVertex(vertexData).get(0);
                    standartPath.getElements().add(new LineTo(fieldcrossing.getX() - animationOffsetX, fieldcrossing.getY() - animationOffsetY));
                    standartPath.getElements().add(new LineTo(vertexData.getX() - animationOffsetX, vertexData.getY() - animationOffsetY));
                    Weight tempCrossingWeight = (Weight) backwardGraph.getAdjacenctVertexEdges(vertexData).get(0).getWeight();
                    standartDurration = standartDurration + (Integer) tempCrossingWeight.getData();
                }

                 */
                vertexListSize = forwardGraph.getAdjacenctVertex(vertexData).size();
                if (vertexListSize > 0) {
                    if (vertexData.getType() == Field.fieldType.CrossoverField) {
                        PathTransition standartPathTransition = new PathTransition();
                        standartPathTransition.setPath(standartPath);
                        standartPathTransition.setDuration(Duration.millis(standartDurration));
                        seqtrans.getChildren().add(standartPathTransition);
                        gameBord.selectPathEvent(vertexData,i-1,currentPlayer,seqtrans);     // Crossing
                    } else {
                        // The user can stand at this point on a lande because he went backwards. When walking backwards Laders do not trigger.
                        for (var item : forwardGraph.getAdjacenctVertexEdges(vertexData)) {
                            tmpWeigth = (Weight) item.getWeight();
                            if (tmpWeigth.getType() == edgeType.NormalEdge) {
                                var nextVertexData = (item.getSource() == vertexData) ? (Field) item.getTarget() : (Field) item.getSource();
                                standartPath.getElements().add(new LineTo(nextVertexData.getX() - animationOffsetX, nextVertexData.getY() - animationOffsetY));
                                standartDurration = standartDurration + (Integer) tmpWeigth.getData();
                                vertexData = nextVertexData;
                                break;
                            }
                        }
                    }

                    if (vertexData.getType() == Field.fieldType.LadderField) {
                        if (i == hops - 1) { // Last loop
                            for (var item : forwardGraph.getAdjacenctVertexEdges(vertexData)) {
                                tmpWeigth = (Weight) item.getWeight();
                                if (tmpWeigth.getType() == edgeType.LadderEdge) {
                                    if (vertexData.getId() == 71 || vertexData.getId() == 72)
                                        currentPlayer.playFallWaterfall(); // Special Fall on the Watterfall
                                    ladderPath.getElements().add(new MoveTo(vertexData.getX() - animationOffsetX, vertexData.getY() - animationOffsetY));
                                    var nextVertexData = (item.getSource() == vertexData) ? (Field) item.getTarget() : (Field) item.getSource();
                                    ladderPath.getElements().add(new LineTo(nextVertexData.getX() - animationOffsetX, nextVertexData.getY() - animationOffsetY));
                                    ladderDurration = ladderDurration + (Integer) tmpWeigth.getData();
                                    vertexData = nextVertexData;
                                    break;
                                }
                            }
                        }

                    }
                } else {
                    if (standartPath.getElements().size() > 0) {
                        PathTransition standartPathTransition = new PathTransition();
                        standartPathTransition.setPath(standartPath);
                        standartPathTransition.setDuration(Duration.millis(standartDurration));
                        seqtrans.getChildren().add(standartPathTransition);
                    }

                    if (ladderPath.getElements().size() > 0) {
                        PathTransition ladderPathTransition = new PathTransition();
                        ladderPathTransition.setPath(ladderPath);
                        ladderPathTransition.setDuration(Duration.millis(ladderDurration));
                        seqtrans.getChildren().add(ladderPathTransition);
                    }
                    return seqtrans;   // End of Graph
                }
            }

            PathTransition standartPathTransition = new PathTransition();
            standartPathTransition.setPath(standartPath);
            standartPathTransition.setDuration(Duration.millis(standartDurration));
            seqtrans.getChildren().add(standartPathTransition);

            if (ladderPath.getElements().size() > 0) {
                PathTransition ladderPathTransition = new PathTransition();
                ladderPathTransition.setPath(ladderPath);
                ladderPathTransition.setDuration(Duration.millis(ladderDurration));
                seqtrans.getChildren().add(ladderPathTransition);
            }

            return seqtrans;
        } else {

            for (int i = hops; i < 0; i++) {
                vertexListSize = backwardGraph.getAdjacenctVertex(vertexData).size();
                if (vertexListSize > 0) {
                    if (vertexListSize > 1) {
                        var nextVertexData = backwardGraph.getAdjacenctVertex(vertexData).get(rnd.nextInt(vertexListSize)); // More than one way back
                        tmpWeigth = (Weight) backwardGraph.getAdjacenctVertexEdges(vertexData).get(0).getWeight(); // Initialesierung weil es sein könnte das nichts zurück kommt.

                        for (var item : backwardGraph.getAdjacenctVertexEdges(vertexData)) {
                            if (item.getSource() == nextVertexData || item.getTarget() == nextVertexData) {
                                tmpWeigth = (Weight) item.getWeight();
                                break;
                            }
                        }
                        standartPath.getElements().add(new LineTo(nextVertexData.getX() - animationOffsetX, nextVertexData.getY() - animationOffsetY));
                        standartDurration = standartDurration + (Integer) tmpWeigth.getData();
                        vertexData = nextVertexData;
                    } else {
                        var nextVertexData = backwardGraph.getAdjacenctVertex(vertexData).get(rnd.nextInt(vertexListSize)); // More than one way back
                        tmpWeigth = (Weight) backwardGraph.getAdjacenctVertexEdges(vertexData).get(0).getWeight(); // Initialesierung weil es sein könnte das nichts zurück kommt.
                        standartPath.getElements().add(new LineTo(nextVertexData.getX() - animationOffsetX, nextVertexData.getY() - animationOffsetY));
                        standartDurration = standartDurration + (Integer) tmpWeigth.getData();
                        vertexData = nextVertexData;
                    }


                } else {
                    PathTransition standartPathTransition = new PathTransition();
                    standartPathTransition.setPath(standartPath);
                    standartPathTransition.setDuration(Duration.millis(standartDurration));
                    seqtrans.getChildren().add(standartPathTransition);

                    return seqtrans;  // End of Graph
                }
            }

            PathTransition standartPathTransition = new PathTransition();
            standartPathTransition.setPath(standartPath);
            standartPathTransition.setDuration(Duration.millis(standartDurration));
            seqtrans.getChildren().add(standartPathTransition);
            return seqtrans;
        }
    }

    public Field crossingMove(Field root, edgeType edgetype){
        if(root.getType() != Field.fieldType.CrossoverField) return root;

        for (Edge edge: forwardGraph.getAdjacenctVertexEdges(root)) {
            Weight tempWeight = (Weight)edge.getWeight();
            if(tempWeight.getType() == edgetype){
                return (edge.getSource() == root)? (Field)edge.getTarget():(Field)edge.getSource();
            }
        }
        return root; // An error is accured. No Crossing should have no two crossing Edges. Returning Root for damage minomizaton.
    }

    public void checkGraph()
    {
        // TODO: (OPTONAL) Checking if the graph is a valide one.
        /*
        Forward Graph
        Backward Graph
         */

        System.out.println("!! NOT IMPLEMENTED JET !!");
    }

        /*
        Implement HopCountTraversal Methode

        Kommentare

        Testing

        Check Graph Methode
         */

    public enum edgeType {
        NormalEdge,
        LadderEdge,
        CrossoverPathOne,
        CrossoverPathTwo
    }
}