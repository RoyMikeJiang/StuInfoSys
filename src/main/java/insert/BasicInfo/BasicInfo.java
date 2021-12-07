package insert.BasicInfo;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import utils.ErrorExceptionAlter;
import utils.StaticResourcesConfig.StaticResourcesConfig;
import utils.SQLConnection;
import javafx.fxml.Initializable;


public class BasicInfo implements Initializable{
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
    private ToggleGroup GenderRadioButton;

    @FXML
    private TextField NationalityField;

    @FXML
    private DatePicker BirthDatePicker;

    @FXML
    private TextField NoteField;

    @FXML
    private ComboBox<String> ClassCombobox;

    @Override
    public void initialize(URL url, ResourceBundle resources){
        ClassCombobox = new ComboBox<String>(StaticResourcesConfig.CLASS_OPTIONS);
        gridPane.add(ClassCombobox,1,5);
    }

    @FXML
    void Submit(ActionEvent event){
        // Get the Selected Button
        RadioButton selectedRadioButton = (RadioButton) GenderRadioButton.getSelectedToggle();
        // Clear the Warning in the view
        gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node)==8 && GridPane.getColumnIndex(node)==1);
        // Verify the input has null or not (Except the NoteField)
        if(Objects.equals(StudentIdField.getText(), "") || Objects.equals(NameField.getText(), "") || selectedRadioButton == null
                || Objects.equals(NationalityField.getText(), "") || BirthDatePicker.getValue()==null || Objects.equals(ClassCombobox.getValue(), "")){
            // Add Warning in the gridPane(1,8)
            Label WarnLabel = new Label();
            WarnLabel.setText("请补充完所有必要信息再提交！");
            WarnLabel.setTextFill(Color.RED);
            gridPane.add(WarnLabel,1,8);
            // Return Without go to the SQL
            return;
        }
        // Try and Get the Exception Thrown by the SQL
        try {
            // Use the InsertBasicInfo of the SQLConnection to insert into the MYSQL
            if (SQLConnection.InsertBasicInfo(StudentIdField.getText(), NameField.getText(), selectedRadioButton.getText(),
                    NationalityField.getText(), BirthDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日")),
                    ClassCombobox.getValue(), NoteField.getText())) {
                // Raise the Information Alter to Notice Success
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("运行结果");
                alert.setHeaderText(null);
                alert.setContentText("插入成功！");
                alert.showAndWait();
                // Clear All the Input Before
                StudentIdField.clear();
                NameField.clear();
                selectedRadioButton.setSelected(false);
                NationalityField.clear();
                BirthDatePicker.getEditor().clear();
                ClassCombobox.valueProperty().set(null);
                NoteField.clear();
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
