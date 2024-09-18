package com.oguzhan;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainController {
    
    @FXML
    private VBox itemContainer;

    @FXML
    private Label warningLabel1;

    @FXML
    private Label warningLabel2;

    @FXML
    private Button createButton;

    @FXML
    private TreeView<String> treeView;

    private ContextMenu folderContextMenu;
    private ContextMenu pageContextMenu;

    private Folder rootFolder;
    private Map<String, List<ItemView>> pageContents = new HashMap<>();

    @FXML
    private void initialize() {
        File file = new File("src/main/resources/com/oguzhan/save/treeview.json");
        if (file.exists()){
            try {
                SerializableTreeItem data = Serializer.loadTreeItemFromFile();
                treeView.setRoot(data.toTreeItem(treeView));
                pageContents.putAll(Serializer.loadMapFromFile());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            rootFolder = new Folder("Movies", Folder.getFolderImage());
            treeView.setRoot(rootFolder);
        }


        // To change context menu based on the TreeItem type. Page type is not expandable.
        folderContextMenu = new ContextMenu();
        pageContextMenu = new ContextMenu();
        MenuItem createFolder = new MenuItem("Create New Folder");
        createFolder.setOnAction(event -> addFolder());
        MenuItem createPage = new MenuItem("Create New Page");
        createPage.setOnAction(event -> addPage());
        MenuItem removeItem1 = new MenuItem("Remove");
        removeItem1.setOnAction(event -> removeItem());
        MenuItem removeItem2 = new MenuItem("Remove");
        removeItem2.setOnAction(event -> removeItem());
        MenuItem renameItem1 = new MenuItem("Rename");
        renameItem1.setOnAction(event -> renameItem());
        MenuItem renameItem2 = new MenuItem("Rename");
        renameItem2.setOnAction(event -> renameItem());

        folderContextMenu.getItems().addAll(createFolder, createPage, removeItem1, renameItem1);
        pageContextMenu.getItems().addAll(removeItem2, renameItem2);


        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue instanceof Page) {
                    try {
                        treeView.setContextMenu(pageContextMenu);
                        displayItemsInPage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (newValue instanceof Folder) {
                    treeView.setContextMenu(folderContextMenu);
                }
            }
        });


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                SerializableTreeItem serializableRoot = new SerializableTreeItem(treeView.getRoot(), "Folder");
                Serializer.saveTreeItemToFile(serializableRoot);
                Serializer.saveMapToFile(pageContents);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }

    @FXML
    private void renameItem() {
        TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
        TextInputDialog dialog;
        if (selectedItem instanceof Folder) {
            dialog = new TextInputDialog("New Folder Name");
            dialog.setTitle("Rename Folder");
            dialog.setHeaderText("Enter the name for the folder:");
            dialog.setContentText("Folder name:");
        } else {
            dialog = new TextInputDialog("New Page Name");
            dialog.setTitle("Rename Page");
            dialog.setHeaderText("Enter the name for the Page:");
            dialog.setContentText("Page name:");
        }
        
    
        // Show the dialog and wait for the user input
        Optional<String> result = dialog.showAndWait();
    
        result.ifPresent(folderName -> {
            if (!folderName.trim().isEmpty()) {
                selectedItem.setValue(folderName);
            }
        });
    }

    @FXML
    private void removeItem() {
        TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null && selectedItem != treeView.getRoot()) {
            selectedItem.getParent().getChildren().remove(selectedItem);
            if (selectedItem instanceof Page){
                pageContents.remove(selectedItem.getValue());
            }
        }
    }

    @FXML
    private void addPage() {
        boolean isValid = false;
        TextInputDialog dialog = new TextInputDialog("New Page");
        dialog.setTitle("Create New Page");
        dialog.setContentText("Page name:");
        while (!isValid){
            if (dialog.getHeaderText().equals("Confirmation")){
                dialog.setHeaderText("Enter the name for the new Page (Must be unique):");
            }

            // Show the dialog and wait for the user input
            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                String pageName = result.get().trim();
                if (pageName.isEmpty()) {
                    dialog.setHeaderText("Page name cannot be empty. Please enter a new page name:");
                    continue;
                } else if (pageContents.containsKey(pageName)){
                    dialog.setHeaderText("Page name already taken. Please enter a different name:");
                    continue;
                } else {
                    isValid = true;
                    TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
                    Page newItem = new Page(pageName, Page.getPageImage());
                    if (selectedItem != null && selectedItem instanceof Folder) {
                        selectedItem.getChildren().add(newItem);
                        treeView.getSelectionModel().select(newItem);
                        pageContents.put(newItem.getValue(), new ArrayList<ItemView>());
                    }
                }
            } else {
                // User canceled the dialog.
                isValid = true;
            }
        }
    }

    @FXML
    private void addFolder() {
        TextInputDialog dialog = new TextInputDialog("New Folder");
        dialog.setTitle("Create New Folder");
        dialog.setHeaderText("Enter the name for the new folder:");
        dialog.setContentText("Folder name:");
    
        // Show the dialog and wait for the user input
        Optional<String> result = dialog.showAndWait();
    
        result.ifPresent(folderName -> {
            if (!folderName.trim().isEmpty()) {
                TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
                Folder newItem = new Folder(folderName, Folder.getFolderImage());
                if (selectedItem != null && selectedItem instanceof Folder) {
                    selectedItem.getChildren().add(newItem);
                }
            }
        });
    }

    @FXML
    private void addItemView() throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxmlFiles/ItemView.fxml"));
            Pane itemView = loader.load();
            
            // Get the controller to manipulate the view
            ItemViewController controller = loader.getController();
            controller.setMainController(this);

            TreeItem<String> selectedFolder = treeView.getSelectionModel().getSelectedItem();

            ItemView itemViewObject = new ItemView(controller.getMovieName(), controller.getRunTime(),
            controller.getRating(), controller.getNotes(), selectedFolder.getValue());

            // Connect ItemView object to ItemViewController;
            controller.setItemView(itemViewObject);

            if (selectedFolder != null && selectedFolder instanceof Page) {
                pageContents.get(selectedFolder.getValue()).add(itemViewObject);
                itemContainer.getChildren().add(itemView);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void displayItemsInPage() throws IOException {
        TreeItem<String> selectedFolder = treeView.getSelectionModel().getSelectedItem();
        if (selectedFolder instanceof Folder) {
            itemContainer.getChildren().clear();
            itemContainer.getChildren().addAll(warningLabel1, warningLabel2);
        } else if (selectedFolder instanceof Page) {
            itemContainer.getChildren().clear();
            List<ItemView> items = pageContents.get(selectedFolder.getValue());
            if (items != null) {
                for (ItemView item : items){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("fxmlFiles/ItemView.fxml"));
                    Pane itemView = loader.load();
                    
                    // Get the controller to manipulate the view
                    ItemViewController controller = loader.getController();
                    controller.setMainController(this);
                    controller.setItemView(item);
                    itemContainer.getChildren().add(itemView);
                }
            }
        }
    }
    


    public Map<String, List<ItemView>> getPageContents() {
        return pageContents;
    }
}
