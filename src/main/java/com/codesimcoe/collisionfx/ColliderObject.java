package com.codesimcoe.collisionfx;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Data;

@Data
public class ColliderObject {

  private final Circle circle;

  private double x;
  private double y;

  private double vx;
  private double vy;

  private final double radius;
  private final double mass;

  public ColliderObject(final double radius, final Color color) {
    this.radius = radius;
    this.circle = new Circle(radius);
    this.circle.setFill(color);

    // Make mass proportional to area
    this.mass = radius * radius;
  }

  public void update() {
    this.x += this.vx;
    this.y += this.vy;

    // Handle collision with left and right boundaries
    if (this.x - this.radius < 0) {
      this.x = this.radius; // Adjust position
      this.vx = -this.vx; // Reverse x-velocity
    } else if (this.x + this.radius > Configuration.WIDTH) {
      this.x = Configuration.WIDTH - this.radius; // Adjust position
      this.vx = -this.vx; // Reverse x-velocity
    }

    // Handle collision with top and bottom boundaries
    if (this.y - this.radius < 0) {
      this.y = this.radius; // Adjust position
      this.vy = -this.vy; // Reverse y-velocity
    } else if (this.y + this.radius > Configuration.HEIGHT) {
      this.y = Configuration.HEIGHT - this.radius; // Adjust position
      this.vy = -this.vy; // Reverse y-velocity
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

    double overlap = (this.radius + other.getRadius()) - distance;

    if (overlap > 0) {
      // Normalize the direction vector
      double nx = dx / distance;
      double ny = dy / distance;

      // Move objects so they just touch at the point of impact
      double correction = overlap / 2.0;
      this.x += correction * nx;
      this.y += correction * ny;
      other.setX(other.getX() - correction * nx);
      other.setY(other.getY() - correction * ny);

      // Calculate new velocities
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
