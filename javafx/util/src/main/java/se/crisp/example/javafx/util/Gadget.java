package se.crisp.example.javafx.util;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class Gadget extends Application {
    private Stage stage;
    private double deltaX;
    private double deltaY;

    @Override
    public abstract void start(Stage stage) throws Exception;

    protected void gadgetStart(Stage stage, Node node, double width, double height) {
        this.stage = stage;
        final Parent root = new Group(node);

        setUpMouseForScaleAndMove(stage, root);
        Scene scene = makeATransparentScene(root, width, height);
        makeATransparentStage(stage, scene);
    }

    private void setUpMouseForScaleAndMove(final Stage stage, final Parent root) {
        root.onMousePressedProperty().setValue(this::pressed);
        root.onMouseDraggedProperty().setValue(this::dragged);
        root.onScrollProperty().set(scaleWhenScrolling(stage, root));
    }

    private void dragged(MouseEvent mouseEvent) {
        stage.setX(mouseEvent.getScreenX() - deltaX);
        stage.setY(mouseEvent.getScreenY() - deltaY);
    }

    private void pressed(MouseEvent mouseEvent) {
        deltaX = mouseEvent.getX();
        deltaY = mouseEvent.getY();
    }

    private EventHandler<ScrollEvent> scaleWhenScrolling(final Stage stage, final Parent root) {
        return scrollEvent -> {
            double scroll = scrollEvent.getDeltaY();
            double scaleX = root.getScaleX() + scroll / 300;
            root.setScaleX(scaleX);
            double scaleY = root.getScaleY() + scroll / 300;
            root.setScaleY(scaleY);
            stage.sizeToScene();
        };
    }

    private Scene makeATransparentScene(Parent root, double width, double height) {
        return new Scene(root, width, height, Color.TRANSPARENT);
    }

    private void makeATransparentStage(Stage stage, Scene scene) {
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setAlwaysOnTop(true);
        stage.show();
    }
}
