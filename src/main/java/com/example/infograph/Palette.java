package com.example.infograph;


import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

/**
 *
 * @author jpereda
 */
public class Palette {

    public interface ColorPalette {

        int getNumColors();

        Color getColor(int i);

        default LinearGradient getLinearGradient() {
            List<Stop> stops = new ArrayList<>();
            for (int i = 0; i <= 5; i++) {
                double p = (double) i / 5d;
                stops.add(new Stop(p, getColor((int) (p * getNumColors()))));
            }
            return new LinearGradient(0, 1, 0, 0, true, CycleMethod.NO_CYCLE, stops);
        }
    }

    public static class ListColorPalette implements ColorPalette {

        private final List<Color> colors;

        public ListColorPalette(Color... colors) {
            this(new ArrayList<>(Arrays.asList(colors)));
        }

        public ListColorPalette(List<Color> colors) {
            this.colors = colors;
        }

        public void setColors(Color... colors) {
            setColors(new ArrayList<>(Arrays.asList(colors)));
        }

        public void setColors(List<Color> colors) {
            this.colors.clear();
            this.colors.addAll(colors);
        }

        public List<Color> getColors() {
            return colors;
        }

        @Override
        public Color getColor(int i) {
            return colors != null && ! colors.isEmpty() ?
                    colors.get(Math.max(0, Math.min(i, colors.size() - 1))) : Color.BLACK;
        }

        @Override
        public int getNumColors() {
            return colors != null ? colors.size() : 0;
        }
    }

    public static class FunctionColorPalette implements ColorPalette {

        private final int numColors;
        private final Function<Double, Color> function;

        public FunctionColorPalette(int numColors, Function<Double, Color> function) {
            this.numColors = numColors;
            this.function = function;
        }

        @Override
        public Color getColor(int i) {
            return function != null ? function.apply((double) i / (double) numColors) : Color.BLACK;
        }

        @Override
        public int getNumColors() {
            return numColors;
        }
    }

    private final int numColors;
    private final ColorPalette colorPalette;
    private final double opacity;
    private int width;
    private int height;
    private Image imgPalette;

    private final static double DEFAULT_OPACITY = 1.0;
    private final static int DEFAULT_NUMCOLORS = 1530; // 39x40 palette image
    public final static ColorPalette DEFAULT_COLOR_PALETTE =
            new FunctionColorPalette(DEFAULT_NUMCOLORS, d -> Color.hsb(360d * d, 1, 1, DEFAULT_OPACITY));

    public Palette(){
        this(DEFAULT_NUMCOLORS, DEFAULT_COLOR_PALETTE, DEFAULT_OPACITY);
    }

    public Palette(int numColors){
        this(numColors, DEFAULT_COLOR_PALETTE, DEFAULT_OPACITY);
    }

    public Palette(int numColors, ColorPalette colorPalette){
        this(numColors, colorPalette, DEFAULT_OPACITY);
    }

    public Palette(int numColors, ColorPalette colorPalette, double opacity) {
        this.numColors = numColors;
        this.colorPalette = colorPalette;
        this.opacity = opacity;
    }

    public Image createPalette(boolean save){
        if (colorPalette == null || colorPalette.getNumColors() < 1) {
            return null;
        }

        // try to create a square image
        width = (int) Math.sqrt(colorPalette.getNumColors());
        height = (int) Math.ceil((double) numColors / (double) width);

        imgPalette = new WritableImage(width, height);
        PixelWriter pw  = ((WritableImage) imgPalette).getPixelWriter();
        AtomicInteger count = new AtomicInteger();
        IntStream.range(0, height).boxed()
                .forEach(y -> IntStream.range(0, width).boxed()
                        .forEach(x -> pw.setColor(x, y, getColor(count.getAndIncrement()))));
        if (save) {
            //saveImage();
        }
        return imgPalette;
    }

    public DoubleStream getTextureLocation(int iPoint){
        if(width==0 || height==0){
            return DoubleStream.of(0f,0f);
        }
        int y = iPoint/width;
        int x = iPoint-width*y;
        // add 0.5 to interpolate colors from the middle of the pixel
        return DoubleStream.of((((float)x+0.5f)/((float)width)),(((float)y+0.5f)/((float)height)));
    }



    /*
        int iColor [0-numColors]
    */
    private Color getColor(int iColor){
        return colorPalette.getColor(iColor);
    }

    public int getNumColors() {
        return numColors;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getPaletteImage() {
        return imgPalette;
    }

    public ColorPalette getColorPalette(){
        return colorPalette;
    }

}