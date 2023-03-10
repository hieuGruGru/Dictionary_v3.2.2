package org.example;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController implements Initializable{

    @FXML
    public Pane ParentPane;
    @FXML
    public Pane titlePane;
    @FXML
    ListView<String> listView = new ListView<>();
    @FXML
    TextField searchText = new TextField();
    @FXML
    Label firstLabel;
    @FXML
    Label secondLabel;
    @FXML
    TextField firstText = new TextField();
    @FXML
    TextField secondText1 = new TextField();
    @FXML
    TextField secondText2= new TextField();
    @FXML
    TextField statusText = new TextField();
    @FXML
    Button buttonSearch;
    @FXML
    Button buttonAdd;
    @FXML
    Button buttonFix;
    @FXML
    Button buttonDel;
    @FXML
    Button buttonSave;
    @FXML
    Button speech;
    @FXML
    Button buttonReset;

    private double x, y;
    private String desPath = "";
    private String action = "";
    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
    String inputPath = "";
    String outputPath = "";

    @FXML
    public void dragged(MouseEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    @FXML
    public void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    public void close(MouseEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void minimize(MouseEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (App.languageMode  ==  1) {
            inputPath = "/org/example/Etest" + App.sourceFile + ".txt";
            System.out.println(App.sourceFile);
            System.out.println(inputPath);
            outputPath = "D:\\java\\Dictionary\\Dictionary_v3.2.2\\target\\classes" +
                    "\\org\\example\\Etest" + (3 - App.sourceFile) + ".txt";
            firstLabel.setText("Ti???ng Anh");
            secondLabel.setText("Ti???ng Vi???t");

        } else {
            inputPath = "/org/example/Vtest" + App.sourceFile + ".txt";
            outputPath = "D:\\java\\Dictionary\\Dictionary_v3.2.2\\target\\classes" +
                    "\\org\\example\\Vtest"  + (3 - App.sourceFile) + ".txt";
            firstLabel.setText("Ti???ng Vi???t");
            secondLabel.setText("Ti???ng Anh");
        }
        InputStream is = getClass().getResourceAsStream(inputPath);
        System.out.println(getClass().getResource(inputPath));
        DictionaryMgmt.init(is);
        reloadList();
    }

    private void reloadList() {
        listView.getItems().clear();
        DictionaryMgmt.dict1.list.clear();
        DictionaryMgmt.loadToList(DictionaryMgmt.dict1.trie.root, DictionaryMgmt.dict1.list);
        for (int i = 0; i  < DictionaryMgmt.dict1.list.size(); i ++ ) {
            String word = DictionaryMgmt.dict1.list.get(i).getTarget();
            listView.getItems().add(word);
        }
    }

    @FXML
    private void showListView() {
        String word = searchText.getText().trim();
        if (word.isEmpty()) {
            reloadList();
        } else {
            Dictionary dict2 = new Dictionary();
            dict2.trie.root = DictionaryMgmt.search(word);
            listView.getItems().clear();
            dict2.list.clear();
            DictionaryMgmt.loadToList(dict2.trie.root, dict2.list);
            for (int i = 0; i  < dict2.list.size(); i ++ ) {
                String word1 = dict2.list.get(i).getTarget();
                listView.getItems().add(word1);
            }
        }
    }


    @FXML
    public void getMeaning(MouseEvent event) {
        String word = listView.getSelectionModel().getSelectedItem();
        String meaning1 = DictionaryMgmt.search(word).getMeaning1();
        String meaning2 = DictionaryMgmt.search(word).getMeaning2();
        firstText.setText(word);
        secondText1.setText(meaning1);
        secondText2.setText(meaning2);
    }


    @FXML
    public void addWord(ActionEvent event) throws IllegalAccessException {
        String word = firstText.getText().trim();
        String meaning1 = secondText1.getText().trim();
        String meaning2 = secondText2.getText().trim();
        if (!DictionaryMgmt.validateString(word) ||
            !DictionaryMgmt.validateString(meaning1)) {
            statusText.setText("?????nh l???a ai??? Vui l??ng th??m t??? h???n hoi");
        } else {
            if (!DictionaryMgmt.isWord(word)) {
                DictionaryMgmt.insert(word, meaning1, meaning2);
                statusText.setText("???? th??m t??? " + word + " v??o t??? ??i???n");
            } else {
                statusText.setText("T??? " + word + " ???? t???n t???i trong t??? ??i???n");
            }
        }
        reloadList();
    }

    @FXML
    public void deleteWord(ActionEvent actionEvent) throws IllegalAccessException {
        String word = listView.getSelectionModel().getSelectedItem().trim();
        int index = listView.getSelectionModel().getSelectedIndex();
        confirmAlert.setTitle("Wait a minute");
        confirmAlert.setHeaderText("B???n c?? ch???c mu???n x??a t??? " + word + " kh???i t??? ??i???n?");
        confirmAlert.setContentText("");
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.get() == ButtonType.OK) {
            listView.getItems().remove(index);
            DictionaryMgmt.delete(word);
            statusText.setText("???? x??a t??? '" + word + "' kh???i t??? ??i???n");
            firstText.clear();
            secondText1.clear();
            secondText2.clear();
        } else {
            statusText.setText("May cho m??y ?????y " + word + " ??? ??? ???_??? ??????");
        }
        reloadList();
    }

    @FXML
    public void updateWord(ActionEvent event) throws IllegalAccessException {

        String word = listView.getSelectionModel().getSelectedItem();
        if (word == null) {
            statusText.setText("H??y ch???n t??? ????? s???a");
        } else {
            String meaning1 = secondText1.getText();
            String meaning2 = secondText2.getText().trim();
            String currentMeaning1 = DictionaryMgmt.search(word).getMeaning1();
            String currentMeaning2 = DictionaryMgmt.search(word).getMeaning2();
            if (meaning1.equals(currentMeaning1)) {
                if (!meaning2.equals(currentMeaning2) && !meaning2.equals(currentMeaning1)) {
                    DictionaryMgmt.insert(word, meaning1, meaning2);
                    if (currentMeaning2.equals("")) {
                        statusText.setText("???? th??m ngh??a " + meaning2);
                    } else {
                        statusText.setText("???? s???a ngh??a " + currentMeaning2 + " th??nh " + meaning2);
                    }
                } else {
                    if (meaning2.equals(currentMeaning2)) {
                        statusText.setText("H??y th??m ngh??a th??? 2 kh??c c??i c??");
                    } else {
                        secondText2.setText(currentMeaning2);
                        statusText.setText("H??y th??m ngh??a th??? 2 kh??c ngh??a th??? 1");
                    }
                }
            } else {
                secondText1.setText(currentMeaning1);
                statusText.setText("Ch??? c?? th??? ch???nh s???a ngh??a th??? 2");
            }
        }
    }

    @FXML
    public void pronounce_E(ActionEvent event) {
        String textPronounce1 = listView.getSelectionModel().getSelectedItem();
        Audio.Text_Speech(textPronounce1);
    }

    @FXML
    public void saveFile(ActionEvent event) throws IOException {
        System.out.println("savefile");
        DictionaryMgmt.export(outputPath);
    }

    @FXML
    public void logOut() throws IOException {
        App.setScene("OptionSample");
        if (App.sourceFile == 1) {
            App.sourceFile = 2;
        } else {
            App.sourceFile = 1;
        }
    }
}