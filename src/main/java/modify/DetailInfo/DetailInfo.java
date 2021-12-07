package modify.DetailInfo;

import java.net.URL;
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
import utils.*;
import utils.NormalData.DetailInfoData;
import utils.StaticResourcesConfig.StaticResourcesConfig;

public class DetailInfo implements Initializable {

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
    private TableView<DetailInfoData> tableView;

    @FXML
    private TableColumn<DetailInfoData, String> StudentId;

    @FXML
    private TableColumn<DetailInfoData, String> Name;

    @FXML
    private TableColumn<DetailInfoData, String> Dorm;

    @FXML
    private TableColumn<DetailInfoData, String> Id;

    @FXML
    private TableColumn<DetailInfoData, String> BirthPlace;

    @FXML
    private TableColumn<DetailInfoData, String> PhoneNum;

    @FXML
    private TableColumn<DetailInfoData, String> PolStatus;

    @FXML
    private TableColumn<DetailInfoData, String> HouseholdTransfer;

    @FXML
    private TableColumn<DetailInfoData, String> SeniorSchool;

    @FXML
    private TableColumn<DetailInfoData, String> FatherName;

    @FXML
    private TableColumn<DetailInfoData, String> FatherPhone;

    @FXML
    private TableColumn<DetailInfoData, String> FatherWorkPlace;

    @FXML
    private TableColumn<DetailInfoData, String> FatherWorkPosition;

    @FXML
    private TableColumn<DetailInfoData, String> MotherName;

    @FXML
    private TableColumn<DetailInfoData, String> MotherPhone;

    @FXML
    private TableColumn<DetailInfoData, String> MotherWorkPlace;

    @FXML
    private TableColumn<DetailInfoData, String> MotherWorkPosition;

    @FXML
    private TableColumn<DetailInfoData, String> HomePlace;

    @FXML
    private TableColumn<DetailInfoData, String> PostCode;

    @FXML
    private TableColumn<DetailInfoData, String> PoorGrade;

    @FXML
    private TableColumn<DetailInfoData, String> DetailNote;

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
    private TextField DetailNoteField;

    @FXML
    private Button SubmitButton;
    
    @FXML
    private ComboBox<String> PolStatusCombobox;
    
    @FXML
    private ComboBox<String> PoorGradeCombobox;

    @FXML
    private RadioButton YesHouseTransferRadioButton;

    @FXML
    private RadioButton NoHouseTransferRadioButton;

