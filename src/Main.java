import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

//ToDo: Sort out collision Logic
//ToDo: Implement collision Calculations


public class Main {
    private static int accuracy;

    private static Block leftBlock, rightBlock;
    public static int collisions = 0;

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        boolean done = false, broke = false;
        while (!done) {
            try {
                broke = false;
                System.out.println("How many decimal places do you want: ");
                try {
                    accuracy = Integer.valueOf(br.readLine());
                } catch (NumberFormatException n) {
                    broke = true;
                    System.out.println("Error, input a number");
                }
            } catch (IOException e) {
                broke = true;
                System.out.println("Error, no input");
            }
            if (!broke) {
                done = true;
            }
        }

        new JFXPanel();
        Platform.runLater(Main::launch);
    }

    private static int bonkNo = 0;
    private static DecimalFormat df;

    private static void close() {
        System.out.println("No. of collisions: " + collisions);
        System.exit(0);
    }

    private static void launch() {
        Stage stage = new Stage();
        stage.setHeight(650);
        stage.setWidth(500);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.show();

        VBox vb = new VBox();
        Scene scene = new Scene(vb);
        stage.setScene(scene);

        scene.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ESCAPE)) close();
        });

        df = new DecimalFormat("#.00000");

        Canvas blocksCanvas = new Canvas();
        blocksCanvas.setWidth(500);
        blocksCanvas.setHeight(500);
        vb.getChildren().add(blocksCanvas);

        GraphicsContext blocks = blocksCanvas.getGraphicsContext2D();

        Canvas numbersCanvas = new Canvas();
        numbersCanvas.setWidth(500);
        numbersCanvas.setHeight(150);
        vb.getChildren().add(numbersCanvas);

        GraphicsContext numbers = numbersCanvas.getGraphicsContext2D();
        numbers.setFont(new Font("Comic Sans MS", 50));
        numbers.setLineWidth(2);


        leftBlock = new Block(1, 0, 60, true);

        rightBlock = new Block(1 * (long) Math.pow(10, accuracy), -0.25, 200, false);

        AnimationTimer at = new AnimationTimer() {
            private long last = 0;

            @Override
            public void handle(long now) {
                //if(now - last >= 16_000_000) {
                    update(leftBlock,rightBlock);
                    drawBlocks(blocks,leftBlock,rightBlock);
                    drawNumbers(numbers);
                  //  last = now;
                //}
            }
        };
        at.start();

    }

    static void closeTime() {
        Task<Void> ending = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(3000);
                } catch(InterruptedException ignored){
                }
                return null;
            }
        };
        ending.setOnSucceeded(event -> close());
        new Thread(ending).start();
    }

    static void update(Block a, Block b) {
        if(a.oldSpeed() >= 0 && a.oldSpeed() < b.oldSpeed()) closeTime();
        for(int i = 0; i< 10000; i++) {
            a.update();
            b.update();
            if ((a.getD() + 0.5 > b.getD() - 0.5 && a.getD()+0.5<b.getPos()+0.5)||(a.getD()-0.5<b.getD()+0.5&&a.getD()+0.5>b.getD()-0.5)) {
                bonk(a, b);
            }
            if ((100.05>a.getPos() -0.5 && 100+0.5<a.getPos()+0.5)||(100-0.5<b.getPos()+0.5&&100-0.5>b.getPos()-0.5)) {
                wallBonk(a);
            }
        }
    }

    static void bonk(Block a, Block b) {
            collisions++;
            double u1 = a.oldSpeed();
            double u2 = b.oldSpeed();
            double m1 = a.getMass();
            double m2 = b.getMass();

            a.newSpeed(u1*((m1-m2)/(m1+m2)) + u2 * (2d*m2/(m1+m2)));
            b.newSpeed(u1*(2d*m1/(m1+m2))-u2*((m1-m2)/(m1+m2)));
    }

    static void wallBonk(Block b) {
        collisions++;
        b.newSpeed(-b.oldSpeed());
    }

    static void drawBlocks(GraphicsContext gc, Block left, Block right) {
        gc.clearRect(0,0,500,500);
        gc.strokeLine(100,350,400,350);
        gc.strokeLine(100,100,100,350);

        // Left Block
        double leftBlockWidth = left.getBlockWidth();
        gc.fillRect(left.getPos(),350-leftBlockWidth,leftBlockWidth,leftBlockWidth);
        gc.strokeLine(100,200,100 + left.getD(),200);

        // Right Block
        double rightBlockWidth = right.getBlockWidth();
        gc.fillRect(right.getPos(),350-rightBlockWidth,rightBlockWidth,rightBlockWidth);

        gc.strokeLine(100,250,right.getD()+100,250);
    }

    static void drawNumbers(GraphicsContext gc) { //Text maxwidth must be 250, text height is 50

        gc.clearRect(0,0,500,150);
        gc.strokeText(df.format(leftBlock.oldSpeed()),0,50,250);
        gc.strokeText(df.format(rightBlock.oldSpeed()),250,50,250);
        gc.strokeText(String.valueOf(collisions),0,100);
    }
}
