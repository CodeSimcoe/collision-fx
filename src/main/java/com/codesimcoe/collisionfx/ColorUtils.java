package com.codesimcoe.collisionfx;

import javafx.scene.paint.Color;

public final class ColorUtils {

  private ColorUtils() {
    // Utility class
  }

  public static Color randomColor() {
    double hue = Math.random() * 360;
    return Color.hsb(hue, 1.0, 1.0, 1.0);
  }
}
