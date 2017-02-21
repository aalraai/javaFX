package geometry;/**
 * Created by akram on 20.02.17.
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeometryFX extends Application {


    double orgSceneX, orgSceneY;
    MenuBar menuBar;
    final Group gr = new Group();
    static final Random myRand = new Random();
    static final int SCENE_SIZE = 800;


    void buildMenu() {
        menuBar = new MenuBar();
        Menu file = new Menu("File");
        MenuItem quit = new MenuItem("Quit");
        MenuItem generate = new MenuItem("Generate");
        MenuItem resolve = new MenuItem("Resolve");
        MenuItem delete = new MenuItem("Delete");
        MenuItem drawLines = new MenuItem("Draw Lines");

        file.getItems().addAll(generate);
        generate.setOnAction(event -> {
            generateCircles();
        });

        file.getItems().addAll(resolve);
        resolve.setOnAction(event -> {
            resolveCircles();
        });

        file.getItems().addAll(drawLines);
        drawLines.setOnAction(event -> {
            drawLines();
        });

        file.getItems().addAll(delete);
        delete.setOnAction(event -> {
            deleteCircles();
        });

        file.getItems().addAll(quit);
        quit.setOnAction(event -> {
            Platform.exit();
        });


        menuBar.getMenus().addAll(file);
    }

    private ScrollPane buildPane() {
        ScrollPane scr = new ScrollPane();

        scr.setContent(gr);

        return scr;
    }

    void generateCircles() {
        int radius = 5 * myRand.nextInt(20);
        for (int i = 0; i < 20; i++) {
            Circle c1 = new Circle(myRand.nextInt(SCENE_SIZE - radius * 2) + radius,
                    myRand.nextInt(SCENE_SIZE - radius * 2) + radius,
                    radius,
                    new Color(myRand.nextDouble(), myRand.nextDouble(), myRand.nextDouble(), myRand.nextDouble()));

            c1.setOnMousePressed(e -> {
                orgSceneX = e.getSceneX();
                orgSceneY = e.getSceneY();

                Circle c = (Circle) (e.getSource());
                c.toFront();

            });

            c1.setOnMouseDragged(e -> {
                double offsetX = e.getSceneX() - orgSceneX;
                double offsetY = e.getSceneY() - orgSceneY;

                Circle c = (Circle) (e.getSource());

                c.setCenterX(c.getCenterX() + offsetX);
                c.setCenterY(c.getCenterY() + offsetY);

                orgSceneX = e.getSceneX();
                orgSceneY = e.getSceneY();
            });

            gr.getChildren().addAll(c1);

        }

    }

    private void resolveCircles() {
        List<Node> circles = new ArrayList<>();
        circles.addAll(gr.getChildren());
        if (circles.size() > 0) {
            boolean overlapping = true;
            int limit = 100000;
            while (overlapping && limit-- > 0) {
                overlapping = false;
                for (int i = 0; i < circles.size(); i++) {
                    Node node = circles.get(i);
                    if (node instanceof Circle) {
                        for (int k = 0; k < circles.size(); k++) {
                            Node node2 = circles.get(k);
                            if (node2 instanceof Circle) {
                                if (node != node2) {
                                    Double dx = ((Circle) node).getCenterX() - ((Circle) node2).getCenterX();
                                    Double dy = ((Circle) node).getCenterY() - ((Circle) node2).getCenterY();
                                    Double dist = Math.sqrt(dx * dx + dy * dy);

                                    if (dist <= ((Circle) node).getRadius() + ((Circle) node2).getRadius()) {

                                        ((Circle) node).setCenterX(myRand.nextInt(SCENE_SIZE + 5 * myRand.nextInt(20) * 2) + ((Circle) node).getRadius());
                                        ((Circle) node).setCenterY(myRand.nextInt(SCENE_SIZE + 5 * myRand.nextInt(20) * 2) + ((Circle) node).getRadius());
                                        overlapping = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("Finished!");
        }
    }

    void drawLines() {
        if (gr.getChildren().size() > 0) {
            for (int i = 0; i < gr.getChildren().size(); i++) {
                Node node = gr.getChildren().get(i);
                if (node instanceof Circle) {
                    for (int k = 0; k < gr.getChildren().size(); k++) {
                        Node node2 = gr.getChildren().get(k);
                        if (node2 instanceof Circle) {
                            if (node != node2) {
                                Line line = new Line();

                                line.setStroke(Color.LIGHTGRAY);
                                line.startXProperty().bind(((Circle) node).centerXProperty().add(node.translateXProperty()));
                                line.startYProperty().bind(((Circle) node).centerYProperty().add(node.translateYProperty()));
                                line.endXProperty().bind(((Circle) node2).centerXProperty().add(node2.translateXProperty()));
                                line.endYProperty().bind(((Circle) node2).centerYProperty().add(node2.translateYProperty()));

                                gr.getChildren().addAll(line);
                            }
                        }
                    }
                }

            }
        }
    }


    private void deleteCircles() {
        gr.getChildren().clear();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        ScrollPane pane = buildPane();
        Scene scene = new Scene(root, SCENE_SIZE, SCENE_SIZE);

        buildMenu();

        root.setTop(menuBar);
        root.setCenter(pane);

        primaryStage.setTitle("Geometry");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
