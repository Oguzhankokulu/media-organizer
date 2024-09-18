package com.oguzhan;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

// Assuming you have Page and ItemView classes that can be serialized.
public class Serializer {

    public static void saveMapToFile(Map<String, List<ItemView>> map) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/com/oguzhan/save/pageContents.dat"))) {
            oos.writeObject(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked")
    public static Map<String, List<ItemView>> loadMapFromFile() {
        Map<String, List<ItemView>> map = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/main/resources/com/oguzhan/save/pageContents.dat"))) {
            map = (Map<String, List<ItemView>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void saveTreeItemToFile(SerializableTreeItem serializableItem) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(serializableItem);
        
        try (FileWriter fileWriter = new FileWriter("src/main/resources/com/oguzhan/save/treeview.json")) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SerializableTreeItem loadTreeItemFromFile() {
        Gson gson = new Gson();
        SerializableTreeItem serializableRoot;
        try (FileReader fileReader = new FileReader("src/main/resources/com/oguzhan/save/treeview.json")) {
            serializableRoot = gson.fromJson(fileReader, SerializableTreeItem.class);
            return serializableRoot;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

