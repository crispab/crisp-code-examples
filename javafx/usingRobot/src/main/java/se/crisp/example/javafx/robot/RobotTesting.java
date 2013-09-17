package se.crisp.example.javafx.robot;

import javafx.animation.KeyFrame;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RobotTesting extends Application {

    private static final Logger logger = Logger.getLogger(RobotTesting.class.getName());

    @Override
    public void start(Stage primaryStage) {
        final Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(buttonActionHandler(btn));

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private EventHandler<ActionEvent> buttonActionHandler(final Button button) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
                final Scene scene = button.getScene();
                final Point2D windowCoord = new Point2D(scene.getWindow().getX(), scene.getWindow().getY());
                final Point2D sceneCoord = new Point2D(scene.getX(), scene.getY());
                final Point2D nodeCoord = button.localToScene(0.0, 0.0);
                final double clickX = Math.round(windowCoord.getX() + sceneCoord.getX() + nodeCoord.getX());
                final double clickY = Math.round(windowCoord.getY() + sceneCoord.getY() + nodeCoord.getY());
                clickMeAgainLater(clickX, clickY);
            }

            private void clickMeAgainLater(double x, double y) {
                TimelineBuilder
                        .create()
                        .keyFrames(
                        new KeyFrame(Duration.seconds(5), clickIt(x, y)))
                        .build()
                        .play();
            }

            private EventHandler<ActionEvent> clickIt(final double x, final double y) {
                return new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        try {
                            Robot robot = new Robot();
                            robot.mouseMove(new Double(x).intValue(), new Double(y).intValue());
                            robot.mousePress(InputEvent.BUTTON1_MASK);
                            robot.mouseRelease(InputEvent.BUTTON1_MASK);
                        } catch (AWTException ex) {
                            logger.log(Level.SEVERE, "bad mouse", ex);
                        }
                    }
                };
            }
        };
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application. main() serves only as fallback in case the
     * application can not be launched through deployment artifacts, e.g., in IDEs with limited FX support. NetBeans
     * ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
