package se.crisp.example.javafx.tdd;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collection;

public class TetrahedronViewer extends Application {

    final Double sideLength = 300.0;
    Timeline animation;

    @Override
    public void start(Stage stage) {

        Node content = createContent();

        StackPane root = new StackPane();
        root.setDepthTest(DepthTest.ENABLE);
        root.getChildren().addAll(content, createButtons());
        
        stage.setTitle("TDD Tetrahedron");
        Scene scene = createScene(root);
        stage.setScene(scene);
        stage.show();
        animation.play();
    }

    private HBox createButtons() {
        HBox buttons = new HBox();
        buttons.getChildren().addAll(createPauseButton(), createResetButton());
        return buttons;
    }

    private Node createPauseButton() {
        final Button btn = new Button();
        btn.setText("Pause");
        btn.setOnAction(event -> {
            if (animation.getStatus() == Animation.Status.PAUSED) {
                animation.play();
                btn.setText("Pause");
            } else {
                animation.pause();
                btn.setText("Run");
            }
        });
        return btn;
    }
    
    private Node createResetButton() {
        final Button btn = new Button();
        btn.setText("Reset");
        btn.setOnAction(event -> {
            animation.playFromStart();
            animation.pause();
        });
        return btn;
    }
    private Node createContent() {
        final Tetrahedron tetra = new Tetrahedron();
        tetra.translateXProperty().setValue(sideLength/2);
        animation = new Timeline();
        animation.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                new KeyValue(tetra.ry.angleProperty(), 0d)
                //,new KeyValue(tetra.rx.angleProperty(), 0d)
                //,new KeyValue(tetra.rz.angleProperty(), 0d)
                ),
                new KeyFrame(Duration.seconds(10),
                new KeyValue(tetra.ry.angleProperty(), 360d)
                //,new KeyValue(tetra.rx.angleProperty(), 360d)
                //,new KeyValue(tetra.rz.angleProperty(), 360d)
                        ));
        animation.setCycleCount(Animation.INDEFINITE);

        return tetra;
    }

    private Scene createScene(StackPane root) {
        boolean depthBuffer = true;
        final Scene scene = new Scene(root, sideLength*2.2, sideLength*2.1, depthBuffer);
        scene.setCamera(new PerspectiveCamera());
        return scene;
    }

    class Tetrahedron extends Group {

        final Rotate rx = new Rotate(0, Rotate.X_AXIS);
        final Rotate ry = new Rotate(0, Rotate.Y_AXIS);
        final Rotate rz = new Rotate(0, Rotate.Z_AXIS);

        public Tetrahedron() {
            getTransforms().addAll(rz, ry, rx);
            final double halfside = sideLength / 2;
            final double sideHeight = Math.sqrt(sideLength * sideLength - halfside * halfside);
            final Point2D baseLeft = new Point2D(0, sideHeight);
            final Point2D baseRight = new Point2D(sideLength, sideHeight);
            final Point2D top = new Point2D(halfside, 0);
            Collection<Double> points = createSidePoints(baseLeft, top, baseRight);
            final double leanAngle = -19.5;
            final Rotate rotate2lean = new Rotate(leanAngle, 0, sideHeight, 0, Rotate.X_AXIS);
            Node red = createRedSide(points,  baseLeft, rotate2lean);
            Node green = createGreenSide(points, rotate2lean);
            Node yellow = createYellowSide(points, baseRight, rotate2lean);
            Node bottom = createBottom(points, baseLeft);
            getChildren().addAll(red, yellow, green, bottom);
        }

        private Collection<Double> createSidePoints(final Point2D baseLeft, final Point2D top, final Point2D baseRight) {
            final Collection<Double> points = new ArrayList<>();
            points.add(baseLeft.getX());
            points.add(baseLeft.getY());
            points.add(top.getX());
            points.add(top.getY());
            points.add(baseRight.getX());
            points.add(baseRight.getY());
            points.add(baseLeft.getX());
            points.add(baseLeft.getY());
            return points;
        }

        private Node createYellowSide(Collection<Double> points, final Point2D baseRight, final Rotate rotate2lean) {
            final Node yellow = createSide(points, Color.YELLOW, -sideLength, "Refactor");
            yellow.getTransforms().add(new Rotate(120.0, baseRight.getX(), baseRight.getY(), 0, Rotate.Y_AXIS));
            yellow.getTransforms().add(rotate2lean);
            return yellow;
        }

        private Node createRedSide(Collection<Double> points, final Point2D baseLeft, final Rotate rotate2lean) {
            final Node green = createSide(points, Color.RED, sideLength, "Failing Test");
            green.getTransforms().add(new Rotate(-120.0, baseLeft.getX(), baseLeft.getY(), 0, Rotate.Y_AXIS));
            green.getTransforms().add(rotate2lean);

            return green;
        }

        private Node createGreenSide(Collection<Double> points, final Rotate rotate2lean) {
            Node red = createSide(points, Color.GREEN, 0.0, "Implementation");
            red.getTransforms().add(rotate2lean);
            return red;
        }
        
        private Node createBottom(Collection<Double> points, final Point2D baseLeft) {
            final Node bottom = createSide(points, Color.GREY, 0.0, "B");
            bottom.getTransforms().add(new Rotate(-90, 0, baseLeft.getY(), 0, Rotate.X_AXIS));
            return bottom;
        }

        private Node createSide(final Collection<Double> points, Color color, Double xTranslate, String sideText) {
            Polygon side = new Polygon();
            side.getPoints().addAll(points);
            side.setFill(color);
            side.setStroke(Color.BLACK);
            Text text = new Text(0, 0, sideText);
            text.setFont(new Font(20));
            StackPane result = new StackPane();
            result.getChildren().addAll(side,text);
            result.translateXProperty().setValue(xTranslate);
            return result;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
