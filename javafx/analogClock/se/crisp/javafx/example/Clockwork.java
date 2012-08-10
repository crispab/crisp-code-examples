package se.crisp.javafx.example;

/*
 * Created with IntelliJ IDEA.
 * User: perty
 * Date: 2012-08-09
 * Time: 15:22
 */

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
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
        TimelineBuilder.create()
                .cycleCount(Timeline.INDEFINITE)
                .keyFrames(
                        new KeyFrame(Duration.seconds(1), updateTime())
                )
                .build()
                .play();
    }

    private EventHandler updateTime() {
        return new EventHandler() {
            @Override
            public void handle(Event event) {
                Calendar calendar = Calendar.getInstance();
                hour.set(calendar.get(Calendar.HOUR));
                minute.set(calendar.get(Calendar.MINUTE));
                second.set(calendar.get(Calendar.SECOND));
            }
        };
    }
}
