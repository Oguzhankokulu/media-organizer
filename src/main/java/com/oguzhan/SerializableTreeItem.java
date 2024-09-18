package com.oguzhan;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class SerializableTreeItem implements Serializable {
    private String value, type;
    private List<SerializableTreeItem> children = new ArrayList<>();

    // Constructor, getters, setters

    public SerializableTreeItem(TreeItem<String> treeItem, String type) {
        this.value = treeItem.getValue();
        this.type = type;
        for (TreeItem<String> child : treeItem.getChildren()) {
            this.children.add(new SerializableTreeItem(child, SerializableTreeItem.determineType(child)));
        }
    }

    public TreeItem<String> toTreeItem(TreeView<String> treeView) {
        if (treeView.getRoot() == null){
            Folder folder = new Folder(value, Folder.getFolderImage());
            treeView.setRoot(folder);
            for (SerializableTreeItem child : children) {
                treeView.getRoot().getChildren().add(child.toTreeItem(treeView));
            }
            return treeView.getRoot();
        }
        if ( type.equals("Page") ) {
            Page page = new Page(value, Page.getPageImage());
            return page;
        } else {
            Folder folder = new Folder(value, Folder.getFolderImage());
            for (SerializableTreeItem child : children) {
                folder.getChildren().add(child.toTreeItem(treeView));
            }
            return folder;
        } 
    }

    private static String determineType(TreeItem<String> item) {
        if (item instanceof Folder) {
            return "Folder";
        } else if (item instanceof Page) {
            return "Page";
        }
        return null; // Or throw an exception if the type is unknown
    }
}