package insert.DetailInfo;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import javafx.scene.paint.Color;
import utils.StaticResourcesConfig.StaticResourcesConfig;
import utils.SQLConnection;
import utils.ErrorExceptionAlter;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DetailInfo implements Initializable {

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField StudentIdField;

    @FXML
    private TextField NameField;

    @FXML
    private TextField DormField;

    @FXML
    private TextField IdField;

    @FXML
    private TextField BirthPlaceField;

    @FXML
    private TextField PhoneNumField;

    @FXML
    private ComboBox<String> PolStatusCombobox;

    @FXML
    private ToggleGroup HouseTransferRadioButton;

    @FXML
    private TextField SeniorSchoolField;

    @FXML
    private TextField FatherNameField;

    @FXML
    private TextField FatherPhoneField;

    @FXML
    private TextField FatherWorkPlaceField;

    @FXML
    private TextField FatherWorkPositionField;

    @FXML
    private TextField MotherNameField;

    @FXML
    private TextField MotherPhoneField;

    @FXML
    private TextField MotherWorkPlaceField;

    @FXML
    private TextField MotherWorkPositionField;

    @FXML
    private TextField HomePlaceField;

    @FXML
    private TextField PostCodeField;

    @FXML
    private ComboBox<String> PoorGradeCombobox;

    @FXML
    private TextField DetailNoteField;

    @FXML
    private Button SubmitButton;

    @Override
    public void initialize(URL url, ResourceBundle resources){
        PolStatusCombobox = new ComboBox<String>(StaticResourcesConfig.POLICY_OPTIONS);
        gridPane.add(PolStatusCombobox,1,6);
        PoorGradeCombobox = new ComboBox<String>(StaticResourcesConfig.POORGRADE_OPTIONS);
        gridPane.add(PoorGradeCombobox,1,9);
    }

    @FXML
    void Submit(ActionEvent event) {
        // Get the selected button
        RadioButton selectedRadioButton = (RadioButton) HouseTransferRadioButton.getSelectedToggle();
        // Clear the Warning in the view
        gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node)==13 && GridPane.getColumnIndex(node)==3);
        // Verify the input has null or not (Only verify the StudentId, Name, Dorm)
        if(Objects.equals(StudentIdField.getText(), "") || Objects.equals(NameField.getText(), "") ||
                Objects.equals(DormField.getText(), "")){
            // Add Warning in the gridPane(3,13)
            Label WarnLabel = new Label();
            WarnLabel.setText("请补充完所有必要信息再提交！");
            WarnLabel.setTextFill(Color.RED);
            gridPane.add(WarnLabel,1,13);
            // Return Without go to the SQL
            return;
        }
        // Try and get the exception thrown by the SQL
        try {
            String HouseHoldTransferValue = "";
            if(selectedRadioButton!=null){
                HouseHoldTransferValue = selectedRadioButton.getText();
            }
            String PolStatusValue = "";
            if(PolStatusCombobox.getValue()!=null){
                PolStatusValue = PolStatusCombobox.getValue();
            }
            String PoorGradeValue = "";
            if(PoorGradeCombobox.getValue()!=null){
                PoorGradeValue = PoorGradeCombobox.getValue();
            }
            // Use the InsertDetailInfo of the SQLConnection to insert into the MYSQL
            if (SQLConnection.InsertDetailInfo(StudentIdField.getText(),NameField.getText(), DormField.getText(), IdField.getText(), BirthPlaceField.getText(),
                    PhoneNumField.getText(), PolStatusValue, HouseHoldTransferValue, SeniorSchoolField.getText(), FatherNameField.getText(), FatherPhoneField.getText(),
                    FatherWorkPlaceField.getText(), FatherWorkPositionField.getText(), MotherNameField.getText(), MotherPhoneField.getText(), MotherWorkPlaceField.getText(),
                    MotherWorkPositionField.getText(), HomePlaceField.getText(), PostCodeField.getText(), PoorGradeValue, DetailNoteField.getText())) {
                // Raise the Information Alter to Notice Success
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("运行结果");
                alert.setHeaderText(null);
                alert.setContentText("插入成功！");
                alert.showAndWait();
                // Clear All the Input Before
                StudentIdField.clear();
                NameField.clear();
                DormField.clear();
                IdField.clear();
                BirthPlaceField.clear();
                PhoneNumField.clear();
                PolStatusCombobox.valueProperty().set(null);
                if(selectedRadioButton!=null){
                    selectedRadioButton.setSelected(false);
                }
                SeniorSchoolField.clear();
                FatherNameField.clear();
                FatherPhoneField.clear();
                FatherWorkPlaceField.clear();
                FatherWorkPositionField.clear();
                MotherNameField.clear();
                MotherPhoneField.clear();
                MotherWorkPlaceField.clear();
                MotherWorkPositionField.clear();
                HomePlaceField.clear();
                PostCodeField.clear();
                PoorGradeCombobox.valueProperty().set(null);
                DetailNoteField.clear();
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
