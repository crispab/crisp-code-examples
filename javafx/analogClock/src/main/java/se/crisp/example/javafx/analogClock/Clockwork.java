package se.crisp.example.javafx.analogClock;

/*
 * Created with IntelliJ IDEA.
 * User: perty
 * Date: 2012-08-09
 * Time: 15:22
 */

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

import java.util.Calendar;

/**
 * Clockwork keeps track of time of day, once a second. It has properties to bind to.
 */
public class Clockwork {

    public SimpleIntegerProperty hour = new SimpleIntegerProperty(0);
    public SimpleIntegerProperty minute = new SimpleIntegerProperty(0);
    public SimpleIntegerProperty second = new SimpleIntegerProperty(0);

    public Clockwork() {
        startTicking();
    }

    private void startTicking() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            Calendar calendar = Calendar.getInstance();
            hour.set(calendar.get(Calendar.HOUR));
            minute.set(calendar.get(Calendar.MINUTE));
            second.set(calendar.get(Calendar.SECOND));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

}
