package com.oguzhan;


import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Folder extends TreeItem<String> {
    
    private static String imageUrl = "/com/oguzhan/images/folderIcon.png";
    
    public Folder(String name, ImageView imageView) {
        super(name, imageView);
    }

    public static ImageView getFolderImage() {
        Image image = new Image(imageUrl);
        ImageView folderImage = new ImageView(image);
        folderImage.setFitHeight(16);  // Adjust size as needed
        folderImage.setFitWidth(16);
        return folderImage;
    }
    
}
