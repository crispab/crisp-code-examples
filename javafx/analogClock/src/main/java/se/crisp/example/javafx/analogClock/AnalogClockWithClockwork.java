package se.crisp.example.javafx.analogClock;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradientBuilder;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.RotateBuilder;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/*
 * Created with IntelliJ IDEA.
 * User: perty
 * Date: 2012-08-08
 * Time: 08:25
 */

/**
 * The analog clock gets a clockwork.
 */
public class AnalogClockWithClockwork extends Application {

    static final double unit = 100.0;

    private Clockwork clockwork = new Clockwork();

    @Override
    public void start(final Stage stage) throws Exception {
        final Parent root = GroupBuilder.create()
                .children(
                        outerRim(),
                        minuteHand(),
                        hourHand(),
                        secondsHand(),
                        tickMarks(),
                        centerPoint()
                )
                .build();
        setUpMouseForScaleAndMove(stage, root);
        Scene scene = makeATransparentScene(root);
        makeATransparentStage(stage, scene);
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
        return LineBuilder.create()
                .startX(unit)
                .endX(unit)
                .startY(unit * 1.1)
                .endY(unit * 0.2)
                .transforms(rotate)
                .build();
    }

    private Rotate rotationAroundCenter() {
        return RotateBuilder.create()
                .pivotX(unit)
                .pivotY(unit)
                .build();
    }


    private Node hand(double stretchRelativeToRim, Color color, Rotate rotate) {
        return PathBuilder.create()
                .fill(color)
                .stroke(Color.TRANSPARENT)
                .elements(
                        new MoveTo(unit, unit),
                        new LineTo(unit * 0.9, unit * 0.9),
                        new LineTo(unit, stretchRelativeToRim),
                        new LineTo(unit * 1.1, unit * 0.9),
                        new LineTo(unit, unit)
                )
                .transforms(rotate)
                .build();
    }


    private Node tickMarks() {
        Group tickMarkGroup = new Group();
        for (int n = 0; n < 12; n++) {
            tickMarkGroup.getChildren().add(tickMark(n));
        }
        return tickMarkGroup;
    }

    private Node tickMark(int n) {
        return LineBuilder.create()
                .startX(unit)
                .endX(unit)
                .startY(unit * 0.12)
                .endY(unit * (n % 3 == 0 ? 0.3 : 0.2))
                .transforms(
                        RotateBuilder.create()
                                .pivotX(unit)
                                .pivotY(unit)
                                .angle(360 / 12 * n)
                                .build()
                )
                .strokeWidth(2)
                .build();
    }

    private Node centerPoint() {
        return CircleBuilder.create()
                .fill(Color.BLACK)
                .radius(0.05 * unit)
                .centerX(unit)
                .centerY(unit)
                .build();
    }

    private Node outerRim() {
        return CircleBuilder.create()
                .fill(
                        RadialGradientBuilder.create()
                                .stops(
                                        new Stop(0.8, Color.WHITE),
                                        new Stop(0.9, Color.BLACK),
                                        new Stop(0.95, Color.WHITE),
                                        new Stop(1.0, Color.BLACK)
                                )
                                .cycleMethod(CycleMethod.NO_CYCLE)
                                .centerX(0.5)
                                .centerY(0.5)
                                .radius(0.5)
                                .proportional(true)
                                .build()
                )
                .radius(unit)
                .centerX(unit)
                .centerY(unit)
                .build();
    }

    private void setUpMouseForScaleAndMove(final Stage stage, final Parent root) {
        root.onMouseDraggedProperty().set(moveWhenDragging(stage));
        root.onScrollProperty().set(scaleWhenScrolling(stage, root));
    }

    private EventHandler<MouseEvent> moveWhenDragging(final Stage stage) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.setX(mouseEvent.getScreenX() - stage.getWidth() / 2);
                stage.setY(mouseEvent.getScreenY() - stage.getHeight() / 2);
            }
        };
    }

    private EventHandler<ScrollEvent> scaleWhenScrolling(final Stage stage, final Parent root) {
        return new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent scrollEvent) {
                double scroll = scrollEvent.getDeltaY();
                root.setScaleX(root.getScaleX() + scroll / 100);
                root.setScaleY(root.getScaleY() + scroll / 100);
                root.setTranslateX(root.getTranslateX() + scroll);
                root.setTranslateY(root.getTranslateY() + scroll);
                stage.sizeToScene();
            }
        };
    }

    private Scene makeATransparentScene(Parent root) {
        return SceneBuilder.create()
                .root(root)
                .fill(Color.TRANSPARENT)
                .build();
    }

    private void makeATransparentStage(Stage stage, Scene scene) {
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
