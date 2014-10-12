package se.crisp.example.javafx.analogClock;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import se.crisp.example.javafx.util.Gadget;

public class AnalogClockWithClockwork extends Gadget {

    static final double unit = 100.0;

    private Clockwork clockwork = new Clockwork();

    @Override
    public void start(final Stage stage) throws Exception {
        final Parent root = new Group(
                outerRim(),
                minuteHand(),
                hourHand(),
                secondsHand(),
                tickMarks(),
                centerPoint()
        );

        gadgetStart(stage, root, unit * 3, unit * 3);
    }

    private Node hourHand() {
        Rotate rotate = rotationAroundCenter();
        rotate.angleProperty().bind(
                clockwork.hour
                        .multiply(360 / 12)
                        .add(
                                clockwork.minute
                                        .multiply(360.0 / 12.0 / 60.0)
                        )
        );
        return hand(unit * 0.4, Color.BLACK, rotate);
    }

    private Node minuteHand() {
        Rotate rotate = rotationAroundCenter();
        rotate.angleProperty().bind(clockwork.minute.multiply(360 / 60));
        return hand(unit * 0.2, Color.BLACK, rotate);
    }

    private Node secondsHand() {
        Rotate rotate = rotationAroundCenter();
        rotate.angleProperty().bind(clockwork.second.multiply(360 / 60));
        Line line = new Line(unit, unit * 1.1, unit, unit * 0.2);
        line.getTransforms().add(rotate);

        return line;
    }

    private Rotate rotationAroundCenter() {
        return new Rotate(0.0, unit, unit);
    }


    private Node hand(double stretchRelativeToRim, Color color, Rotate rotate) {
        Path path = new Path(
                new MoveTo(unit, unit),
                new LineTo(unit * 0.9, unit * 0.9),
                new LineTo(unit, stretchRelativeToRim),
                new LineTo(unit * 1.1, unit * 0.9),
                new LineTo(unit, unit)
        );
        path.setFill(color);
        path.setStroke(Color.TRANSPARENT);
        path.getTransforms().add(rotate);
        return path;
    }


    private Node tickMarks() {
        Group tickMarkGroup = new Group();
        for (int n = 0; n < 12; n++) {
            tickMarkGroup.getChildren().add(tickMark(n));
        }
        return tickMarkGroup;
    }

    private Node tickMark(int n) {
        double angle = 360 / 12 * n;
        Rotate rotate = new Rotate(angle, unit, unit);
        Line line = new Line(unit, unit * 0.12, unit, unit * (n % 3 == 0 ? 0.3 : 0.2));
        line.getTransforms().add(rotate);
        return line;
    }

    private Node centerPoint() {
        double radius = 0.05 * unit;
        return new Circle(unit, unit, radius, Color.BLACK);
    }

    private Node outerRim() {
        double centerX = 0.5;
        double centerY = 0.5;
        double radius = 0.5;
        RadialGradient radialGradient = new RadialGradient(0.0, 0.0, centerX, centerY, radius, true, CycleMethod.NO_CYCLE,
                new Stop(0.8, Color.WHITE),
                new Stop(0.9, Color.BLACK),
                new Stop(0.95, Color.WHITE),
                new Stop(1.0, Color.BLACK)
        );
        return new Circle(unit, unit, unit, radialGradient);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
