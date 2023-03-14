package com.cgvsu;

import com.cgvsu.model.ModelOnStage;
import com.cgvsu.objreader.ObjReaderException;
import com.cgvsu.objwriter.ObjWriter;
import com.cgvsu.polygonDeleter.PolygonDeleter;
import com.cgvsu.render_engine.RenderEngine;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.vecmath.Vector3f;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;

public class GuiController {

    final private float TRANSLATION = 0.5F;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private ArrayList<ModelOnStage> meshList = new ArrayList();

    private Camera camera = new Camera(
            new Vector3f(0, 00, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private Timeline timeline;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            if (meshList != null) {
                for (ModelOnStage mesh : meshList) {
                    if (mesh != null && mesh.visiblity) {
                        RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height);
                    } //цикл по списку моделей, рендерим активные модели
                }
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    private void onOpenModelMenuItemReadClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");


        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            ModelOnStage mesh = ObjReader.read(fileContent);
//            mesh.isActive = true;
            meshList.add(mesh); //добавлять в список моделей
            // todo: обработка ошибок
        } catch (IOException exception) {
            //обработать исключения здесь
        }
    }

    @FXML
    private void onOpenModelMenuItemWriteClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Save Model");
        String localPath = "";

        for (ModelOnStage mesh : meshList) {
            if (mesh.isActive) {

                File dir = directoryChooser.showDialog(canvas.getScene().getWindow());
                if (dir != null) {
                    localPath = dir.getAbsolutePath();
                }

                String filePath = localPath + "\\savedModel.obj";
                try {
                    System.out.println("Creating file");
                    ObjWriter.createObjFile(filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                File f = new File(filePath);
                try {
                    System.out.println("Writing to file");
                    ObjWriter.writeToFile(mesh, f);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Saved");
    }

    @FXML
    private void deletePoly() {
        for (ModelOnStage mesh : meshList) {
            if (mesh.isActive) {
                int size = mesh.polygons.size();
                //удаление всего
                for (int j = 1; j < size; j++) {
                    PolygonDeleter.deletePolygon(mesh, j);
                }
                System.out.println("Vertices: " + mesh.getVertices().size());
                System.out.println("Texture vertices: " + mesh.getTextureVertices().size());
                System.out.println("Normals: " + mesh.getNormals().size());
                System.out.println("Polygons: " + mesh.getPolygons().size());
            }
        }
    }

    @FXML
    private void changeActivityOfModel1() {
        ModelOnStage mesh = meshList.get(0);
        mesh.isActive = !mesh.isActive;
    }

    @FXML
    private void changeActivityOfModel2() {
        ModelOnStage mesh = meshList.get(1);
        mesh.isActive = !mesh.isActive;
    }

    @FXML
    private void changeVisibilityOfModel1() {
        ModelOnStage mesh = meshList.get(0);
        mesh.visiblity = !mesh.visiblity;
    }

    @FXML
    private void changeVisibilityOfModel2() {
        ModelOnStage mesh = meshList.get(1);
        mesh.visiblity = !mesh.visiblity;
    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
    }
}