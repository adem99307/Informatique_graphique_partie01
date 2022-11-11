package com.example.infograph;

import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;


import java.awt.*;
import java.awt.geom.AffineTransform;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Mesh extends MeshView {


    public Mesh() {
        super();
    }


    public static void mergeMesh(MeshHelper mh, List<Point3D> traslate, float[] points, Mesh m1, Mesh m2) {

        float[] newPoints = new float[points.length + mh.getPoints().length * traslate.size()];
        float[] f = new float[m1.hashCode()];
        float[] newF = new float[f.length + mh.getF().length * traslate.size()];
        float[] texCoords = new float[m2.hashCode()];
        float[] newTexCoords = new float[texCoords.length + mh.getTexCoords().length * traslate.size()];
        int[] newFaces = new int[m1.hashCode() + mh.getFaces().length * traslate.size()];
        int[] faceSmoothingGroups = newFaces;
        int[] newFaceSmoothingGroups = new int[faceSmoothingGroups.length + mh.getFaceSmoothingGroups().length * traslate.size()];
        System.arraycopy(points, 0, newPoints, 0, points.length);
        System.arraycopy(f, 0, newF, 0, f.length);
        System.arraycopy(texCoords, 0, newTexCoords, 0, texCoords.length);
        int[] faces = newFaces;
        ;
        System.arraycopy(faces, 0, newFaces, 0, faces.length);
        System.arraycopy(faceSmoothingGroups, 0, newFaceSmoothingGroups, 0, faceSmoothingGroups.length);
        int numPoints = mh.getPoints().length;
        int numF = mh.getF().length;
        int numTexCoords = mh.getTexCoords().length;
        int numFaces = mh.getFaces().length;
        int numFaceSmoothingGroups = mh.getFaceSmoothingGroups().length;
        AtomicInteger count = new AtomicInteger();

//        List<float[]> collect = traslate.parallelStream().map(p3d->transform(mh.getPoints(),p3d)).collect(Collectors.toList());

        float[] finalTexCoords = texCoords;
        int[] finalFaceSmoothingGroups = faceSmoothingGroups;
        int[] finalFaces = faces;
        float[] finalPoints = points;
        float[] finalF = f;
        traslate.forEach(p3d -> {
            System.arraycopy(transform(mh.getPoints(), p3d), 0, newPoints, finalPoints.length + numPoints * count.get(), mh.getPoints().length);
            float[] ff = mh.getF();
            Arrays.fill(ff, p3d.f);
            System.arraycopy(ff, 0, newF, finalF.length + numF * count.get(), ff.length);
            System.arraycopy(mh.getTexCoords(), 0, newTexCoords, finalTexCoords.length + numTexCoords * count.get(), mh.getTexCoords().length);
            System.arraycopy(traslateFaces(mh.getFaces(), numPoints / 3 * (count.get() + 1), numTexCoords / 2 * (count.get() + 1)), 0, newFaces, finalFaces.length + numFaces * count.get(), mh.getFaces().length);
            System.arraycopy(mh.getFaceSmoothingGroups(), 0, newFaceSmoothingGroups, finalFaceSmoothingGroups.length + numFaceSmoothingGroups * count.getAndIncrement(), mh.getFaceSmoothingGroups().length);
        });
        points = newPoints;
        f = newF;
        texCoords = newTexCoords;
        faces = newFaces;
        faceSmoothingGroups = newFaceSmoothingGroups;
    }

    private static float[] transform(float[] points, Point3D p3d) {
        float[] newPoints = new float[points.length];
        for (int i = 0; i < points.length / 3; i++) {
            newPoints[3 * i] = points[3 * i] + p3d.x;
            newPoints[3 * i + 1] = points[3 * i + 1] + p3d.y;
            newPoints[3 * i + 2] = points[3 * i + 2] + p3d.z;
        }
        return newPoints;
    }

    private static int[] traslateFaces(int[] faces, int points, int texCoords) {
        int[] newFaces = new int[faces.length];
        for (int i = 0; i < faces.length; i++) {
            newFaces[i] = faces[i] + (i % 2 == 0 ? points : texCoords);
        }
        return newFaces;
    }

    public static AffineTransform getRotateInstance(double theta) {
        AffineTransform t = new AffineTransform();
        t.setToRotation(theta);
        return t;
    }

    public static AffineTransform getRotateInstance(double theta,
                                                    double x, double y) {
        AffineTransform t = new AffineTransform();
        t.setToTranslation(x, y);
        t.rotate(theta);
        t.translate(-x, -y);
        return t;
    }

    public static AffineTransform getScaleInstance(double sx, double sy) {
        AffineTransform t = new AffineTransform();
        t.setToScale(sx, sy);
        return t;
    }


    public static AffineTransform getShearInstance(double shx, double shy) {
        AffineTransform t = new AffineTransform();
        t.setToShear(shx, shy);
        return t;
    }



    public static void sphereWarp(Sphere sphere,int distance){
        int index=0;
        int rectWidth =  sphere.getDivisions()+1;
        int rectHeight =(sphere.getDivisions()+1)*2;
        int ratio = rectHeight/rectWidth;
        float[] textureCoords = new float[(sphere.getDivisions()+1)*(sphere.getDivisions()+1)*2];
        int scale = distance;
        float patternHeight = 0;
        float restHeight=patternHeight-((float)(1d/(patternHeight/scale)*ratio*rectWidth))%patternHeight;
        float factorHeight = (float)(1d+restHeight/(1d/(patternHeight/scale)*ratio*rectWidth));
        float patternWidth = 0;
        float restWidth=patternWidth-((float)(rectWidth/(patternWidth/scale)))%patternWidth;
        float factorWidth = (float)(1d+restWidth/(rectWidth/(patternWidth/scale)));
        boolean reverseTexture=false;
        if(reverseTexture){
            for (int x = 0; x <= rectWidth; x++) {
                float dx = (float) ((x)/(patternWidth/scale)*factorWidth);
                for (int y = 0; y <= rectHeight; y++) {
                    float dy = (float) ((y)/(patternHeight/scale)*ratio/rectHeight*rectWidth*factorHeight);
                    textureCoords[index] = dx;
                    textureCoords[index + 1] = dy;
                    index+=2;
                }
            }
        } else {
            for (int y = 0; y <= rectHeight; y++) {
                float dy = (float) ((y)/(patternHeight/scale)*ratio/rectHeight*rectWidth*factorHeight);
                for (int x = 0; x <= distance; x++) {
                    textureCoords[index] = (float) ((x)/(patternWidth/scale)*factorWidth);
                    textureCoords[index + 1] = dy;
                    index+=2;
                }
            }
        }

    }

}
