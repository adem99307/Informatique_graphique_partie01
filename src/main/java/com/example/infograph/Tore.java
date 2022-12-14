package com.example.infograph;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.scene.AmbientLight;
import javafx.scene.DepthTest;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import com.example.infograph.ShapeContainer;
import com.example.infograph.ToreMesh;
public class Tore extends ShapeContainer<ToreMesh>{

    public ToreMesh mesh;

    public Tore() {
        super(new ToreMesh());
        this.mesh = getShape();
    }

    public Tore(double radius, double tRadius){
        this();
        mesh.setRadius(radius);
        mesh.setTubeRadius(tRadius);
    }

    public Tore(int rDivs, int tDivs, double radius, double tRadius) {
        this();
        mesh.setRadiusDivisions(rDivs);
        mesh.setTubeDivisions(tDivs);
        mesh.setRadius(radius);
        mesh.setTubeRadius(tRadius);
    }

    public Tore(Color c) {
        this();
        this.setDiffuseColor(c);
    }

    public Tore(double radius, double tRadius, Color c){
        this(radius, tRadius);
        this.setDiffuseColor(c);
    }

    public Tore(int rDivs, int tDivs, double radius, double tRadius, Color c) {
        this(rDivs, tDivs,radius, tRadius);
        this.setDiffuseColor(c);
    }

    public final int getRadiusDivisions() {
        return mesh.getRadiusDivisions();
    }

    public final void setRadiusDivisions(int value) {
        mesh.setRadiusDivisions(value);
    }

    public IntegerProperty radiusDivisionsProperty() {
        return mesh.radiusDivisionsProperty();
    }

    public final int getTubeDivisions() {
        return mesh.getTubeDivisions();
    }

    public final void setTubeDivisions(int value) {
        mesh.setTubeDivisions(value);
    }

    public IntegerProperty tubeDivisionsProperty() {
        return mesh.tubeDivisionsProperty();
    }

    public final double getRadius() {
        return mesh.getRadius();
    }

    public final void setRadius(double value) {
        mesh.setRadius(value);
    }

    public DoubleProperty radiusProperty() {
        return mesh.radiusProperty();
    }

    public final double getTubeRadius() {
        return mesh.getTubeRadius();
    }

    public final void setTubeRadius(double value) {
        mesh.setTubeRadius(value);
    }

    public DoubleProperty tubeRadiusProperty() {
        return mesh.tubeRadiusProperty();
    }

    public final double getTubeStartAngleOffset() {
        return mesh.getTubeStartAngleOffset();
    }

    public void setTubeStartAngleOffset(double value) {
        mesh.setTubeStartAngleOffset(value);
    }

    public DoubleProperty tubeStartAngleOffsetProperty() {
        return mesh.tubeStartAngleOffsetProperty();
    }

    public final double getxOffset() {
        return mesh.getxOffset();
    }

    public void setxOffset(double value) {
        mesh.setxOffset(value);
    }

    public DoubleProperty xOffsetProperty() {
        return mesh.xOffsetProperty();
    }

    public final double getyOffset() {
        return mesh.getyOffset();
    }

    public void setyOffset(double value) {
        mesh.setyOffset(value);
    }

    public DoubleProperty yOffsetProperty() {
        return mesh.yOffsetProperty();
    }

    public final double getzOffset() {
        return mesh.getzOffset();
    }

    public void setzOffset(double value) {
        mesh.setzOffset(value);
    }

    public DoubleProperty zOffsetProperty() {
        return mesh.zOffsetProperty();
    }

    public final void setMaterial(Material value) {
        mesh.setMaterial(value);
    }

    public final void setDrawMode(DrawMode value) {
        mesh.setDrawMode(value);
    }

    public final void setCullFace(CullFace value) {
        mesh.setCullFace(value);
    }

}
