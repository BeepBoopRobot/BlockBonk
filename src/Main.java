import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static int accuracy;

    private static Block leftBlock, rightBlock;

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        boolean done = false, broke = false;
        while (!done) {
            try {
                broke = false;
                System.out.println("How many decimal places do you want: ");
                try {
                    int f = Integer.valueOf(br.readLine());
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
            if (ke.getCode().equals(KeyCode.ESCAPE)) System.exit(0);
        });

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

        leftBlock = new Block(10, 0, 30, false);

        rightBlock = new Block(10 * (long) Math.pow(10, accuracy), -0.5, 100, true);

        AnimationTimer at = new AnimationTimer() {
            private long last = 0;

            @Override
            public void handle(long now) {
                if(now - last >= 16_000_000) {
                    clear(blocks,blocksCanvas.getWidth(),blocksCanvas.getHeight());
                    clear(numbers,numbersCanvas.getWidth(),numbersCanvas.getHeight());
                    update();
                    drawBlocks(blocks);
                    drawNumbers(numbers);
                }
            }
        };

    }

    static void update() {

    }

    static void bonk(Block a, Block b) {

    }

    static void drawBlocks(GraphicsContext gc) {

    }

    static void drawNumbers(GraphicsContext gc) {

    }

    static void clear(GraphicsContext gc, double width, double height) {
        gc.clearRect(0,0,width,height);
    }
}
