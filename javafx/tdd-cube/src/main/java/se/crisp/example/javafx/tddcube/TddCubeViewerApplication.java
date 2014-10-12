package se.crisp.example.javafx.tddcube;

import javafx.stage.Stage;
import se.crisp.example.javafx.util.Gadget;

public class TddCubeViewerApplication extends Gadget {
    static final float unit = 200.0f;

    private TddCubeView node;

    @Override
    public void start(Stage stage) throws Exception {
        node = new TddCubeView(unit);

        gadgetStart(stage, node, unit * 3, unit * 3);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
