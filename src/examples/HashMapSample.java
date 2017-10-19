package examples;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

import java.util.HashMap;
import java.util.Map;

public class HashMapSample {

    public static void main(String[] args) {

        Map<Integer, String> map = new HashMap<>();
        ObservableMap<Integer, String> obsmap = FXCollections.observableMap(map);

        obsmap.addListener((MapChangeListener<? super Integer, ? super String>) e -> {
            System.out.println("changes in obsMap: {key= " + e.getKey() + ", value= " +e.getValueAdded()+"}");
        });

        map.put(1, "akram");
        map.put(2, "safira");
        map.put(3, "ayham");

        System.out.println("hashmap: " + map);
        System.out.println("obs hashmap: " + obsmap);

        obsmap.clear();
        map.clear();

        System.out.println("hashmap after clear: "+ map);
        System.out.println("obs hashmap after clear: " + obsmap);

    }
}
