package com.example.theos;

import com.example.theos.BordGameGraph.BoardGraph;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TheOs extends javafx.application.Application {

    /* scene width and height are used for scene size and field class
       (–> x and y parameters of field constructor are in percent, useful for resizing)
        */
    static final int SCENE_WIDTH = 1422;
    static final int SCENE_HEIGHT = 800;

    static boolean negativeInputForTesting = false;

    // colors for texts (in diceUI)
    final static Color BROWN = Color.rgb(120, 98, 68);
    final static Color MINT_GREEN = Color.rgb(63, 139, 88);

    // accessing the custom font fix: https://stackoverflow.com/questions/30245085/javafx-embed-custom-font-not-working
    static final Font CUSTOM_FONT_VARELA = Font.loadFont(TheOs.class.getClassLoader().getResourceAsStream("fonts/VarelaRound-Regular.ttf"), 22);
    static final Font CUSTOM_FONT_CAVEAT = Font.loadFont(TheOs.class.getClassLoader().getResourceAsStream("fonts/Caveat-SemiBold.ttf"), 25);

    static boolean waitingForUserInput = true;
    static int currentPlayerIndex = 0;

    @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml")); // bis jetzt war noch kein FXML notwendig

        GameBoard gameBoard = new GameBoard();

        // Initialize game board Graph


        // Initialize gameboard Graph
        List<Field> fieldList = new ArrayList<>();

        // Fields for spawn area
        Field spawn1 = new Field(Field.fieldType.NormalField, 4.8, 60.1); // 1 = Diva O'Hara
        Field spawn2 = new Field(Field.fieldType.NormalField, 8.3, 56.6); // 2 = Y'Olanda
        Field spawn3 = new Field(Field.fieldType.NormalField, 11.6, 53.1); // 3 = Kidd'O
        Field spawn4 = new Field(Field.fieldType.NormalField, 8.3, 63.9); // 4 = Mint'O Lint
        Field spawn5 = new Field(Field.fieldType.NormalField, 11.9, 60.3); // 5 = Brooke O'Let
        Field spawn6 = new Field(Field.fieldType.NormalField, 15.2, 56.9); // 6 = O'Fitz

        // Fields on main path
        Field field101 = new Field(Field.fieldType.NormalField, 17.3, 50.1);
        Field field102 = new Field(Field.fieldType.NormalField, 19.2, 48.4);
        Field field103 = new Field(Field.fieldType.NormalField, 21.4, 47.9);
        Field field104 = new Field(Field.fieldType.NormalField, 23.7, 48.5);
        Field field105 = new Field(Field.fieldType.NormalField, 25.1, 51.3);
        Field field106 = new Field(Field.fieldType.NormalField, 25.1, 55.5);
        Field field107 = new Field(Field.fieldType.LadderField, 25.1, 60.1);
        Field field108 = new Field(Field.fieldType.NormalField, 26.1, 63.6);
        Field field109 = new Field(Field.fieldType.NormalField, 28, 66);
        Field field110 = new Field(Field.fieldType.NormalField, 30.4, 67.4);

        Field field111 = new Field(Field.fieldType.NormalField, 32.9, 67.6);
        Field field112 = new Field(Field.fieldType.NormalField, 35.4, 67);
        Field field113 = new Field(Field.fieldType.NormalField, 37.7, 65.3);
        Field field114 = new Field(Field.fieldType.NormalField, 39.6, 62.7);
        Field field115 = new Field(Field.fieldType.NormalField, 40.8, 58.7);
        Field field116 = new Field(Field.fieldType.NormalField, 40.9, 54.1);
        Field field117 = new Field(Field.fieldType.LadderField, 39.8, 50.2);
        Field field118 = new Field(Field.fieldType.NormalField, 38.4, 47);
        Field field119 = new Field(Field.fieldType.NormalField, 36.3, 44.8);
        Field field120 = new Field(Field.fieldType.NormalField, 34, 42.9);

        Field field121 = new Field(Field.fieldType.LadderField, 31.4, 41.1);
        Field field122 = new Field(Field.fieldType.NormalField, 28.7, 39.4);
        Field field123 = new Field(Field.fieldType.NormalField, 26.2, 37.9);
        Field field124 = new Field(Field.fieldType.NormalField, 23.7, 36.4);
        Field field125 = new Field(Field.fieldType.LadderField, 21.2, 34.7);
        Field field126 = new Field(Field.fieldType.NormalField, 18.7, 33.1);
        Field field127 = new Field(Field.fieldType.LadderField, 16.6, 30.9);
        Field field128 = new Field(Field.fieldType.NormalField, 15.1, 28.4);
        Field field129 = new Field(Field.fieldType.NormalField, 14.1, 25.3);
        Field field130 = new Field(Field.fieldType.NormalField, 13.6, 21.3);

        Field field131 = new Field(Field.fieldType.NormalField, 14.3, 17.1);
        Field field132 = new Field(Field.fieldType.NormalField, 15.7, 14);
        Field field133 = new Field(Field.fieldType.NormalField, 17.7, 12);
        Field field134 = new Field(Field.fieldType.NormalField, 19.9, 10.9);
        Field field135 = new Field(Field.fieldType.NormalField, 21.9, 11.3);
        Field field136 = new Field(Field.fieldType.NormalField, 23.6, 12.4);
        Field field137 = new Field(Field.fieldType.NormalField, 25.1, 14.4);
        Field field138 = new Field(Field.fieldType.NormalField, 26.3, 17);
        Field field139 = new Field(Field.fieldType.NormalField, 27.2, 19.9);
        Field field140 = new Field(Field.fieldType.NormalField, 28.1, 23.2);

        Field field141 = new Field(Field.fieldType.NormalField, 29, 26.3);
        Field field142 = new Field(Field.fieldType.NormalField, 30.2, 29.2);
        Field field143 = new Field(Field.fieldType.LadderField, 31.7, 31.8);
        Field field144 = new Field(Field.fieldType.NormalField, 33.5, 34.1);
        Field field145 = new Field(Field.fieldType.NormalField, 35.7, 35.5);
        Field field146 = new Field(Field.fieldType.NormalField, 38.2, 36.6);
        Field field147 = new Field(Field.fieldType.NormalField, 40.8, 36.9);
        Field field148 = new Field(Field.fieldType.NormalField, 43.6, 36.4);
        Field field149 = new Field(Field.fieldType.CrossoverField, 45.9, 34.5);
        Field field150 = new Field(Field.fieldType.NormalField, 43.8, 32.7);

        Field field151 = new Field(Field.fieldType.NormalField, 42, 30.7);
        Field field152 = new Field(Field.fieldType.NormalField, 40.1, 28.9);
        Field field153 = new Field(Field.fieldType.NormalField, 38.4, 27.1);
        Field field154 = new Field(Field.fieldType.LadderField, 36.6, 25.1);
        Field field155 = new Field(Field.fieldType.NormalField, 34.9, 23.3);
        Field field156 = new Field(Field.fieldType.NormalField, 33.5, 21.1);
        Field field157 = new Field(Field.fieldType.NormalField, 32.5, 18.2);
        Field field158 = new Field(Field.fieldType.NormalField, 32.2, 14.6);
        Field field159 = new Field(Field.fieldType.NormalField, 32.8, 11.3);
        Field field160 = new Field(Field.fieldType.SpecialChargeField, 34.1, 8.4);

        Field field161 = new Field(Field.fieldType.NormalField, 35.9, 6.8);
        Field field162 = new Field(Field.fieldType.NormalField, 37.8, 6.1);
        Field field163 = new Field(Field.fieldType.NormalField, 39.6, 6.4);
        Field field164 = new Field(Field.fieldType.NormalField, 41.4, 7.6);
        Field field165 = new Field(Field.fieldType.NormalField, 43, 9.1);
        Field field166 = new Field(Field.fieldType.NormalField, 44.7, 10.8);
        Field field167 = new Field(Field.fieldType.NormalField, 46.6, 12.8);
        Field field168 = new Field(Field.fieldType.NormalField, 48.5, 14.8);
        Field field169 = new Field(Field.fieldType.NormalField, 50.3, 16.6);
        Field field170 = new Field(Field.fieldType.LadderField, 52.1, 18.3);

        Field field171 = new Field(Field.fieldType.LadderField, 53.8, 20);
        Field field172 = new Field(Field.fieldType.NormalField, 55.5, 21.9);
        Field field173 = new Field(Field.fieldType.NormalField, 57.1, 23.6);
        Field field174 = new Field(Field.fieldType.NormalField, 59.2, 21.6);
        Field field175 = new Field(Field.fieldType.NormalField, 61.5, 19.3);
        Field field176 = new Field(Field.fieldType.NormalField, 63.6, 17);
        Field field177 = new Field(Field.fieldType.LadderField, 66.1, 14.9);
        Field field178 = new Field(Field.fieldType.NormalField, 69, 14.5);
        Field field179 = new Field(Field.fieldType.NormalField, 71.1, 15.8);
        Field field180 = new Field(Field.fieldType.NormalField, 72.8, 18.6);

        Field field181 = new Field(Field.fieldType.NormalField, 73.6, 22.2);
        Field field182 = new Field(Field.fieldType.NormalField, 73.4, 26);
        Field field183 = new Field(Field.fieldType.NormalField, 72.4, 29.7);
        Field field184 = new Field(Field.fieldType.LadderField, 70.6, 32.5);
        Field field185 = new Field(Field.fieldType.NormalField, 68.4, 34.9);
        Field field186 = new Field(Field.fieldType.NormalField, 66.2, 37.1);
        Field field187 = new Field(Field.fieldType.NormalField, 64.2, 39.1);
        Field field188 = new Field(Field.fieldType.NormalField, 66.2, 41.2);
        Field field189 = new Field(Field.fieldType.NormalField, 68.3, 43.4);
        Field field190 = new Field(Field.fieldType.NormalField, 70.5, 45.5);

        Field field191 = new Field(Field.fieldType.NormalField, 72.8, 46.8);
        Field field192 = new Field(Field.fieldType.NormalField, 75.1, 47);
        Field field193 = new Field(Field.fieldType.NormalField, 77.3, 46.4);
        Field field194 = new Field(Field.fieldType.LadderField, 79.5, 44.7);
        Field field195 = new Field(Field.fieldType.NormalField, 81.1, 42.3);

        fieldList.add(field101);
        fieldList.add(field102);
        fieldList.add(field103);
        fieldList.add(field104);
        fieldList.add(field105);
        fieldList.add(field106);
        fieldList.add(field107);
        fieldList.add(field108);
        fieldList.add(field109);
        fieldList.add(field110);

        fieldList.add(field111);
        fieldList.add(field112);
        fieldList.add(field113);
        fieldList.add(field114);
        fieldList.add(field115);
        fieldList.add(field116);
        fieldList.add(field117);
        fieldList.add(field118);
        fieldList.add(field119);
        fieldList.add(field120);

        fieldList.add(field121);
        fieldList.add(field122);
        fieldList.add(field123);
        fieldList.add(field124);
        fieldList.add(field125);
        fieldList.add(field126);
        fieldList.add(field127);
        fieldList.add(field128);
        fieldList.add(field129);
        fieldList.add(field130);

        fieldList.add(field131);
        fieldList.add(field132);
        fieldList.add(field133);
        fieldList.add(field134);
        fieldList.add(field135);
        fieldList.add(field136);
        fieldList.add(field137);
        fieldList.add(field138);
        fieldList.add(field139);
        fieldList.add(field140);

        fieldList.add(field141);
        fieldList.add(field142);
        fieldList.add(field143);
        fieldList.add(field144);
        fieldList.add(field145);
        fieldList.add(field146);
        fieldList.add(field147);
        fieldList.add(field148);
        fieldList.add(field149);
        fieldList.add(field150);

        fieldList.add(field151);
        fieldList.add(field152);
        fieldList.add(field153);
        fieldList.add(field154);
        fieldList.add(field155);
        fieldList.add(field156);
        fieldList.add(field157);
        fieldList.add(field158);
        fieldList.add(field159);
        fieldList.add(field160);

        fieldList.add(field161);
        fieldList.add(field162);
        fieldList.add(field163);
        fieldList.add(field164);
        fieldList.add(field165);
        fieldList.add(field166);
        fieldList.add(field167);
        fieldList.add(field168);
        fieldList.add(field169);
        fieldList.add(field170);

        fieldList.add(field171);
        fieldList.add(field172);
        fieldList.add(field173);
        fieldList.add(field174);
        fieldList.add(field175);
        fieldList.add(field176);
        fieldList.add(field177);
        fieldList.add(field178);
        fieldList.add(field179);
        fieldList.add(field180);

        fieldList.add(field181);
        fieldList.add(field182);
        fieldList.add(field183);
        fieldList.add(field184);
        fieldList.add(field185);
        fieldList.add(field186);
        fieldList.add(field187);
        fieldList.add(field188);
        fieldList.add(field189);
        fieldList.add(field190);

        fieldList.add(field191);
        fieldList.add(field192);
        fieldList.add(field193);
        fieldList.add(field194);
        fieldList.add(field195);

        // Fields on shortest path
        Field field201 = new Field(Field.fieldType.NormalField, 48.2, 37.2);
        Field field202 = new Field(Field.fieldType.NormalField, 50.7, 39.6);
        Field field203 = new Field(Field.fieldType.NormalField, 53, 42);
        Field field204 = new Field(Field.fieldType.NormalField, 51.1, 44.7);
        Field field205 = new Field(Field.fieldType.SpecialChargeField, 49.9, 48.5);
        Field field206 = new Field(Field.fieldType.NormalField, 49.5, 53.3);
        Field field207 = new Field(Field.fieldType.NormalField, 50.3, 57.5);
        Field field208 = new Field(Field.fieldType.NormalField, 51.8, 60.7);
        Field field209 = new Field(Field.fieldType.CrossoverField, 53.8, 62.5);
        Field field210 = new Field(Field.fieldType.LadderField, 55.9, 63.1);

        Field field211 = new Field(Field.fieldType.SpecialChargeField, 58.4, 62.2);
        Field field212 = new Field(Field.fieldType.LadderField, 60.4, 59.5);
        Field field213 = new Field(Field.fieldType.LadderField, 61.7, 55.7);
        Field field214 = new Field(Field.fieldType.SpecialChargeField, 62, 51.4);
        Field field215 = new Field(Field.fieldType.LadderField, 61.4, 47.2);
        Field field216 = new Field(Field.fieldType.NormalField, 59.9, 43.7);
        Field field217 = new Field(Field.fieldType.NormalField, 57.8, 41.1);
        Field field218 = new Field(Field.fieldType.NormalField, 55.9, 38.3);
        Field field219 = new Field(Field.fieldType.NormalField, 56.1, 34.3);
        Field field220 = new Field(Field.fieldType.NormalField, 58.3, 33.4);

        Field field221 = new Field(Field.fieldType.NormalField, 60.2, 35.1);
        Field field222 = new Field(Field.fieldType.NormalField, 62.2, 37.1);

        fieldList.add(field201);
        fieldList.add(field202);
        fieldList.add(field203);
        fieldList.add(field204);
        fieldList.add(field205);
        fieldList.add(field206);
        fieldList.add(field207);
        fieldList.add(field208);
        fieldList.add(field209);
        fieldList.add(field210);

        fieldList.add(field211);
        fieldList.add(field212);
        fieldList.add(field213);
        fieldList.add(field214);
        fieldList.add(field215);
        fieldList.add(field216);
        fieldList.add(field217);
        fieldList.add(field218);
        fieldList.add(field219);
        fieldList.add(field220);

        fieldList.add(field221);
        fieldList.add(field222);

        // Fields on longest path
        Field field301 = new Field(Field.fieldType.NormalField, 52.5, 67.1);
        Field field302 = new Field(Field.fieldType.NormalField, 51.1, 71.7);
        Field field303 = new Field(Field.fieldType.NormalField, 50.3, 76.3);
        Field field304 = new Field(Field.fieldType.NormalField, 50, 80.7);
        Field field305 = new Field(Field.fieldType.NormalField, 50.5, 85.6);
        Field field306 = new Field(Field.fieldType.NormalField, 51.9, 89.6);
        Field field307 = new Field(Field.fieldType.SpecialChargeField, 54.2, 92.2);
        Field field308 = new Field(Field.fieldType.NormalField, 57, 93.4);
        Field field309 = new Field(Field.fieldType.NormalField, 59.6, 93.3);
        Field field310 = new Field(Field.fieldType.NormalField, 62.3, 92.1);

        Field field311 = new Field(Field.fieldType.NormalField, 64.6, 90.3);
        Field field312 = new Field(Field.fieldType.LadderField, 66.4, 87.1);
        Field field313 = new Field(Field.fieldType.NormalField, 67.2, 82.9);
        Field field314 = new Field(Field.fieldType.LadderField, 67.2, 78.4);
        Field field315 = new Field(Field.fieldType.NormalField, 66.6, 73.9);
        Field field316 = new Field(Field.fieldType.LadderField, 65.8, 69.6);
        Field field317 = new Field(Field.fieldType.NormalField, 65.7, 65.3);
        Field field318 = new Field(Field.fieldType.NormalField, 66.3, 61.4);
        Field field319 = new Field(Field.fieldType.NormalField, 67.6, 58);
        Field field320 = new Field(Field.fieldType.NormalField, 69.6, 55.8);

        Field field321 = new Field(Field.fieldType.LadderField, 71.9, 54.9);
        Field field322 = new Field(Field.fieldType.NormalField, 74.4, 55.3);
        Field field323 = new Field(Field.fieldType.NormalField, 76.7, 57.1);
        Field field324 = new Field(Field.fieldType.LadderField, 78.6, 60.5);
        Field field325 = new Field(Field.fieldType.NormalField, 79.6, 64.8);
        Field field326 = new Field(Field.fieldType.NormalField, 79.8, 69.1);
        Field field327 = new Field(Field.fieldType.LadderField, 79.7, 73.7);
        Field field328 = new Field(Field.fieldType.NormalField, 79.7, 78.6);
        Field field329 = new Field(Field.fieldType.NormalField, 80.3, 83);
        Field field330 = new Field(Field.fieldType.NormalField, 81.5, 87.2);

        Field field331 = new Field(Field.fieldType.NormalField, 83.5, 90.4);
        Field field332 = new Field(Field.fieldType.LadderField, 86.7, 91.8);
        Field field333 = new Field(Field.fieldType.NormalField, 89.8, 90.8);
        Field field334 = new Field(Field.fieldType.NormalField, 92.1, 88.5);
        Field field335 = new Field(Field.fieldType.NormalField, 93.7, 85.1);
        Field field336 = new Field(Field.fieldType.NormalField, 94.6, 81.3);
        Field field337 = new Field(Field.fieldType.NormalField, 94.9, 77.4);
        Field field338 = new Field(Field.fieldType.NormalField, 94.3, 73.7);
        Field field339 = new Field(Field.fieldType.NormalField, 93.3, 70.3);
        Field field340 = new Field(Field.fieldType.LadderField, 91.9, 67.6);

        Field field341 = new Field(Field.fieldType.NormalField, 90.1, 65.4);
        Field field342 = new Field(Field.fieldType.NormalField, 88.2, 63.5);
        Field field343 = new Field(Field.fieldType.NormalField, 86.4, 61.6);
        Field field344 = new Field(Field.fieldType.NormalField, 84.3, 59.4);
        Field field345 = new Field(Field.fieldType.NormalField, 82.5, 57.1);
        Field field346 = new Field(Field.fieldType.LadderField, 82, 53.2);
        Field field347 = new Field(Field.fieldType.NormalField, 82.7, 49.7);

        fieldList.add(field301);
        fieldList.add(field302);
        fieldList.add(field303);
        fieldList.add(field304);
        fieldList.add(field305);
        fieldList.add(field306);
        fieldList.add(field307);
        fieldList.add(field308);
        fieldList.add(field309);
        fieldList.add(field310);

        fieldList.add(field311);
        fieldList.add(field312);
        fieldList.add(field313);
        fieldList.add(field314);
        fieldList.add(field315);
        fieldList.add(field316);
        fieldList.add(field317);
        fieldList.add(field318);
        fieldList.add(field319);
        fieldList.add(field320);

        fieldList.add(field321);
        fieldList.add(field322);
        fieldList.add(field323);
        fieldList.add(field324);
        fieldList.add(field325);
        fieldList.add(field326);
        fieldList.add(field327);
        fieldList.add(field328);
        fieldList.add(field329);
        fieldList.add(field330);

        fieldList.add(field331);
        fieldList.add(field332);
        fieldList.add(field333);
        fieldList.add(field334);
        fieldList.add(field335);
        fieldList.add(field336);
        fieldList.add(field337);
        fieldList.add(field338);
        fieldList.add(field339);
        fieldList.add(field340);

        fieldList.add(field341);
        fieldList.add(field342);
        fieldList.add(field343);
        fieldList.add(field344);
        fieldList.add(field345);
        fieldList.add(field346);
        fieldList.add(field347);

        // Fields for winning area
        Field win1 = new Field(Field.fieldType.NormalField, 82.4, 29.1);
        Field win2 = new Field(Field.fieldType.NormalField, 84.5, 31.3);
        Field win3 = new Field(Field.fieldType.NormalField, 85.1, 26.1);
        Field win4 = new Field(Field.fieldType.NormalField, 86.4, 28.7);
        Field win5 = new Field(Field.fieldType.NormalField, 88.4, 26.5);
        Field win6 = new Field(Field.fieldType.NormalField, 88.9, 31.1);

        for (int i = 0; i < fieldList.size(); i++) {
            gameBoard.getBoardGraph().addVertex(fieldList.get(i));
        }

        for (int i = 0; i < fieldList.size() - 1; i++) {
            gameBoard.getBoardGraph().addOneDirectionalEdge(fieldList.get(i), fieldList.get(i + 1), 500, BoardGraph.edgeType.NormalEdge);
        }

        // Add normal edges from spawn area to first field
        gameBoard.getBoardGraph().addVertex(spawn1);
        gameBoard.getBoardGraph().addVertex(spawn2);
        gameBoard.getBoardGraph().addVertex(spawn3);
        gameBoard.getBoardGraph().addVertex(spawn4);
        gameBoard.getBoardGraph().addVertex(spawn5);
        gameBoard.getBoardGraph().addVertex(spawn6);

        gameBoard.getBoardGraph().addOneDirectionalEdge(spawn1, field101, 500, BoardGraph.edgeType.NormalEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(spawn2, field101, 500, BoardGraph.edgeType.NormalEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(spawn3, field101, 500, BoardGraph.edgeType.NormalEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(spawn4, field101, 500, BoardGraph.edgeType.NormalEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(spawn5, field101, 500, BoardGraph.edgeType.NormalEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(spawn6, field101, 500, BoardGraph.edgeType.NormalEdge);

        // Add ladder edges between the necessary fields (going from the main path)
        gameBoard.getBoardGraph().addOneDirectionalEdge(field107, field117, 2500, BoardGraph.edgeType.LadderEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field121, field104, 2500, BoardGraph.edgeType.LadderEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field125, field140, 2500, BoardGraph.edgeType.LadderEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field127, field135, 2500, BoardGraph.edgeType.LadderEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field143, field124, 2500, BoardGraph.edgeType.LadderEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field154, field165, 2500, BoardGraph.edgeType.LadderEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field170, field202, 2500, BoardGraph.edgeType.LadderEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field171, field203, 2500, BoardGraph.edgeType.LadderEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field177, field182, 2500, BoardGraph.edgeType.LadderEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field184, field174, 2500, BoardGraph.edgeType.LadderEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field194, field322, 2500, BoardGraph.edgeType.LadderEdge);

        // Add ladder edges between the necessary fields (going from the shortest path)
        gameBoard.getBoardGraph().addOneDirectionalEdge(field210, field203, 2500, BoardGraph.edgeType.LadderEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field212, field204, 2500, BoardGraph.edgeType.LadderEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field213, field204, 2500, BoardGraph.edgeType.LadderEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field215, field206, 2500, BoardGraph.edgeType.LadderEdge);

        // Add ladder edges between the necessary fields (going from the longest path)
        gameBoard.getBoardGraph().addOneDirectionalEdge(field312, field304, 2500, BoardGraph.edgeType.LadderEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field314, field330, 2500, BoardGraph.edgeType.LadderEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field316, field301, 2500, BoardGraph.edgeType.LadderEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field321, field189, 2500, BoardGraph.edgeType.LadderEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field324, field318, 2500, BoardGraph.edgeType.LadderEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field327, field344, 2500, BoardGraph.edgeType.LadderEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field332, field337, 2500, BoardGraph.edgeType.LadderEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field340, field328, 2500, BoardGraph.edgeType.LadderEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field346, field322, 2500, BoardGraph.edgeType.LadderEdge);

        // Add normal edges from last fields to winning area
        gameBoard.getBoardGraph().addVertex(win1);
        gameBoard.getBoardGraph().addVertex(win2);
        gameBoard.getBoardGraph().addVertex(win3);
        gameBoard.getBoardGraph().addVertex(win4);
        gameBoard.getBoardGraph().addVertex(win5);
        gameBoard.getBoardGraph().addVertex(win6);

        gameBoard.getBoardGraph().addOneDirectionalEdge(field195, win1, 500, BoardGraph.edgeType.NormalEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field347, win1, 500, BoardGraph.edgeType.NormalEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field195, win2, 500, BoardGraph.edgeType.NormalEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field347, win2, 500, BoardGraph.edgeType.NormalEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field195, win3, 500, BoardGraph.edgeType.NormalEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field347, win3, 500, BoardGraph.edgeType.NormalEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field195, win4, 500, BoardGraph.edgeType.NormalEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field347, win4, 500, BoardGraph.edgeType.NormalEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field195, win5, 500, BoardGraph.edgeType.NormalEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field347, win5, 500, BoardGraph.edgeType.NormalEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field195, win6, 500, BoardGraph.edgeType.NormalEdge);
        gameBoard.getBoardGraph().addOneDirectionalEdge(field347, win6, 500, BoardGraph.edgeType.NormalEdge);

        // initializing gameBoard Graph done; should we move this into a method?

        Circle playerTestView = new Circle(5); // just used for testing; better visualizes the exact path of player
        playerTestView.setCenterX(field101.getX());
        playerTestView.setCenterY(field101.getY());

        int[] mintOLintDie = {1, 1, 2, 2, 2, 7};
        Player player1 = new Player("Mint’O Lint", mintOLintDie, Paths.get("images"));

        ImageView sprite1 = new ImageView(new Image("images/sprites/Sprites_1.png"));
        sprite1.setPreserveRatio(true);
        sprite1.setFitWidth(54);

        ImageView sprite2 = new ImageView(new Image("images/sprites/Sprites_2.png"));
        sprite2.setPreserveRatio(true);
        sprite2.setFitWidth(54);

        ImageView sprite3 = new ImageView(new Image("images/sprites/Sprites_3.png"));
        sprite3.setPreserveRatio(true);
        sprite3.setFitWidth(54);

        ImageView sprite4 = new ImageView(new Image("images/sprites/Sprites_4.png"));
        sprite4.setPreserveRatio(true);
        sprite4.setFitWidth(54);

        ImageView sprite5 = new ImageView(new Image("images/sprites/Sprites_5.png"));
        sprite5.setPreserveRatio(true);
        sprite5.setFitWidth(54);

        ImageView sprite6 = new ImageView(new Image("images/sprites/Sprites_6.png"));
        sprite6.setPreserveRatio(true);
        sprite6.setFitWidth(54);

        player1.setImageView(sprite4);
        player1.setCurrentField(spawn4);
        player1.getImageView().setX(spawn4.getX() - 27);
        player1.getImageView().setY(spawn4.getY() - 27 - 15);


        player1.playIdle();
        //player1.playWalk();
        //player1.playSlip();

        /* Überlegung Group und Pane als Parent Element für Scene:
        Group setzt kein fixes Layout für Elemente vor, daher kommen da die sich bewegenden Elemente wie Spieler rein

        Pane dient als Root Element fürs Layout, darin kommt die Group mit den Spielern (dz. Kreise)
        Pane ermöglicht, im Gegensatz zu Group, dass ein Hintergrundbild gesetzt wird. Und vllt ist es später auch praktisch (bzw. ein Subtyp von Pane)
        für zusätliche Layout-Elemente wie Spieler- und Würfelanzeige.
         */

        Group group = new Group(player1.getImageView());

        Pane root = new Pane(group);

        root.setBackground(gameBoard.getBackground()); // setBackground method needs Background object, not BackgroundImage

        // Placing the DiceUI of the GameBoard on the screen
        gameBoard.getDiceUI().setTranslateX(40);
        gameBoard.getDiceUI().setTranslateY(800 - 215);

        root.getChildren().addAll(gameBoard.getDiceUI());

        Scene inGameScene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT); // ich hab die Größe erstmal auf 1422x800 eingestellt da 1920x1080 für meinen Laptop-Bildschirm zu groß war
        Scene playerSelectScene = PlayerSelectionScreen.createPlayerSelectionScreen();

        inGameScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.A) {
                player1.playIdle();
            }
            if (event.getCode() == KeyCode.S) {
                player1.playSwim();
            }
            if (waitingForUserInput) {
                if (event.getCode() == KeyCode.UP) {
                    gameBoard.getDiceUI().selectNormalDie();
                }
                // Special die charges are checked here!
                if (event.getCode() == KeyCode.DOWN && player1.getSpecialDie().getCharge() > 0) {
                    gameBoard.getDiceUI().selectSpecialDie();
                }
                if (event.getCode() == KeyCode.SPACE) {
                    waitingForUserInput = false;
                    gameBoard.playerTurn(player1);
                }
            }
        });

        inGameScene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                stage.setScene(playerSelectScene);
            }
        });

        playerSelectScene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                stage.setScene(inGameScene);
            }
        });

        stage.setTitle("The O’s");
        stage.setScene(inGameScene);
        stage.setResizable(false); // daweil mal ohne resizable
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}