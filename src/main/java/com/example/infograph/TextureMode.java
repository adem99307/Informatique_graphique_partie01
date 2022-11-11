package com.example.infograph;

import java.util.List;
import java.util.function.Function;


import javafx.scene.paint.Color;


/**
 *
 * @author jpereda
 */
public interface TextureMode {

    void setTextureModeNone();
    void setTextureModeNone(Color color);
    void setTextureModeNone(Color color, String image);
    void setTextureModeImage(String image);
    void setTextureModePattern(Patterns.CarbonPatterns pattern, double scale);
    void setTextureModeVertices3D(int colors, Function<Point3D, Number> dens);
    void setTextureModeVertices3D(Palette.ColorPalette palette, Function<Point3D, Number> dens);
    void setTextureModeVertices3D(int colors, Function<Point3D, Number> dens, double min, double max);
    void setTextureModeVertices1D(int colors, Function<Number, Number> function);
    void setTextureModeVertices1D(Palette.ColorPalette palette, Function<Number, Number> function);
    void setTextureModeVertices1D(int colors, Function<Number, Number> function, double min, double max);
    void setTextureModeFaces(int colors);
    void setTextureModeFaces(Palette.ColorPalette palette);
    void setTextureOpacity(double value);

    void updateF(List<Number> values);
}
