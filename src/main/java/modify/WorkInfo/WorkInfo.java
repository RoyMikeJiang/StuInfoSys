package modify.WorkInfo;

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
import utils.NormalData.WorkInfoData;
import utils.StaticResourcesConfig.StaticResourcesConfig;

public class WorkInfo implements Initializable {

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
    private TableView<WorkInfoData> tableView;

    @FXML
    private TableColumn<WorkInfoData, String> StudentId;

    @FXML
    private TableColumn<WorkInfoData, String> Name;

    @FXML
    private TableColumn<WorkInfoData, String> Class;

    @FXML
    private TableColumn<WorkInfoData, String> Work;

    @FXML
    private TableColumn<WorkInfoData, String> Dorm;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField StudentIdField;

    @FXML
    private TextField NameField;

    @FXML
    private TextField DormField;

    @FXML
    private Button SubmitButton;

    @FXML
    private Button DeleteButton;

    @FXML
    private ComboBox<String> ClassCombobox;

    @FXML
    private ComboBox<String> WorkCombobox;

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
            // Use the UpdateClassWorkInfo of the SQLConnection to insert into the MYSQL
            if (SQLConnection.UpdateWorkInfo(StudentIdField.getText(), NameField.getText(), ClassCombobox.getValue(), WorkCombobox.getValue(), DormField.getText())) {
                // Raise the Information Alter to Notice Success
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("运行结果");
                alert.setHeaderText(null);
                alert.setContentText("更新成功！");
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
            resultList = SQLConnection.SearchWorkInfo(StudentIdSearchField.getText(),NameSearchField.getText());
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
            ObservableList<WorkInfoData> info_list = FXCollections.observableArrayList();
            for(Map<String, Object> info_map : resultList){
                WorkInfoData info = new WorkInfoData(info_map);
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
    void Delete(ActionEvent event){
        // Clear the Warning in the view
        gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node)==6 && GridPane.getColumnIndex(node)==1);
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
            // Use the DeleteWorkInfo of the SQLConnection to update the basicinfo table in the MYSQL
            if (SQLConnection.DeleteWorkInfo(StudentIdField.getText())) {
                // Raise the Information Alter to Notice Success
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("运行结果");
                alert.setHeaderText(null);
                alert.setContentText("删除成功！");
                alert.showAndWait();
                // Clear All the Input Before
                StudentIdField.clear();
                NameField.clear();
                ClassCombobox.valueProperty().set(null);
                WorkCombobox.valueProperty().set(null);
                DormField.clear();
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
    public void initialize(URL location, ResourceBundle resources) {
        ClassCombobox = new ComboBox<String>(StaticResourcesConfig.CLASS_OPTIONS);
        gridPane.add(ClassCombobox,1,2);
        WorkCombobox = new ComboBox<String>(StaticResourcesConfig.CLASSWORK_OPTIONS);
        gridPane.add(WorkCombobox, 1 ,3);
        StudentId.setCellValueFactory(new PropertyValueFactory<WorkInfoData, String>("StudentId"));
        Name.setCellValueFactory(new PropertyValueFactory<WorkInfoData, String>("Name"));
        Class.setCellValueFactory(new PropertyValueFactory<WorkInfoData, String>("PClass"));
        Work.setCellValueFactory(new PropertyValueFactory<WorkInfoData, String>("Work"));
        Dorm.setCellValueFactory(new PropertyValueFactory<WorkInfoData, String>("Dorm"));
        // Register the double click event
        tableView.setRowFactory( tv -> {
            TableRow<WorkInfoData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    // Get the data of the double-clicked row
                    WorkInfoData rowData = row.getItem();
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

    public void SetEditText(WorkInfoData info){
        // Use the data in the WorkInfoData to fill the values in the gridPane
        StudentIdField.setText(info.getStudentId());
        NameField.setText(info.getName());
        DormField.setText(info.getDorm());
        ClassCombobox.setValue(info.getPClass());
        WorkCombobox.setValue(info.getWork());
    }
}