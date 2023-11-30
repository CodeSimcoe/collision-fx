package com.codesimcoe.collisionfx;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Data;

@Data
public class ColliderObject {

  private Circle circle;

  private double x;
  private double y;

  private double vx;
  private double vy;

  private double radius;
  private double mass;

  public ColliderObject(final double radius, final Color color) {
    this.radius = radius;
    this.circle = new Circle(radius);
    this.circle.setFill(color);

    // TODO
    this.mass = radius * radius;
  }

  public void update() {
    this.x += this.vx;
    this.y += this.vy;

    // Bounce off the walls
    if (this.x < this.radius || this.x > Configuration.WIDTH - this.radius) {
      this.vx *= -1;
    }
    if (this.y < this.radius || this.y > Configuration.HEIGHT - this.radius) {
      this.vy *= -1;
    }

    this.circle.setCenterX(this.x);
    this.circle.setCenterY(this.y);
  }

  public Node getNode() {
    return this.circle;
  }

  public void collide(final ColliderObject other) {
    double dx = this.x - other.getX();
    double dy = this.y - other.getY();
    double distance = Math.sqrt(dx * dx + dy * dy);

    boolean collides = distance <= (this.radius + other.getRadius());

    if (collides) {
      double nx = dx / distance;
      double ny = dy / distance;

      double p = 2 * (this.vx * nx + this.vy * ny - other.getVx() * nx - other.getVy() * ny) /
        (this.mass + other.getMass());

      double v1xNew = this.vx - p * other.getMass() * nx;
      double v1yNew = this.vy - p * other.getMass() * ny;
      double v2xNew = other.getVx() + p * this.mass * nx;
      double v2yNew = other.getVy() + p * this.mass * ny;

      this.vx = v1xNew;
      this.vy = v1yNew;
      other.setVx(v2xNew);
      other.setVy(v2yNew);
    }
  }
}
