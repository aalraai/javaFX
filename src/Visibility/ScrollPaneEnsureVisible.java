package Visibility;/**
 * Created by akram on 15.02.17.
 */

import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class ScrollPaneEnsureVisible extends Application {

//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//
//        StackPane root = new StackPane();
//        root.setAlignment(Pos.TOP_LEFT);
//
//        ScrollPane scrollPane = new ScrollPane();
//        Pane pane = new Pane();
//        pane.setMinHeight(1000);
//        pane.setMinWidth(1000);
//        scrollPane.setContent(pane);
//
//        root.getChildren().add(scrollPane);
//        Label fixed = new Label("Fixed");
//        root.getChildren().add(fixed);
//
//        // Allow vertical scrolling of fixed element:
//        scrollPane.hvalueProperty().addListener( (observable, oldValue, newValue) -> {
//            double xTranslate = newValue.doubleValue() * (scrollPane.getViewportBounds().getWidth() - fixed.getWidth());
//            fixed.translateXProperty().setValue(-xTranslate);
//        });
//        // Allow horizontal scrolling of fixed element:
//        scrollPane.vvalueProperty().addListener( (observable, oldValue, newValue) -> {
//            double yTranslate = newValue.doubleValue() * (scrollPane.getViewportBounds().getHeight() - fixed.getWidth());
//            fixed.translateYProperty().setValue(-yTranslate);
//        });
//
//        Scene scene = new Scene(root, 500, 500);
//
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }

    @Override
    public void start(Stage primaryStage) {
        ScrollPane scrollPane = new ScrollPane();
        Pane content = new Pane();
        scrollPane.setContent(content);

        // adding background
        content.getChildren().add(new Rectangle(500, 500, Color.LIGHTGRAY));

        Label label = new Label("Hallo!!");
        label.setLayoutX(200);
        label.setLayoutY(200);
        label.setTextFill(Color.RED);
        label.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        Circle immovableObject = new Circle(30, Color.RED);
        content.getChildren().add(label);

        primaryStage.setScene(new Scene(scrollPane, 300, 300));
        primaryStage.show();

        // here we bind circle Y position
        label.layoutYProperty().bind(
                // to vertical scroll shift (which ranges from 0 to 1)
                scrollPane.vvalueProperty()
                        // multiplied by (scrollableAreaHeight - visibleViewportHeight)
                        .multiply(
                                content.heightProperty()
                                        .subtract(label.getLayoutY())));

        label.layoutXProperty().bind(
                scrollPane.hvalueProperty().multiply(content.widthProperty().subtract(new ScrollPaneViewPortHeightBinding(scrollPane)))
        );
    }

    // we need this class because Bounds object doesn't support binding
    private static class ScrollPaneViewPortHeightBinding extends DoubleBinding {

        private final ScrollPane root;

        public ScrollPaneViewPortHeightBinding(ScrollPane root) {
            this.root = root;
            super.bind(root.viewportBoundsProperty());
        }

        @Override
        protected double computeValue() {
            return root.getViewportBounds().getHeight();
        }
    }

    public static void main(String[] args) { launch(); }

//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//
//        StackPane root = new StackPane();
//        root.setAlignment(Pos.TOP_LEFT);
//
//        ScrollPane scrollPane = new ScrollPane();
//        Pane pane = new Pane();
//        pane.setMinHeight(1000);
//        pane.setMinWidth(1000);
//        scrollPane.setContent(pane);
//
//        root.getChildren().add(scrollPane);
//        Label fixed = new Label("Fixed");
//        root.getChildren().add(fixed);
//
////        // Allow vertical scrolling of fixed element:
////        scrollPane.hvalueProperty().addListener((observable, oldValue, newValue) -> {
////            double xTranslate = newValue.doubleValue() * (scrollPane.getViewportBounds().getWidth() - fixed.getWidth());
////            fixed.translateXProperty().setValue(-xTranslate);
////        });
////        // Allow horizontal scrolling of fixed element:
////        scrollPane.vvalueProperty().addListener((observable, oldValue, newValue) -> {
////            double yTranslate = newValue.doubleValue() * (scrollPane.getViewportBounds().getHeight() - fixed.getWidth());
////            fixed.translateYProperty().setValue(-yTranslate);
////        });
//
//        Scene scene = new Scene(root, 500, 500);
//
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
}