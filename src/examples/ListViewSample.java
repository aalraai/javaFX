package examples;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/*
Add items to a map using the UI on the right and, as the keys in the extension -> mimeType map change, you will see the
list of keys shown in the ListView on the left automatically update.

The solution listens for changes to an ObservableMap which wraps the extension -> mimetype map and, when a key in the
map changes, applies relevant updates to an ObservableList which backs the ListView.


 */

public class ListViewSample extends Application{

    Map<String, String> keyToValueMap = new HashMap<>();

    ObservableMap<String, String> observableKeyToValueMap = FXCollections.observableMap(keyToValueMap);

    @Override
    public void start(Stage stage) throws Exception {
        // create an observable wrapper for our map data


        // create an ListView based on key items in the map.
        ListView<String> stringListView = new ListView<>();
        stringListView.getItems().setAll(keyToValueMap.keySet());
        stringListView.setPrefWidth(100);
        stringListView.setCellFactory(param -> new ListCellView(stringListView));

        observableKeyToValueMap.addListener((MapChangeListener<? super String, ? super String>) e->{
            System.out.println("{key= " +e.getKey() + ", value= "+ e.getValueAdded()+", size= " +e.getMap().size()+"}");
            stringListView.getItems().removeAll(e.getKey());
            if(e.wasAdded()){
                stringListView.getItems().add(e.getKey());
            }
        });

        // layout the app.
        Pane layout = new HBox(stringListView, createAddExtensionPane(observableKeyToValueMap));
        layout.setPrefHeight(150);

        // display the app.
        stage.setScene(new Scene(layout));
        stage.show();

    }

    private GridPane createAddExtensionPane(Map<String, String> map) {
        GridPane addExtensionPane = new GridPane();

        addExtensionPane.add(new Label("key:"), 0, 0);
        TextField key = new TextField();
        addExtensionPane.add(key, 1, 0);

        addExtensionPane.add(new Label("value:"), 0, 1);
        TextField value = new TextField();
        addExtensionPane.add(value, 1, 1);

        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            map.put(key.getText(), value.getText());
            System.out.println("Map: " + keyToValueMap.toString());
        });
        addExtensionPane.add(addButton, 1, 2);

        addExtensionPane.setPadding(new Insets(10));
        addExtensionPane.setHgap(5);
        addExtensionPane.setVgap(10);


        return addExtensionPane;
    }

    private class ListCellView extends TextFieldListCell<String> {

        ListView<String> myListView;

        public ListCellView(ListView<String> extensionListView) {
            myListView = extensionListView;
        }

        public void installContextMenu(final String item) {
            ContextMenu menu = new ContextMenu(); {
                MenuItem removeApp = new MenuItem("Remove");
                removeApp.setOnAction(e -> {
                    System.out.println("item to be removed: " + item);
                    myListView.getItems().remove(item);
//                    observableKeyToValueMap.remove(item);
                    keyToValueMap.remove(item);
                    System.out.println("map: " + keyToValueMap.toString());
                    System.out.println("obsMap: " + observableKeyToValueMap.toString());
                });

                menu.getItems().add(removeApp);
            }

//        myController.stateGenerationProperty().addListener((v, o, n) -> getListView().refresh());
            setContextMenu(menu);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
                setOnContextMenuRequested(null);
                return;
            }

            setText(item);

            setOnMouseEntered(event -> installContextMenu(item));
            setOnMouseReleased(event -> installContextMenu(item));
        }

    }

}
