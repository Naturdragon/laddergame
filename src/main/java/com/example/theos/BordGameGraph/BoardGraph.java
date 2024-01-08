package com.example.theos.BordGameGraph;

import Graph.*;
import com.example.theos.Field;
import com.example.theos.Player;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

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

    public <T> void addOneDirectionalEdge(T source, T target, T weight, edgeType type)
    {
        forwardGraph.addOneDirectionalEdge(source, target, new Weight(weight, type));
        backwardGraph.addOneDirectionalEdge(target, source, new Weight(weight, type));
    }

    public <T> void addOneDirectionalEdgeForward(T source, T target)
    {
        forwardGraph.addOneDirectionalEdge(source, target);
        backwardGraph.addOneDirectionalEdge(target, source);
    }

    public <T> void addOneDirectionalEdgeForward(T source, T target, T weight)
    {
        forwardGraph.addOneDirectionalEdge(source, target, weight);
        backwardGraph.addOneDirectionalEdge(target, source, weight);
    }

    public <T> void addOneDirectionalEdgeForward(T source, T target, T weight, edgeType type)
    {
        forwardGraph.addOneDirectionalEdge(source, target, new Weight(weight, type));
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
    Used to traverce the graph in both directions. Takes care of
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
                        // TODO: Implementing the Crossing selection
                        System.out.println("Crosing selection is not implemented jet!");
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

    public SequentialTransition getAnimationPathFromGraph(Field root, int hops, int animationOffsetX, int animationOffsetY, Player player)
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
                vertexListSize = forwardGraph.getAdjacenctVertex(vertexData).size();
                if (vertexListSize > 0) {
                    if (vertexData.getType() == Field.fieldType.CrossoverField) {
                        // TODO: Implementing the Crossing selection
                        System.out.println("Crosing selection is not implemented jet!");
                    } else {
                        if (vertexData.getType() == Field.fieldType.LadderField)
                            System.out.println("!! This should not Happen !!");

                        // TODO Choose a Normal Field at random if several Normal Fields are connectet to this field


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
                                        player.playFallOpaque(); // Special Fall on the Watter fall
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
                        vertexData = backwardGraph.getAdjacenctVertex(vertexData).get(rnd.nextInt(vertexListSize)); // More than one way back
                    } else {

                        vertexData = backwardGraph.getAdjacenctVertex(vertexData).get(0);   // One way back
                    }

                    /*
                    ladderPath.getElements().add(new MoveTo(vertexData.getX() - animationOffsetX, vertexData.getY() - animationOffsetY));
                    var nextVertexData = (item.getSource() == vertexData) ? (Field) item.getTarget() : (Field) item.getSource();
                    ladderPath.getElements().add(new LineTo(nextVertexData.getX()-animationOffsetX,nextVertexData.getY()-animationOffsetY));
                    ladderDurration = ladderDurration + (Integer) tmpWeigth.getData();
                    vertexData = nextVertexData;

                     */

                    PathTransition standartPathTransition = new PathTransition();
                    standartPathTransition.setPath(standartPath);
                    standartPathTransition.setDuration(Duration.millis(standartDurration));
                    seqtrans.getChildren().add(standartPathTransition);

                } else {
                    return seqtrans;  // End of Graph
                }
            }

            return seqtrans;
        }
    }

    public void checkGraph()
    {
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
        LadderEdge
    }
}
