package sample;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.*;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/*
+ A stage is used to display a scene. A scene
 */


public class Main extends Application {

    MenuBar menuBar;
    DoubleProperty vPosition = new SimpleDoubleProperty();
    DoubleProperty hPosition = new SimpleDoubleProperty();

    void buildMenuBar() {
        menuBar = new MenuBar();

        Menu file = new Menu("File");
        file.getItems().addAll(new MenuItem("Quit"));
        file.setOnAction(event -> {
            Platform.exit();
        });

        menuBar.getMenus().addAll(file);


    }

    ScrollPane testPane() {
        ScrollPane scr = new ScrollPane();

        Rectangle r1 = new Rectangle(10, 200, 140, 200);
        r1.setFill(Color.LIGHTGRAY);

        Rectangle r2 = new Rectangle(20, 40, 40, 100);
        r2.setFill(Color.RED);

        Rectangle r3 = new Rectangle(200, 200, 180, 180);
        r3.setFill(Color.BLACK);

        Text tx = new Text("Akram");
        tx.setFill(Color.RED);

        Rectangle rect = new Rectangle(0,0, Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        rect.setManaged(false);
        tx.boundsInParentProperty().addListener((v,o,n)->{
            rect.setLayoutX(tx.getBoundsInParent().getMinX());
            rect.setLayoutY(tx.getBoundsInParent().getMinY());
            rect.setWidth(tx.getBoundsInParent().getWidth());
            rect.setHeight(tx.getBoundsInParent().getHeight());
        });
        tx.setVisible(false);
        rect.setVisible(false);

        Group gr = new Group();
        {
            gr.getChildren().addAll(r1);
            gr.getChildren().addAll(r2);
            gr.getChildren().addAll(r3);

            gr.getChildren().addAll(tx);
            gr.getChildren().addAll(rect);

        }

        scr.setContent(gr);

        addIfAbsent(r2, r3, tx, scr);

        return scr;
    }

    void addIfAbsent(Rectangle a, Rectangle b, Text label, ScrollPane pane) {

        vPosition.bind(pane.vvalueProperty());
        hPosition.bind(pane.hvalueProperty());

        vPosition.addListener((v,o,n) -> {
            List<Node> inVisN = getInvisibleElement(pane);
            Double x = b.getLayoutBounds().getWidth();
            Double y = b.getLayoutBounds().getHeight();
            System.out.println("o: " + o.longValue());
            System.out.println("n: " + n.longValue());

            if(inVisN.contains(a)) {
                    label.setLayoutX(b.getLayoutBounds().getMinX() + 50);
                    label.setLayoutY(b.getLayoutBounds().getMinY() + 20);
                    label.setVisible(true);
            } else {
                label.setVisible(false);
            }
        });

        hPosition.addListener((v,o,n) -> {
            List<Node> inVisN = getInvisibleElement(pane);


            System.out.println("o: " + o.longValue());
            System.out.println("n: " + n.longValue());

            if(inVisN.contains(a)) {
                label.setLayoutX(b.getLayoutBounds().getMinX() + 50);
                label.setLayoutY(b.getLayoutBounds().getMinY() + 20);
                label.setVisible(true);
            } else {
                label.setVisible(false);
            }
        });

    }


    public List<Node> getInvisibleElement(ScrollPane pane) {
        List<Node> invisibleNodes = new ArrayList<Node>();
        Bounds paneBounds = pane.localToScene(pane.getBoundsInParent());
        if (pane.getContent() instanceof Parent) {
            for (Node n : ((Parent) pane.getContent()).getChildrenUnmodifiable()) {
                Bounds nodeBounds = n.localToScene(n.getBoundsInLocal());
                if (!paneBounds.intersects(nodeBounds)) {
                    invisibleNodes.add(n);
                }
            }
        }
        return invisibleNodes;
    }

    public List<Node> getVisibleElement(ScrollPane pane) {
        List<Node> visibleNodes = new ArrayList<Node>();
        Bounds paneBounds = pane.localToScene(pane.getBoundsInParent());
        if (pane.getContent() instanceof Parent) {
            for (Node n : ((Parent) pane.getContent()).getChildrenUnmodifiable()) {
                Bounds nodeBounds = n.localToScene(n.getBoundsInLocal());
                if (!paneBounds.intersects(nodeBounds)) {
                    visibleNodes.add(n);
                }
            }
        }
        return visibleNodes;
    }

//    public static void

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();
        ScrollPane scroll = testPane();
        Scene scene = new Scene(root, 300, 275);

        buildMenuBar();

        root.setTop(menuBar);
        root.setCenter(scroll);

        primaryStage.setTitle("GUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
