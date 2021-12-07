package insert.WorkInfo;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import utils.ErrorExceptionAlter;
import utils.SQLConnection;
import utils.StaticResourcesConfig.StaticResourcesConfig;

public class WorkInfo implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField StudentIdField;

    @FXML
    private TextField NameField;

    @FXML
    private ComboBox<String> ClassCombobox;

    @FXML
    private ComboBox<String> WorkCombobox;

    @FXML
    private TextField DormField;

    @Override
    public void initialize(URL url, ResourceBundle resources){
        ClassCombobox = new ComboBox<String>(StaticResourcesConfig.CLASS_OPTIONS);
        gridPane.add(ClassCombobox,1,2);
        WorkCombobox = new ComboBox<String>(StaticResourcesConfig.CLASSWORK_OPTIONS);
        gridPane.add(WorkCombobox, 1 ,3);
    }

    @FXML
    void Submit(ActionEvent event) {
        // Clear the Warning in the view
        gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node)==6 && GridPane.getColumnIndex(node)==1);
        // Verify the input has null or not (Except the NoteField)
        if(Objects.equals(StudentIdField.getText(), "") || Objects.equals(NameField.getText(), "") || ClassCombobox.getValue()==null || WorkCombobox.getValue()==null){
            // Add Warning in the gridPane(1,6)
            Label WarnLabel = new Label();
            WarnLabel.setText("请补充完所有必要信息再提交！");
            WarnLabel.setTextFill(Color.RED);
            gridPane.add(WarnLabel,1,6);
            // Return Without go to the SQL
            return;
        }
        // Try and Get the Exception Thrown by the SQL
        try {
            // Use the InsertClassWorkInfo of the SQLConnection to insert into the MYSQL
            if (SQLConnection.InsertClassWorkInfo(StudentIdField.getText(), NameField.getText(), ClassCombobox.getValue(), WorkCombobox.getValue(), DormField.getText())) {
                // Raise the Information Alter to Notice Success
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("运行结果");
                alert.setHeaderText(null);
                alert.setContentText("插入成功！");
                alert.showAndWait();
                // Clear All the Input Before
                StudentIdField.clear();
                NameField.clear();
                ClassCombobox.valueProperty().set(null);
                WorkCombobox.valueProperty().set(null);
                DormField.clear();
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
}