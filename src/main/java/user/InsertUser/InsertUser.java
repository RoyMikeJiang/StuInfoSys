package user.InsertUser;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import utils.ErrorExceptionAlter;
import utils.SQLConnection;
import utils.StaticResourcesConfig.StaticResourcesConfig;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class InsertUser implements Initializable {

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField QQField;

    @FXML
    private TextField NoteField;

    @FXML
    private Button SubmitButton;

    @FXML
    private ComboBox<String> LevelComboBox;

    @FXML
    private Label PasswordLabel;

    @FXML
    private TextField PasswordField;

    @FXML
    void Submit(ActionEvent event) {
        // Clear the Warning in the view
        gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node)==5 && GridPane.getColumnIndex(node)==1);
        // Verify the input has null or not (Except the NoteField)
        String level_string;
        String password_string;
        level_string = LevelComboBox.getValue().toString();
        if(StaticResourcesConfig.USERLEVEL_OPTIONS.indexOf(level_string)>1){
            password_string = PasswordField.getText();
        }else{
            password_string = null;
        }
        if(Objects.equals(QQField.getText(), "") || Objects.equals(NoteField.getText(), "") || LevelComboBox.getValue()==null || password_string == ""){
            // Add Warning in the gridPane(1,5)
            Label WarnLabel = new Label();
            WarnLabel.setText("请补充完所有必要信息再提交！");
            WarnLabel.setTextFill(Color.RED);
            gridPane.add(WarnLabel,1,5);
            // Return Without go to the SQL
            return;
        }
        // Try and Get the Exception Thrown by the SQL

        try {

            // Use the InsertUser of the SQLConnection to insert into the MYSQL
            if (SQLConnection.InsertUser(QQField.getText(), level_string, NoteField.getText(), password_string)) {
                // Raise the Information Alter to Notice Success
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("运行结果");
                alert.setHeaderText(null);
                alert.setContentText("插入成功！");
                alert.showAndWait();
                // Clear All the Input Before
                QQField.clear();
                NoteField.clear();
                LevelComboBox.valueProperty().set(null);
                PasswordField.clear();
            } else {
                // Raise the Warning Alter to Notice Fail
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("运行结果");
                alert.setHeaderText(null);
                alert.setContentText("插入失败，请检查信息是否重复！");
                alert.showAndWait();
            }
        }catch (Exception e){
            // Raise the Error Alter to Notice the ERROR when Executing the SQL
            ErrorExceptionAlter.RaiseAlter(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        ObservableList<String> ComboBoxOptions = FXCollections.observableArrayList();
        for(int i = 0; i<StaticResourcesConfig.UserLevel-1;i++) {
            ComboBoxOptions.add(StaticResourcesConfig.USERLEVEL_OPTIONS.get(i));
        }
        LevelComboBox = new ComboBox<String>(ComboBoxOptions);
        LevelComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(StaticResourcesConfig.USERLEVEL_OPTIONS.indexOf(newValue.toString())>1){
                    PasswordField.setVisible(true);
                    PasswordLabel.setVisible(true);
                }else{
                    PasswordField.setVisible(false);
                    PasswordField.clear();
                    PasswordLabel.setVisible(false);
                }
            }
        });
        gridPane.add(LevelComboBox, 1, 1);
    }

}

