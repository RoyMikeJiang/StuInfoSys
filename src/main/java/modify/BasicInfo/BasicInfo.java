package modify.BasicInfo;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import utils.NormalData.BasicInfoData;
import utils.ErrorExceptionAlter;
import utils.StaticResourcesConfig.StaticResourcesConfig;
import utils.SQLConnection;

public class BasicInfo implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField StudentIdSearchField;

    @FXML
    private TextField NameSearchField;

    @FXML
    private Button SearchButton;

    @FXML
    private TableView<BasicInfoData> tableView;

    @FXML
    private TableColumn<BasicInfoData, String> StudentId;

    @FXML
    private TableColumn<BasicInfoData, String> Name;

    @FXML
    private TableColumn<BasicInfoData, String> Gender;

    @FXML
    private TableColumn<BasicInfoData, String> Nationality;

    @FXML
    private TableColumn<BasicInfoData, String> BirthDate;

    @FXML
    private TableColumn<BasicInfoData, String> Class;

    @FXML
    private TableColumn<BasicInfoData, String> Note;

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
    private Button SubmitButton;

    @FXML
    private ComboBox<String> ClassCombobox;

    @FXML
    private RadioButton MaleRadioButton;

    @FXML
    private RadioButton FemaleRadioButton;

    @FXML
    private Button DeleteButton;

    @FXML
    void Submit(ActionEvent event) {
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
            // Use the UpdateBasicInfo of the SQLConnection to update the basicinfo table in the MYSQL
            if (SQLConnection.UpdateBasicInfo(StudentIdField.getText(), NameField.getText(), selectedRadioButton.getText(),
                    NationalityField.getText(), BirthDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日")),
                    ClassCombobox.getValue(), NoteField.getText())) {
                // Raise the Information Alter to Notice Success
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("运行结果");
                alert.setHeaderText(null);
                alert.setContentText("更新成功！");
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
                alert.setContentText("更新失败，检查是否有格式错误！");
                alert.showAndWait();
            }
        }catch (Exception e){
            // Raise the Error Alter to Notice the ERROR when Executing the SQL
            ErrorExceptionAlter.RaiseAlter(e);
        }
        search();
        SubmitButton.setDisable(true);
        DeleteButton.setDisable(true);
    }

    @FXML
    void search() {
        // Verify if both of the search TextField are null
        if(Objects.equals(StudentIdSearchField.getText(), "") && Objects.equals(NameSearchField.getText(), "")){
            // Raise the Warning Alter to Notice Fail
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("查询失败");
            alert.setHeaderText(null);
            alert.setContentText("请至少输入一个查询参数！");
            alert.showAndWait();
            return;
        }
        // Try and catch fail
        try{
            // Initialize the resultList
            List<Map<String, Object>> resultList = null;
            // Use the SearchBasicInfo of the SQLConnection to query info in the basicinfo table in the MYSQL
            resultList = SQLConnection.SearchBasicInfo(StudentIdSearchField.getText(),NameSearchField.getText());
            // If is null or size == 0, raise alter to warn no result in this query
            if(resultList==null || resultList.size()==0){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("查询无结果");
                alert.setHeaderText(null);
                alert.setContentText("没有对应的结果，请重新核查后输入查询参数！");
                alert.showAndWait();
                return;
            }
            // Construct the ObservableList to reconnect the data in the resultList
            ObservableList<BasicInfoData> info_list = FXCollections.observableArrayList();
            for(Map<String, Object> info_map : resultList){
                BasicInfoData info = new BasicInfoData(info_map);
                info_list.add(info);
            }
            // Fill the ObservableList which containing the query result into the content tableview
            tableView.setItems(info_list);
        }catch(Exception e){
            // Raise the Error Alter to Notice the ERROR when Executing the SQL
            ErrorExceptionAlter.RaiseAlter(e);
        }
    }

    @FXML
    void Delete(){
        // Clear the Warning in the view
        gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node)==8 && GridPane.getColumnIndex(node)==1);
        // Raise the Confirmation alert to confirm delete
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除确认！");
        alert.setHeaderText(null);
        alert.setContentText("确认删除学号为："+StudentIdField.getText()+"的记录吗？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() != ButtonType.OK){
            return;
        }
        // Try and Get the Exception Thrown by the SQL
        try {
            // Use the UpdateBasicInfo of the SQLConnection to update the basicinfo table in the MYSQL
            if (SQLConnection.DeleteBasicInfo(StudentIdField.getText())) {
                // Raise the Information Alter to Notice Success
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("运行结果");
                alert.setHeaderText(null);
                alert.setContentText("删除成功！");
                alert.showAndWait();
                // Clear All the Input Before
                StudentIdField.clear();
                NameField.clear();
                GenderRadioButton.selectToggle(null);
                NationalityField.clear();
                BirthDatePicker.getEditor().clear();
                ClassCombobox.valueProperty().set(null);
                NoteField.clear();
            } else {
                // Raise the Warning Alter to Notice Fail
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("运行结果");
                alert.setHeaderText(null);
                alert.setContentText("删除失败！");
                alert.showAndWait();
            }
        }catch (Exception e){
            // Raise the Error Alter to Notice the ERROR when Executing the SQL
            ErrorExceptionAlter.RaiseAlter(e);
        }
        SubmitButton.setDisable(true);
        DeleteButton.setDisable(true);
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources){
        ClassCombobox = new ComboBox<String>(StaticResourcesConfig.CLASS_OPTIONS);
        gridPane.add(ClassCombobox,3,2);
        // Set the tableview data connected to the BasicInfoData Class
        StudentId.setCellValueFactory(new PropertyValueFactory<BasicInfoData, String>("StudentId"));
        Name.setCellValueFactory(new PropertyValueFactory<BasicInfoData, String>("Name"));
        Gender.setCellValueFactory(new PropertyValueFactory<BasicInfoData, String>("Gender"));
        Nationality.setCellValueFactory(new PropertyValueFactory<BasicInfoData, String>("Nationality"));
        BirthDate.setCellValueFactory(new PropertyValueFactory<BasicInfoData, String>("BirthDate"));
        Class.setCellValueFactory(new PropertyValueFactory<BasicInfoData, String>("PClass"));
        Note.setCellValueFactory(new PropertyValueFactory<BasicInfoData, String>("Note"));
        // Register the double click event
        tableView.setRowFactory( tv -> {
            TableRow<BasicInfoData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    // Get the data of the double-clicked row
                    BasicInfoData rowData = row.getItem();
                    // Use the SetEditText function to set the value in the gridPane
                    SetEditText(rowData);
                    // Set the SubmitButton clickable which is disabled when rendering the page
                    SubmitButton.setDisable(false);
                    DeleteButton.setDisable(false);
                }
            });
            return row;
        });
    }

    public void SetEditText(BasicInfoData info){
        // Use the data in the BasicInfoData to fill the values in the gridPane
        StudentIdField.setText(info.getStudentId());
        NameField.setText(info.getName());
        if(Objects.equals(info.getGender(), "男")) {
            GenderRadioButton.selectToggle(MaleRadioButton);
        }else if(Objects.equals(info.getGender(), "女")){
            GenderRadioButton.selectToggle(FemaleRadioButton);
        }else{
            GenderRadioButton.selectToggle(null);
        }
        NationalityField.setText(info.getNationality());
        // Format the String Date to Date that can be used to DatePicker
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        LocalDate localDate = LocalDate.parse(info.getBirthDate(), formatter);
        BirthDatePicker.setValue(localDate);
        ClassCombobox.setValue(info.getPClass());
        NoteField.setText(info.getNote());
    }

}