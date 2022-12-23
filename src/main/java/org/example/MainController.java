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
            firstLabel.setText("Tiếng Anh");
            secondLabel.setText("Tiếng Việt");

        } else {
            inputPath = "/org/example/Vtest" + App.sourceFile + ".txt";
            outputPath = "D:\\java\\Dictionary\\Dictionary_v3.2.2\\target\\classes" +
                    "\\org\\example\\Vtest"  + (3 - App.sourceFile) + ".txt";
            firstLabel.setText("Tiếng Việt");
            secondLabel.setText("Tiếng Anh");
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
            statusText.setText("Định lừa ai??? Vui lòng thêm từ hẳn hoi");
        } else {
            if (!DictionaryMgmt.isWord(word)) {
                DictionaryMgmt.insert(word, meaning1, meaning2);
                statusText.setText("Đã thêm từ " + word + " vào từ điển");
            } else {
                statusText.setText("Từ " + word + " đã tồn tại trong từ điển");
            }
        }
        reloadList();
    }

    @FXML
    public void deleteWord(ActionEvent actionEvent) throws IllegalAccessException {
        String word = listView.getSelectionModel().getSelectedItem().trim();
        int index = listView.getSelectionModel().getSelectedIndex();
        confirmAlert.setTitle("Wait a minute");
        confirmAlert.setHeaderText("Bạn có chắc muốn xóa từ " + word + " khỏi từ điển?");
        confirmAlert.setContentText("");
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.get() == ButtonType.OK) {
            listView.getItems().remove(index);
            DictionaryMgmt.delete(word);
            statusText.setText("Đã xóa từ '" + word + "' khỏi từ điển");
            firstText.clear();
            secondText1.clear();
            secondText2.clear();
        } else {
            statusText.setText("May cho mày đấy " + word + " ༼ つ ◕_◕ ༽つ");
        }
        reloadList();
    }

    @FXML
    public void updateWord(ActionEvent event) throws IllegalAccessException {

        String word = listView.getSelectionModel().getSelectedItem();
        if (word == null) {
            statusText.setText("Hãy chọn từ để sửa");
        } else {
            String meaning1 = secondText1.getText();
            String meaning2 = secondText2.getText().trim();
            String currentMeaning1 = DictionaryMgmt.search(word).getMeaning1();
            String currentMeaning2 = DictionaryMgmt.search(word).getMeaning2();
            if (meaning1.equals(currentMeaning1)) {
                if (!meaning2.equals(currentMeaning2) && !meaning2.equals(currentMeaning1)) {
                    DictionaryMgmt.insert(word, meaning1, meaning2);
                    if (currentMeaning2.equals("")) {
                        statusText.setText("Đã thêm nghĩa " + meaning2);
                    } else {
                        statusText.setText("Đã sửa nghĩa " + currentMeaning2 + " thành " + meaning2);
                    }
                } else {
                    if (meaning2.equals(currentMeaning2)) {
                        statusText.setText("Hãy thêm nghĩa thứ 2 khác cái cũ");
                    } else {
                        secondText2.setText(currentMeaning2);
                        statusText.setText("Hãy thêm nghĩa thứ 2 khác nghĩa thứ 1");
                    }
                }
            } else {
                secondText1.setText(currentMeaning1);
                statusText.setText("Chỉ có thể chỉnh sửa nghĩa thứ 2");
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