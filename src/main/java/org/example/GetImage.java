package org.example;

import javafx.scene.image.Image;

import java.net.URL;

public class GetImage {

    public static String getImagePath(String action, String themeName) {
        return "/org/image/" + action + "_" + themeName + ".png";
    }

    public static Image getImageUrl(String imagePath) {
        URL imageUrl = GetImage.class.getResource(imagePath);
        Image image = new Image(imageUrl.toExternalForm());
        return image;
    }

}