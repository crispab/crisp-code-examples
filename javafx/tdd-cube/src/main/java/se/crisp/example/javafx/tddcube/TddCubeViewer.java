package se.crisp.example.javafx.tddcube;

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

import java.io.FileNotFoundException;
import java.net.URL;

public class TddCubeViewer extends Application {
    static final float unit = 200.0f;
    static final String TEXTURE = "tddcube.png";
    final Rotate rx = new Rotate(0, Rotate.X_AXIS);
    final Rotate ry = new Rotate(270, Rotate.Y_AXIS);
    final Rotate rz = new Rotate(0, Rotate.Z_AXIS);
    private int sideIndex = -1;
    private Timeline sideAnimations[] = new Timeline[6];
    private static final double sideAnglesX[] = {0, 0, 0, 0, 90, 270};
    private static final double sideAnglesY[] = {270, 0, 90, 180, 0, 0};

    @Override
    public void start(Stage stage) throws Exception {
        Node dice = createMeshDice();

        setUpSideAnimations();
        final Parent root = new Group(
                dice
        );
        root.onMouseClickedProperty().setValue(mouseEvent -> nextSide());
        setUpMouseForScaleAndMove(stage, root);
        Scene scene = makeATransparentScene(root);
        makeATransparentStage(stage, scene);
    }

    private void setUpSideAnimations() {
        for (int n = 0; n < sideAnimations.length; n++) {
            sideAnimations[n] = new Timeline();
        }
        sideAnimations[0].getKeyFrames().addAll(
                rotateToSide(5));
        sideAnimations[1].getKeyFrames().addAll(
                rotateToSide(3));
        sideAnimations[2].getKeyFrames().addAll(
                rotateToSide(1));
        sideAnimations[3].getKeyFrames().addAll(
                rotateToSide(2));
        sideAnimations[4].getKeyFrames().addAll(
                rotateToSide(4));
        sideAnimations[5].getKeyFrames().addAll(
                rotateToSide(1));
    }

    private KeyFrame rotateToSide(int toSide) {
        Duration duration = Duration.seconds(3);
        return new KeyFrame(duration,
                new KeyValue(ry.angleProperty(), sideAnglesY[toSide]),
                new KeyValue(rx.angleProperty(), sideAnglesX[toSide])
        );
    }

    private void nextSide() {
        sideIndex = (sideIndex + 1) % sideAnimations.length;
        sideAnimations[sideIndex].play();
    }

    private Node createMeshDice() throws FileNotFoundException {
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

    private PhongMaterial createMaterial() throws FileNotFoundException {
        URL resource = getClass().getResource(TEXTURE);
        if (resource == null) {
            throw new FileNotFoundException(TEXTURE + " was not found.");
        }
        Image diffuseMap = new Image(resource.toExternalForm());

        PhongMaterial earthMaterial = new PhongMaterial();
        earthMaterial.setDiffuseMap(diffuseMap);

        return earthMaterial;
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
            double scaleX = root.getScaleX() + scroll / 300;
            root.setScaleX(scaleX);
            double scaleY = root.getScaleY() + scroll / 300;
            root.setScaleY(scaleY);
            stage.sizeToScene();
        };
    }

    private Scene makeATransparentScene(Parent root) {
        return new Scene(root, unit * 3, unit * 3, Color.TRANSPARENT);
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
