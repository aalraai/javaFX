package MVP.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import MVP.editor.EditorController;
import MVP.list.ListController;
import MVP.menu.MenuController;
import MVP.model.DataModel;

public class ContactApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();
        FXMLLoader listLoader = new FXMLLoader(getClass().getResource("/MVP/list/list.fxml"));
        root.setCenter(listLoader.load());
        ListController listController = listLoader.getController();

        FXMLLoader editorLoader = new FXMLLoader(getClass().getResource("/MVP/editor/editor.fxml"));
        root.setRight(editorLoader.load());
        EditorController editorController = editorLoader.getController();

        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/MVP/menu/menu.fxml"));
        root.setTop(menuLoader.load());
        MenuController menuController = menuLoader.getController();

        DataModel model = new DataModel();
        listController.initModel(model);
        editorController.initModel(model);
        menuController.initModel(model);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    // annoying Eclipse launch workaround:
    public static void main(String[] args) { launch(args); }
}