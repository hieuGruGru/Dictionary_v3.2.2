package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class App extends Application {

    private static Scene scene;
    private static Parent parent;
    private String logoPath = "/org/image/logo_.png";
    private Image logoImage = GetImage.getImageUrl(logoPath);
    protected static String appTheme = "lightmode";
    protected static int languageMode;
    protected static int sourceFile = 1;
    protected static String desFile = "";


    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("LoginSample"), 750, 550);
        stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setTitle("Dictionary_v3.2.0");
        stage.getIcons().add(logoImage);
        stage.show();
    }

    protected static void setScene(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        loadCSS(appTheme);

    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        parent = fxmlLoader.load();
        return parent;
    }

    protected static Parent loadCSS(String css) {
        parent.getStylesheets().clear();
        parent.getStylesheets().add(App.class.getResource(css + ".css").toExternalForm());
        return parent;
    }


    public static void main(String[] args) {
        launch();
    }

}

