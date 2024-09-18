package com.oguzhan;


import java.io.Serializable;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Page extends TreeItem<String> implements Serializable{
    private static String imageUrl = "/com/oguzhan/images/pageIcon.png";
    public Page(String name, ImageView imageView){
        super(name, imageView);
    }

    @Override
    public boolean isLeaf() {
        return true; // Prevent this TreeItem from being expandable
    }

    public static ImageView getPageImage() {
        Image image = new Image(imageUrl);
        ImageView pageImage = new ImageView(image);
        pageImage.setFitHeight(16);  // Adjust size as needed
        pageImage.setFitWidth(16);
        return pageImage;
    }
}
