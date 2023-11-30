package com.codesimcoe.collisionfx;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CollisionFx {

  private final Group root;
  private final Scene scene;

  private final List<ColliderObject> objects = new ArrayList<>();

  public CollisionFx() {
    this.root = new Group();
    this.scene = new Scene(this.root, Configuration.WIDTH, Configuration.HEIGHT);
    this.scene.setFill(Color.BLACK);
  }

  public void update() {

    this.objects.forEach(ColliderObject::update);

    // Collision detection
    for (int i = 0; i < this.objects.size(); i++) {
      ColliderObject object = this.objects.get(i);

      for (int j = i + 1; j < this.objects.size(); j++) {

        ColliderObject other = this.objects.get(j);
        object.collide(other);
      }
    }
  }

  public void addObject(final ColliderObject object) {
    this.objects.add(object);
    this.root.getChildren().add(object.getNode());
  }
}
