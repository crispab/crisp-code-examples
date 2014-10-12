package se.crisp.example.javafx.tddcube;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.net.URL;

public class TddCubeView extends MeshView {
    private static final String TEXTURE = "tddcube.png";
    private static final Rotate rx = new Rotate(0, Rotate.X_AXIS);
    private static final Rotate ry = new Rotate(270, Rotate.Y_AXIS);
    private static final Rotate rz = new Rotate(0, Rotate.Z_AXIS);
    private static final double sideAnglesX[] = {0, 0, 0, 0, 90, 270};
    private static final double sideAnglesY[] = {270, 0, 90, 180, 0, 0};

    private Timeline sideAnimations[] = new Timeline[6];
    private int sideIndex = -1;

    public TddCubeView(float unit) throws FileNotFoundException {
        super(createMesh(unit, unit, unit));
        setMaterial(createMaterial());
        setTranslateX(unit);
        setTranslateY(unit);
        setTranslateZ(unit);
        getTransforms().addAll(rx, ry, rz);

        setUpSideAnimations();

        onMouseClickedProperty().setValue(this::nextSide);
    }
    
    private static TriangleMesh createMesh(float w, float h, float d) {
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

    private void setUpSideAnimations() {
        setUpSideAnimation(0, 5);
        setUpSideAnimation(1, 3);
        setUpSideAnimation(2, 1);
        setUpSideAnimation(3, 2);
        setUpSideAnimation(4, 4);
        setUpSideAnimation(5, 1);
    }

    private void setUpSideAnimation(int animation, int toSide) {
        sideAnimations[animation] = new Timeline();
        sideAnimations[animation].getKeyFrames().addAll(rotateToSide(toSide));
    }

    private KeyFrame rotateToSide(int toSide) {
        Duration duration = Duration.seconds(3);
        return new KeyFrame(duration,
                new KeyValue(ry.angleProperty(), sideAnglesY[toSide]),
                new KeyValue(rx.angleProperty(), sideAnglesX[toSide])
        );
    }

    private void nextSide(MouseEvent event) {
        sideIndex = (sideIndex + 1) % sideAnimations.length;
        sideAnimations[sideIndex].play();
    }

}
