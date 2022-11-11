package com.example.infograph;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.scene.*;

import java.awt.geom.AffineTransform;
import java.time.Clock;

import javafx.scene.control.*;
import javafx.animation.Timeline;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class HelloApplication extends Application {

    public ComboBox combo = null;
    public Shape3D shape =null;
    @Override
    public void start(Stage stage) throws IOException {
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateX(10);
        camera.setTranslateZ(-100);
        camera.setFieldOfView(20);
        Button btn1 = new Button("Cylindre");
        Button btn2 = new Button("Sphere");
        Button btn3 = new Button("Tore");
        Button btn4 = new Button("Capsule");
        Button btn5 = new Button("Forme complexe 1");
        Button btn6 = new Button("Forme complexe 2");
        TextField txt = new TextField();
        TextField txt1 = new TextField();
        TextField txt2 = new TextField();
        TextField txt3 = new TextField();
        CameraTransformer cameraTransform = new CameraTransformer();
        cameraTransform.getChildren().add(camera);
        cameraTransform.ry.setAngle(-30.0);
        cameraTransform.rx.setAngle(-15.0);

        //System.out.println(CapsuleMesh.createCapsule(64,(float) capsule.getRadius(),(float)capsule.getHeight()).getFaceElementSize());




       // capsule.setTextureModeVertices3D(1530, p -> p.f);

        Group group = new Group();

        final SubScene subScene = new SubScene(group, 500, 500, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        final Pane pane3D = new Pane() {
            {
                getChildren().add(subScene);
            }

            @Override
            protected void layoutChildren() {
                subScene.setWidth(getWidth());
                subScene.setHeight(getHeight());
            }
        };
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                group.getChildren().clear();
                long startTime = System.nanoTime();
                Cylinder cylinder = new Cylinder(5,10);

                cylinder.setMaterial(new PhongMaterial(Color.LIGHTGRAY));
                TriangleMesh cylender = CapsuleMesh.createCapsule(correctDivisions(64),5,10);
                cylinder.setTranslateX(12);
                txt1.setText(String.valueOf(cylender.getTexCoords().size()));
                txt2.setText(String.valueOf(cylender.getPoints().size()));
                txt3.setText(String.valueOf(cylender.getFaces().size()));
                long endTime = System.nanoTime();
                long duration = endTime-startTime;
                txt.setText(String.valueOf(duration)+" ns");
                group.getChildren().addAll(cameraTransform,cylinder);
                shape=cylinder;
            }
        });
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                group.getChildren().clear();
                long startTime = System.nanoTime();
                Sphere sphere = new Sphere(5);
                sphere.setMaterial(new PhongMaterial(Color.LIGHTGRAY));
                TriangleMesh sphereM = CapsuleMesh.createCapsule(correctDivisions(64),5,50);
                txt1.setText(String.valueOf(sphereM.getTexCoords().size()));
                txt2.setText(String.valueOf(sphereM.getPoints().size()));
                txt3.setText(String.valueOf(sphereM.getFaces().size()));
                sphere.setTranslateX(12);
                long endTime = System.nanoTime();
                long duration = endTime-startTime;
                txt.setText(String.valueOf(duration)+" ns");
                group.getChildren().addAll(cameraTransform,sphere);
                shape=sphere;
            }
        });

        btn3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                group.getChildren().clear();
                long startTime = System.nanoTime();
                ToreMesh tore = new ToreMesh(200, 100, 10, 5);
                tore.setTranslateX(12);
                TriangleMesh torusMesh = ToreMesh.createTorus(200, 100, 10, 5,0,0,0,1);
                txt1.setText(String.valueOf(torusMesh.getTexCoords().size()));
                txt2.setText(String.valueOf(torusMesh.getPoints().size()));
                txt3.setText(String.valueOf(torusMesh.getFaces().size()));
                long endTime = System.nanoTime();
                long duration = endTime-startTime;
                txt.setText(String.valueOf(duration)+" ns");
                group.getChildren().addAll(cameraTransform,tore);
                shape=tore;
            }
        });
        btn4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                group.getChildren().clear();
                long startTime = System.nanoTime();
                CapsuleMesh capsule = new CapsuleMesh(5,10);
                capsule.setTranslateX(12);
                TriangleMesh capsuleM = CapsuleMesh.createCapsule(correctDivisions(64),50,100);
                long endTime = System.nanoTime();
                long duration = endTime-startTime;
                txt.setText(String.valueOf(duration)+" ns");
                txt1.setText(String.valueOf(capsuleM.getTexCoords().size()));
                txt2.setText(String.valueOf(capsuleM.getPoints().size()));
                txt3.setText(String.valueOf(capsuleM.getFaces().size()));
                group.getChildren().addAll(cameraTransform,capsule);
                shape = capsule;
            }
        });
        btn6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                group.getChildren().clear();
                long startTime = System.nanoTime();
                AffineTransform affineTransform = new AffineTransform();
                affineTransform = Mesh.getRotateInstance(5);
                affineTransform = Mesh.getScaleInstance(5,10);
                affineTransform = Mesh.getShearInstance(5,10);
                CapsuleMesh capsule = new CapsuleMesh(5,10);
                capsule.setTranslateX(12);
                Sphere sphere = new Sphere(5);
                sphere.setTranslateX(15);
                Sphere sphere1 = new Sphere(5);
                TriangleMesh capsuleM = CapsuleMesh.createCapsule(correctDivisions(64),5,10);
                txt1.setText(String.valueOf(capsuleM.getTexCoords().size()));
                txt2.setText(String.valueOf(capsuleM.getPoints().size()));
                txt3.setText(String.valueOf(capsuleM.getFaces().size()));
                long endTime = System.nanoTime();
                long duration = endTime-startTime;
                txt.setText(String.valueOf(duration)+" ns");
                sphere1.setTranslateX(9);
                group.getChildren().addAll(cameraTransform,capsule,sphere,sphere1);
            }
        });
        btn5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                group.getChildren().clear();
                long startTime = System.nanoTime();
                CapsuleMesh capsule = new CapsuleMesh(5,10);
                SpringMesh spring = new SpringMesh(10, 2, 2, 8 * 2 * Math.PI, 200, 100, 0, 0);
                capsule.setCullFace(CullFace.NONE);
                TriangleMesh sprin = (TriangleMesh) spring.mesh;
                Sphere sphere = new Sphere(5,10);
                Point3D point3D = new Point3D(3,5,10);
                Point3D point3 = new Point3D(3,5,10);

                txt1.setText(String.valueOf(sprin.getTexCoords().size()));
                txt2.setText(String.valueOf(sprin.getPoints().size()));
                txt3.setText(String.valueOf(sprin.getFaces().size()));
                long endTime = System.nanoTime();
                long duration = endTime-startTime;
                Mesh.sphereWarp(sphere,(int)point3.distance(point3D)-1);
                txt.setText(String.valueOf(duration)+" ns");
                group.getChildren().addAll(spring);
                shape = spring;
            }
        });

        final Button button = new Button("Rotation");
        button.setOnAction(actionEvent -> toggleAnimation());
        combo = new ComboBox(FXCollections.observableArrayList(Rotate.X_AXIS, Rotate.Y_AXIS, Rotate.Z_AXIS));
        combo.getSelectionModel().select(Rotate.Y_AXIS);
        combo.setButtonCell(new AxisListCell());
        combo.setCellFactory(listView -> new AxisListCell());
        combo.valueProperty().addListener(observable -> {
            if (animation != null) {
                boolean wasRunning = (animation.getStatus() == Timeline.Status.RUNNING);
                animation.stop();
                animation = null;
                shape.getTransforms().clear();
                if (wasRunning) {
                    toggleAnimation();
                }
            }
        });
        Label t = new Label("Temps");
        Label t1 = new Label("nombre de primitives");
        Label t2 = new Label("Vertexes");
        Label t3 = new Label("Triangles");
        Label s = new Label("Statistiques de génération =>");
        final ToolBar toolBar = new ToolBar(btn1,btn2,btn3,btn4,btn5,btn6,button,combo,s,t,txt,t1,txt1,t2,txt2,t3,txt3);
        final BorderPane borderPane = new BorderPane();
        borderPane.setTop(toolBar);
        borderPane.setCenter(pane3D);
        Scene scene = new Scene(borderPane, 1000, 800);
        scene.setFill(Color.BISQUE);
        //scene.setCamera(camera);

        stage.setScene(scene);
        stage.setTitle("FXyz3D Sample");
        stage.show();
    }
    private Timeline animation;

    private void toggleAnimation() {
        if (animation == null) {
            final Point3D axis = (Point3D) combo.getValue();
            final Rotate rotate = new Rotate(0, axis);
            // Centre rotation = centre sphere - centre cylindre
            rotate.setPivotX(400 - 600);
            rotate.setPivotY(400 - 400);
            rotate.setPivotZ(0 - 0);
            animation = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), 0)),
                    new KeyFrame(Duration.seconds(5), new KeyValue(rotate.angleProperty(), 360))
            );
            animation.setCycleCount(RotateTransition.INDEFINITE);
            shape.getTransforms().add(rotate);
        }
        if (animation.getStatus() == Timeline.Status.RUNNING) {
            animation.pause();
        } else {
            animation.play();
        }
    }
    private static int correctDivisions(int paramInt) {
        return (paramInt + 3) / 4 * 4;
    }
    private static class AxisListCell extends ListCell<javafx.geometry.Point3D> {

        @Override
        protected void updateItem(Point3D item, boolean empty) {
            int col,row;
            super.updateItem(item, empty);
            String text = null;
            if (!empty && item != null) {
                if (Rotate.X_AXIS.equals(item)) {
                    text = "Axe X (" + item + ")";
                    double [][] vect = new double[4][4];
                    Matrix.transpose(vect);
                    Matrix.inverse(vect);
                } else if (Rotate.Y_AXIS.equals(item)) {
                    text = "Axe Y (" + item + ")";
                } else if (Rotate.Z_AXIS.equals(item)) {
                    text = "Axe Z (" + item + ")";
                }
            }
            setText(text);
        }
    }


    public static void main(String[] args) {
        launch();
    }
}