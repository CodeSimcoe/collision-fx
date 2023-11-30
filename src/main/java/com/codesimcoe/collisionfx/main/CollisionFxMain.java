package com.codesimcoe.collisionfx.main;

import com.codesimcoe.collisionfx.ColliderObject;
import com.codesimcoe.collisionfx.CollisionFx;
import com.codesimcoe.collisionfx.ColorUtils;
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

    for (int i = 0; i < 50; i++) {
      ColliderObject object = new ColliderObject(
        this.random.nextGaussian(10, 5),
        ColorUtils.randomColor()
      );
      collisionFx.addObject(object);

      // Random position within the scene
      object.setX(Math.random() * collisionFx.getScene().getWidth());
      object.setY(Math.random() * collisionFx.getScene().getHeight());

      // Random velocity
      object.setVx(this.random.nextGaussian(0, 10));
      object.setVy(this.random.nextGaussian(0, 10));
    }

    EventHandler<ActionEvent> update = event -> collisionFx.update();

    Duration duration = Duration.millis(40);
    Animation loop = new Timeline(new KeyFrame(duration, update));
    loop.setCycleCount(Animation.INDEFINITE);
    loop.play();
  }
}
