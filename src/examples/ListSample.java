package examples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListSample {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        Map<Integer, String> map = new HashMap<>();

        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");

        map.put(1, "e");
        map.put(2, "f");
        map.put(3, "g");
        map.put(4, "h");


        System.out.println("list: " + list);
        System.out.println("map: " + map);

        List<String> listmap = new ArrayList<>();
        listmap.addAll(list);
        listmap.addAll(map.values().stream().collect(Collectors.toList()));

        System.out.println("listmap: " + listmap);
//        System.out.println("concatination of list and mapvalues: " + list.addAll(listmap));


    }
}
