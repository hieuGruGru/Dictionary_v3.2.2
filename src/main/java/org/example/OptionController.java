package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OptionController implements Initializable {
    @FXML
    AnchorPane ParentPane;
    @FXML
    Pane ParentPane2, contentArea, titledPane;
    @FXML
    Button logOut, next;
    @FXML
    ImageView ImageMode;
    @FXML
    Label languageLabel, voiceLabel;

    private double x,y;

    @FXML
    private void dragged(MouseEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    @FXML
    private void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minimize(MouseEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImageMode.setImage(GetImage.getImageUrl(GetImage.getImagePath("Option", App.appTheme)));
    }


    @FXML
    private void logOut(ActionEvent actionEvent) throws IOException {
        App.setScene("LoginSample");
        App.loadCSS("login");
    }

    @FXML
    private void Next(ActionEvent actionEvent) throws IOException {
        App.setScene("MainSample");
        App.loadCSS(App.appTheme);
    }

    @FXML
    private void switchLightTheme(ActionEvent actionEvent){
        App.appTheme = "lightmode";
        App.loadCSS("lightmode");
        ImageMode.setImage(GetImage.getImageUrl(GetImage.getImagePath("Option", App.appTheme)));
    }

    @FXML
    private void switchWarmTheme(ActionEvent actionEvent){
        App.appTheme = "warmmode";
        App.loadCSS("warmmode");
        ImageMode.setImage(GetImage.getImageUrl(GetImage.getImagePath("Option", App.appTheme)));
    }

    @FXML
    private void switchDarkTheme(ActionEvent actionEvent){
        App.appTheme = "darkmode";
        App.loadCSS("darkmode");
        ImageMode.setImage(GetImage.getImageUrl(GetImage.getImagePath("Option", App.appTheme)));
    }

    @FXML
    private void EToV(ActionEvent actionEvent) {
        App.languageMode = 1;
        languageLabel.setText("Tiếng Anh - Tiếng Việt");
    }

    @FXML
    private void VToE(ActionEvent actionEvent){
        App.languageMode  = 2;
        languageLabel.setText("Tiếng Việt - Tiếng Anh");
    }

    @FXML
    private void changeVoice1(ActionEvent actionEvent) {
        voiceLabel.setText("Giọng đọc 1");
    }

    @FXML
    private void changeVoice2(ActionEvent actionEvent) {
        voiceLabel.setText("Giọng đọc 2");
    }

}