    @FXML
    private Button DeleteButton;

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
            // Use the UpdateDetailInfo of the SQLConnection to insert into the MYSQL
            if (SQLConnection.UpdateDetailInfo(StudentIdField.getText(),NameField.getText(), DormField.getText(), IdField.getText(), BirthPlaceField.getText(),
                    PhoneNumField.getText(), PolStatusValue, HouseHoldTransferValue, SeniorSchoolField.getText(), FatherNameField.getText(), FatherPhoneField.getText(),
                    FatherWorkPlaceField.getText(), FatherWorkPositionField.getText(), MotherNameField.getText(), MotherPhoneField.getText(), MotherWorkPlaceField.getText(),
                    MotherWorkPositionField.getText(), HomePlaceField.getText(), PostCodeField.getText(), PoorGradeValue, DetailNoteField.getText())) {
                // Raise the Information Alter to Notice Success
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("运行结果");
                alert.setHeaderText(null);
                alert.setContentText("更新成功！");
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
            // Use the SearchDetailInfo of the SQLConnection to query info in the detailinfo table in the MYSQL
            resultList = SQLConnection.SearchDetailInfo(StudentIdSearchField.getText(),NameSearchField.getText());
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
            ObservableList<DetailInfoData> info_list = FXCollections.observableArrayList();
            for(Map<String, Object> info_map : resultList){
                DetailInfoData info = new DetailInfoData(info_map);
                info_list.add(info);
            }
            // Fill the ObservableList which containing the query result into the content tableview
            tableView.setItems(info_list);
        }catch(Exception e){
            // Raise the Error Alter to Notice the ERROR when Executing the SQL
            e.printStackTrace();
            ErrorExceptionAlter.RaiseAlter(e);
        }
    }

    @FXML
    void Delete() {
        // Raise the Confirmation alert to confirm delete
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除确认！");
        alert.setHeaderText(null);
        alert.setContentText("确认删除学号为："+StudentIdField.getText()+"的记录吗？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() != ButtonType.OK){
            return;
        }
        // Clear the Warning in the view
        gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node)==13 && GridPane.getColumnIndex(node)==3);
        // Try and get the exception thrown by the SQL
        try {
            // Use the DeleteDetailInfo of the SQLConnection to insert into the MYSQL
            if (SQLConnection.DeleteDetailInfo(StudentIdField.getText())) {
                // Raise the Information Alter to Notice Success
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("运行结果");
                alert.setHeaderText(null);
                alert.setContentText("删除成功！");
                alert.showAndWait();
                // Clear All the Input Before
                StudentIdField.clear();
                NameField.clear();
                DormField.clear();
                IdField.clear();
                BirthPlaceField.clear();
                PhoneNumField.clear();
                PolStatusCombobox.valueProperty().set(null);
                HouseTransferRadioButton.selectToggle(null);
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
        PolStatusCombobox = new ComboBox<String>(StaticResourcesConfig.POLICY_OPTIONS);
        gridPane.add(PolStatusCombobox,1,6);
        PoorGradeCombobox = new ComboBox<String>(StaticResourcesConfig.POORGRADE_OPTIONS);
        gridPane.add(PoorGradeCombobox,1,9);
        StudentId.setCellValueFactory(new PropertyValueFactory<DetailInfoData, String>("StudentId"));
        Name.setCellValueFactory(new PropertyValueFactory<DetailInfoData, String>("PName"));
        Dorm.setCellValueFactory(new PropertyValueFactory<DetailInfoData, String>("Dorm"));
        Id.setCellValueFactory(new PropertyValueFactory<DetailInfoData, String>("Id"));
        BirthPlace.setCellValueFactory(new PropertyValueFactory<DetailInfoData, String>("BirthPlace"));
        PhoneNum.setCellValueFactory(new PropertyValueFactory<DetailInfoData, String>("PhoneNum"));
        PolStatus.setCellValueFactory(new PropertyValueFactory<DetailInfoData, String>("PolStatus"));
        HouseholdTransfer.setCellValueFactory(new PropertyValueFactory<DetailInfoData, String>("HouseholdTransfer"));
        SeniorSchool.setCellValueFactory(new PropertyValueFactory<DetailInfoData, String>("SeniorSchool"));
        FatherName.setCellValueFactory(new PropertyValueFactory<DetailInfoData, String>("FatherName"));
        FatherPhone.setCellValueFactory(new PropertyValueFactory<DetailInfoData, String>("FatherPhone"));
        FatherWorkPlace.setCellValueFactory(new PropertyValueFactory<DetailInfoData, String>("FatherWorkPlace"));
        FatherWorkPosition.setCellValueFactory(new PropertyValueFactory<DetailInfoData, String>("FatherWorkPosition"));
        MotherName.setCellValueFactory(new PropertyValueFactory<DetailInfoData, String>("MotherName"));
        MotherPhone.setCellValueFactory(new PropertyValueFactory<DetailInfoData, String>("MotherPhone"));
        MotherWorkPlace.setCellValueFactory(new PropertyValueFactory<DetailInfoData, String>("MotherWorkPlace"));
        MotherWorkPosition.setCellValueFactory(new PropertyValueFactory<DetailInfoData, String>("MotherWorkPosition"));
        HomePlace.setCellValueFactory(new PropertyValueFactory<DetailInfoData, String>("HomePlace"));
        PostCode.setCellValueFactory(new PropertyValueFactory<DetailInfoData, String>("PostCode"));
        PoorGrade.setCellValueFactory(new PropertyValueFactory<DetailInfoData, String>("PoorGrade"));
        DetailNote.setCellValueFactory(new PropertyValueFactory<DetailInfoData, String>("DetailNote"));
        // Register the double click event
        tableView.setRowFactory( tv -> {
            TableRow<DetailInfoData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    // Get the data of the double-clicked row
                    DetailInfoData rowData = row.getItem();
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

    public void SetEditText(DetailInfoData info){
        StudentIdField.setText(info.getStudentId());
        NameField.setText(info.getPName());
        DormField.setText(info.getDorm());
        IdField.setText(info.getId());
        BirthPlaceField.setText(info.getBirthPlace());
        PhoneNumField.setText(info.getPhoneNum());
        SeniorSchoolField.setText(info.getSeniorSchool());
        FatherNameField.setText(info.getFatherName());
        FatherPhoneField.setText(info.getFatherPhone());
        FatherWorkPlaceField.setText(info.getFatherWorkPlace());
        FatherWorkPositionField.setText(info.getFatherWorkPosition());
        MotherNameField.setText(info.getMotherName());
        MotherPhoneField.setText(info.getMotherPhone());
        MotherWorkPlaceField.setText(info.getMotherWorkPlace());
        MotherWorkPositionField.setText(info.getMotherWorkPosition());
        HomePlaceField.setText(info.getHomePlace());
        PostCodeField.setText(info.getPostCode());
        DetailNoteField.setText(info.getDetailNote());
        PolStatusCombobox.setValue(info.getPolStatus());
        PoorGradeCombobox.setValue(info.getPoorGrade());
        if(Objects.equals(info.getHouseholdTransfer(), "是")) {
            HouseTransferRadioButton.selectToggle(YesHouseTransferRadioButton);
        }else if(Objects.equals(info.getHouseholdTransfer(), "否")){
            HouseTransferRadioButton.selectToggle(NoHouseTransferRadioButton);
        }else{
            HouseTransferRadioButton.selectToggle(null);
        }
    }
}