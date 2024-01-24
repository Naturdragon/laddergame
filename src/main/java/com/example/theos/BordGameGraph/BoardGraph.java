package com.example.theos.BordGameGraph;

import Graph.*;
import com.example.theos.Field;
import com.example.theos.GameBoard;
import com.example.theos.Player;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.*;
import java.util.stream.Stream;

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
        if (hops == 0) return root; // When a zero got roled nothing needs to be done. Return the current field

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
    This Methode returns two Variables. The Sequential Transition in return and the new Field already set in the Player Object. Merges the two previous Methodes hopCountTraversal and getAnimationPathFromGraph in one.
    Methode Conzept:
        Initial Setup

        Zero resolution

        Forward Graph:
            Checks if the player leaves a crossing Field

            Normal Fields

            Checks if the Player enters a Ladder Field at their last move

            End of Graph resolution

        Backward Graph:
            One way back

            Multible Ways back

            end of graph resolution


        Fehler:
            1) Around Field 114 craracters might bug out and stop moving
            2) After Selecting a Arrow the character does not continiue to move
     */
    public SequentialTransition getSequentialAnimationAndMakeMove(Player currentPlayer, int hops, int animationOffsetX, int animationOffsetY, GameBoard gameBord,  edgeType typeOfEdge)
    {
        // Initialising of variables
        SequentialTransition sequenTransis = new SequentialTransition();

        PauseTransition pause = new PauseTransition(Duration.millis(10));
        sequenTransis.getChildren().add(pause); // Adds a Pause as a basic element. Allowes the returning without the use of null and required null checks

        Field vertexData = currentPlayer.getCurrentField();   // The beginning Field of the Move

        Path standartPath = new Path(); // Path to be used for normale movement
        standartPath.getElements().add(new MoveTo(vertexData.getX() - animationOffsetX, vertexData.getY() - animationOffsetY));     // Sets the starting Point
        double standartDurration = 0;

        if (hops == 0) return sequenTransis;

        if (hops > 0) {
            // Forwards Movement
            for (int i = 0; i < hops; i++) {
                if (forwardGraph.getAdjacenctVertex(vertexData).size() > 0) {
                    // Has a Path to go
                    if (vertexData.getType() == Field.fieldType.CrossoverField) {
                        // Crossing

                        if(typeOfEdge == null) {

                            currentPlayer.setCurrentField(vertexData);
                            gameBord.selectPathEvent(i, currentPlayer);

                            // Adding the Standart Path; The Methode returns the Path to the Crossing and sets the player there. The Events make the run from this point forward.
                            PathTransition standartPathTransition = new PathTransition();
                            standartPathTransition.setPath(standartPath);
                            standartPathTransition.setDuration(Duration.millis(standartDurration));
                            sequenTransis.getChildren().add(standartPathTransition);

                            currentPlayer.setCurrentField(vertexData);
                            return sequenTransis;
                        }else {
                            // get next field
                            // Animations of normal Movement -> continiue
                            for (var edge:forwardGraph.getAdjacenctVertexEdges(vertexData)) {
                                Weight edgeWeight = (Weight) edge.getWeight();
                                if(edgeWeight.getType() == typeOfEdge){
                                    Field newVertexData = (edge.getSource() == vertexData)? (Field) edge.getTarget(): (Field)edge.getSource();
                                    standartPath.getElements().add(new LineTo(newVertexData.getX() - animationOffsetX, newVertexData.getY() - animationOffsetY));

                                    standartDurration = standartDurration + (Integer)getEdgeWeight(getNextEdge(vertexData,forwardGraph,edgeType.NormalEdge)).getData();

                                    vertexData = newVertexData; // Sets the new Fields to the current one
                                }
                            }
                        }
                    }

                    if (vertexData.getType() == Field.fieldType.NormalField || (vertexData.getType() == Field.fieldType.LadderField &&  i < hops)) {
                        // Normal Movement Forward
                        Field newVertexData = getNextVertex(vertexData, forwardGraph,edgeType.NormalEdge);

                        standartPath.getElements().add(new LineTo(newVertexData.getX() - animationOffsetX, newVertexData.getY() - animationOffsetY));

                        standartDurration = standartDurration + (Integer)getEdgeWeight(getNextEdge(vertexData,forwardGraph,edgeType.NormalEdge)).getData();

                        vertexData = newVertexData; // Sets the new Fields to the current one

                    }

                    if (vertexData.getType() == Field.fieldType.LadderField && i == hops-1) { // Last hop
                        // Ladder
                        Field newVertexData = getNextVertex(vertexData, forwardGraph,edgeType.LadderEdge);

                        // Adding the Standart Path
                        PathTransition standartPathTransition = new PathTransition();
                        standartPathTransition.setPath(standartPath);
                        standartPathTransition.setDuration(Duration.millis(standartDurration));
                        sequenTransis.getChildren().add(standartPathTransition);

                        // Adding the Ladder Path
                        Path ladderPath = new Path();
                        ladderPath.getElements().add(new MoveTo(vertexData.getX() - animationOffsetX, vertexData.getY() - animationOffsetY));   // Beginning of the Ladder path
                        ladderPath.getElements().add(new LineTo(newVertexData.getX() - animationOffsetX, newVertexData.getY() - animationOffsetY));   // Ending of the Ladder Path
                        PathTransition ladderPathTransition = new PathTransition();
                        ladderPathTransition.setPath(ladderPath);
                        ladderPathTransition.setDuration(Duration.millis((Integer)getEdgeWeight(getNextEdge(vertexData,forwardGraph,edgeType.LadderEdge)).getData()));
                        sequenTransis.getChildren().add(ladderPathTransition);

                        currentPlayer.setCurrentField(newVertexData);
                        return sequenTransis;
                    }
                } else {
                    // End of Graph

                    // Adding the Standart Path
                    PathTransition standartPathTransition = new PathTransition();
                    standartPathTransition.setPath(standartPath);
                    standartPathTransition.setDuration(Duration.millis(standartDurration));
                    sequenTransis.getChildren().add(standartPathTransition);

                    currentPlayer.setCurrentField(vertexData);
                    return sequenTransis;

                }

            }
            // End of Movement

            // Animation Path prep for return
            PathTransition normalTransitionPath = new PathTransition();
            normalTransitionPath.setPath(standartPath);
            normalTransitionPath.setDuration(Duration.millis(standartDurration));
            sequenTransis.getChildren().add(normalTransitionPath);

            currentPlayer.setCurrentField(vertexData);      // Sets the current Field for the Player
            return sequenTransis;
        } else {
            // Backwards Movement
            int knownVertexes;
            for (int i = hops; i < 0; i++) {
                knownVertexes = backwardGraph.getAdjacenctVertex(vertexData).size();

                if (knownVertexes == 0) {
                    // End of Graph

                    // Animation Path prep for return
                    PathTransition normalTransitionPath = new PathTransition();
                    normalTransitionPath.setPath(standartPath);
                    normalTransitionPath.setDuration(Duration.millis(standartDurration));
                    sequenTransis.getChildren().add(normalTransitionPath);

                    currentPlayer.setCurrentField(vertexData);      // Sets the current Field for the Player
                    return sequenTransis;
                }
                if (knownVertexes == 1) {
                    // One way back

                    Field newVertexData = getNextVertex(vertexData, backwardGraph,edgeType.NormalEdge);

                    standartPath.getElements().add(new LineTo(newVertexData.getX() - animationOffsetX, newVertexData.getY() - animationOffsetY));

                    standartDurration = standartDurration + (Integer)getEdgeWeight(getNextEdge(vertexData,backwardGraph,edgeType.NormalEdge)).getData();

                    vertexData = newVertexData; // Sets the new Fields to the current one
                } else {
                    // Multible ways back
                    Field newVertesData = backwardGraph.getAdjacenctVertex(vertexData).get(rnd.nextInt(knownVertexes));

                    Field newVertexData = getNextVertex(vertexData, backwardGraph,edgeType.NormalEdge);

                    standartPath.getElements().add(new LineTo(newVertexData.getX() - animationOffsetX, newVertexData.getY() - animationOffsetY));

                    for (var edges:backwardGraph.getAdjacenctVertexEdges(vertexData)) {
                        Weight edgesWeight = (Weight) edges.getWeight();
                        if(edgesWeight.getType() == edgeType.NormalEdge && (edges.getSource() == vertexData || edges.getTarget() == vertexData) && (edges.getSource() == newVertexData || edges.getTarget() == newVertesData)){
                            standartDurration = standartDurration + (Integer)edgesWeight.getData();
                        }
                    }

                    vertexData = newVertexData; // Sets the new Fields to the current one
                }
            }
            // End of backward Movement

            // Animation Path prep for return
            PathTransition normalTransitionPath = new PathTransition();
            normalTransitionPath.setPath(standartPath);
            normalTransitionPath.setDuration(Duration.millis(standartDurration));
            sequenTransis.getChildren().add(normalTransitionPath);

            currentPlayer.setCurrentField(vertexData);      // Sets the current Field for the Player
            return sequenTransis;
        }
    }

    private Field getNextVertex(Field root, Graph graphToUse, edgeType typeOfEdge)
    {
        if(graphToUse.getAdjacenctVertexEdges(root).size() == 0) return root;

        for (var edges:graphToUse.getAdjacenctVertexEdges(root)) {
            if(getEdgeWeight(edges).getType() == typeOfEdge) return (edges.getSource() == root) ? (Field) edges.getTarget() : (Field) edges.getSource();
        }
        return null; // No next Vertex was found
    }

    private Edge getNextEdge(Field root, Graph graphToUse, edgeType typeOfEdge)
    {
        if(graphToUse.getAdjacenctVertexEdges(root).size() == 0) return null;
        Weight weight;
        for (var edges:graphToUse.getAdjacenctVertexEdges(root)) {
            weight = (Weight)edges.getWeight();
            if(weight.getType() == typeOfEdge) return edges;
        }
        return null; // No next Edge was found
    }

    private Weight getEdgeWeight(Edge edge){
        return (Weight)edge.getWeight();
    }

    /*
    Primar Methode: This methode is to be called before the hopCountTraversal. I also handels the Crossings, which hopCountTraversal does not.
    */
    /*
    public SequentialTransition getAnimationPathFromGraph(Field root, int hops, int animationOffsetX, int animationOffsetY, Player currentPlayer, GameBoard gameBord, SequentialTransition seqtrans)
    {
        if (seqtrans == null) seqtrans = new SequentialTransition();

        // see below code block
        PathTransition pathTrans = new PathTransition();
        pathTrans.setPath(new Line(root.getX() - animationOffsetX, root.getY() - animationOffsetY, root.getX() - animationOffsetX, root.getY() - animationOffsetY));
        pathTrans.setDuration(Duration.ZERO);
        seqtrans.getChildren().add(pathTrans);
        // Adds a basic non Moving Transsition. This allows the returning of the seqtrans at any point after the code block above.

        if (hops == 0) {
            seqtrans.getChildren().remove(pathTrans);
            PauseTransition pause = new PauseTransition(Duration.millis(10));
            seqtrans.getChildren().add(pause);
            return seqtrans;
        } // Nothing Happens; CHANGED: a 10 millisecond pause transition is played, prevents that the player disappears until the next move

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
                    if (vertexData.getType() == Field.fieldType.CrossoverField) { // Triggers when Player leaves the Field
                        PathTransition standartPathTransition = new PathTransition();
                        standartPathTransition.setPath(standartPath);
                        standartPathTransition.setDuration(Duration.millis(standartDurration));
                        seqtrans.getChildren().add(standartPathTransition);

                        currentPlayer.setCurrentField(this.hopCountTraversal(currentPlayer.getCurrentField(), hops)); // set the current field of the player to the crossover field

                        if (i == 0)
                            seqtrans = null; // this addresses problems that arise when a player lands directly on a crossover, in selectPathEvent it is checked if seqtrans is null

                        gameBord.selectPathEvent(hops - i, currentPlayer, seqtrans); // Calls the event for the player to select a path
                        return null;
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


                    // May fix the end of Graph endles Walk bug
                    if (seqtrans.getChildren().size() == 0) {
                        seqtrans.getChildren().remove(pathTrans);
                        PauseTransition pause = new PauseTransition(Duration.millis(10));
                        seqtrans.getChildren().add(pause);
                    }

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

    */

    /*
    public SequentialTransition crossingMoveAnimationAndMove(Player currentPlayer, edgeType edgetype, int animationOffsetX, int animationOffsetY, int fieldsToMove, GameBoard gameBord)
    {
        Field root = currentPlayer.getCurrentField();

        SequentialTransition seqtrans = new SequentialTransition();

        PathTransition pathTrans = new PathTransition();
        pathTrans.setPath(new Line(root.getX() - animationOffsetX, root.getY() - animationOffsetY, root.getX() - animationOffsetX, root.getY() - animationOffsetY));
        pathTrans.setDuration(Duration.ZERO);
        seqtrans.getChildren().add(pathTrans);
        // Adds a basic non Moving Transsition. This allows the returning of the seqtrans at any point after the code block above.


        if (root.getType() != Field.fieldType.CrossoverField) { // The Methode got called on the wring field
            seqtrans.getChildren().remove(pathTrans);
            PauseTransition pause = new PauseTransition(Duration.millis(10));
            seqtrans.getChildren().add(pause);
            return seqtrans;
        }

        for (Edge edge : forwardGraph.getAdjacenctVertexEdges(root)) {
            Weight tmpWeight = (Weight) edge.getWeight();
            if (tmpWeight.getType() == edgetype) {
                Field returnField = (edge.getSource() == root) ? (Field) edge.getTarget() : (Field) edge.getSource();
                if (returnField.getType() != Field.fieldType.LadderField) {  // If it is not a ladder field
                    currentPlayer.setCurrentField(returnField); // Sets new Player Field

                    PathTransition normalPath = new PathTransition();
                    normalPath.setPath(new Path(new LineTo(returnField.getX() - animationOffsetX, returnField.getY() - animationOffsetY)));
                    normalPath.setDuration(new Duration((Integer) tmpWeight.getData()));
                    seqtrans.getChildren().add(normalPath);

                    hopCountTraversal(currentPlayer.getCurrentField(), fieldsToMove - 1);  // Because the code below does not chanche the Field currently
                    return getAnimationPathFromGraph(currentPlayer.getCurrentField(), fieldsToMove - 1, animationOffsetX, animationOffsetY, currentPlayer, gameBord, seqtrans);    // Normal Field
                }
                if (fieldsToMove == 1) {
                    for (Edge item : forwardGraph.getAdjacenctVertexEdges(returnField)) {         // In case the first field after the Crossing is a ladder / snake
                        Weight tmpWeightLadder = (Weight) item.getWeight();
                        if (tmpWeightLadder.getType() == edgeType.LadderEdge) {
                            Field temp = (item.getSource() == returnField) ? (Field) item.getTarget() : (Field) item.getSource();
                            currentPlayer.setCurrentField(temp);

                            PathTransition LadderPath = new PathTransition();
                            LadderPath.setPath(new Path(new LineTo(temp.getX() - animationOffsetX, temp.getY() - animationOffsetY)));
                            LadderPath.setDuration(new Duration((Integer) tmpWeightLadder.getData()));
                            seqtrans.getChildren().add(LadderPath);

                            return seqtrans;
                        }
                    }
                }
            }
        }

        seqtrans.getChildren().remove(pathTrans);
        PauseTransition pause = new PauseTransition(Duration.millis(10));
        seqtrans.getChildren().add(pause);
        return seqtrans; // An error is accured. No Crossing should have no two crossing Edges. Returning Root for damage minomizaton.
    }
    */

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