package se.crisp.example.javafx.dice;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.URL;

public class DiceMeshViewer extends Application {
    static final float unit = 100.0f;
    static final String TEXTURE = "dice.png";
    final Rotate rx = new Rotate(0, Rotate.X_AXIS);
    final Rotate ry = new Rotate(0, Rotate.Y_AXIS);
    final Rotate rz = new Rotate(0, Rotate.Z_AXIS);

    @Override
    public void start(Stage stage) throws Exception {
        Node dice = createMeshDice();

        animate();

        final Parent root = new Group(
                dice
        );

        setUpMouseForScaleAndMove(stage, root);
        Scene scene = makeATransparentScene(root);
        makeATransparentStage(stage, scene);
    }

    private Node createMeshDice() {
        TriangleMesh mesh = createMesh(unit, unit, unit);
        MeshView dice = new MeshView(mesh);
        dice.setMaterial(createMaterial());
        dice.setTranslateX(unit);
        dice.setTranslateY(unit);
        dice.setTranslateZ(unit);
        dice.getTransforms().addAll(rx, ry, rz);
        return dice;
    }

    TriangleMesh createMesh(float w, float h, float d) {
        return new MeshCube(w, h, d);
    }

    private PhongMaterial createMaterial() {
        URL resource = getClass().getResource(TEXTURE);
        Image diffuseMap = new Image(resource.toExternalForm());

        PhongMaterial earthMaterial = new PhongMaterial();
        earthMaterial.setDiffuseMap(diffuseMap);

        return earthMaterial;
    }

    private void animate() {
        Timeline animation = new Timeline();
        animation.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(ry.angleProperty(), 0d)
                        , new KeyValue(rx.angleProperty(), 0d)
                        , new KeyValue(rz.angleProperty(), 0d)
                ),
                new KeyFrame(Duration.seconds(20),
                        new KeyValue(ry.angleProperty(), 360d)
                        , new KeyValue(rx.angleProperty(), 360d)
                        , new KeyValue(rz.angleProperty(), 360d)
                ));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
    }

    private void setUpMouseForScaleAndMove(final Stage stage, final Parent root) {
        root.onMouseDraggedProperty().set(moveWhenDragging(stage));
        root.onScrollProperty().set(scaleWhenScrolling(stage, root));
    }

    private EventHandler<MouseEvent> moveWhenDragging(final Stage stage) {
        return mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - stage.getWidth() / 2);
            stage.setY(mouseEvent.getScreenY() - stage.getHeight() / 2);
        };
    }

    private EventHandler<ScrollEvent> scaleWhenScrolling(final Stage stage, final Parent root) {
        return scrollEvent -> {
            double scroll = scrollEvent.getDeltaY();
            double scaleX = root.getScaleX() + scroll / 100;
            root.setScaleX(scaleX);
            double scaleY = root.getScaleY() + scroll / 100;
            root.setScaleY(scaleY);
            root.setTranslateX(root.getTranslateX() + scroll);
            root.setTranslateY(root.getTranslateY() + scroll);
            stage.sizeToScene();
        };
    }

    private Scene makeATransparentScene(Parent root) {
        return new Scene(root, unit * 2, unit * 2, Color.TRANSPARENT);
    }

    private void makeATransparentStage(Stage stage, Scene scene) {
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
