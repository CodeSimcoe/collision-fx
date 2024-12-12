package com.codesimcoe.collisionfx.main;

import com.codesimcoe.collisionfx.ColliderObject;
import com.codesimcoe.collisionfx.CollisionFx;
import com.codesimcoe.collisionfx.ColorUtils;
import com.codesimcoe.collisionfx.Configuration;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.Random;

public class CollisionFxMain extends Application {

  private final Random random = new Random();

  public static void main(final String[] args) {
    launch(args);
  }

  @Override
  public void start(final Stage primaryStage) {

    CollisionFx collisionFx = new CollisionFx();

    primaryStage.initStyle(StageStyle.UNDECORATED);
    primaryStage.setScene(collisionFx.getScene());
    primaryStage.show();

    collisionFx.getScene().setOnMouseClicked(event -> this.spawn(collisionFx, event.getX(), event.getY()));

    EventHandler<ActionEvent> update = _ -> collisionFx.update();

    // 50 fps
    Duration duration = Duration.millis(20);
    Animation loop = new Timeline(new KeyFrame(duration, update));
    loop.setCycleCount(Animation.INDEFINITE);
    loop.play();
  }

  private void spawn(final CollisionFx collisionFx, final double x, final double y) {
    ColliderObject object = new ColliderObject(
      this.random.nextGaussian(20, 5), // size
      ColorUtils.randomColor()
    );

    // Random position within the scene
    object.setX(x);
    object.setY(y);

    // Random velocity
    object.setVx(this.random.nextGaussian(0, Configuration.VELOCITY_STDDEV));
    object.setVy(this.random.nextGaussian(0, Configuration.VELOCITY_STDDEV));

    collisionFx.addObject(object);
  }
}
