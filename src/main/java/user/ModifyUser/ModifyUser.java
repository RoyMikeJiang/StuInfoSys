package user.ModifyUser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import utils.ErrorExceptionAlter;
import utils.NormalData.UserInfoData;
import utils.StaticResourcesConfig.StaticResourcesConfig;

import java.net.URL;
import java.util.*;

import utils.SQLConnection;
import utils.NormalData.UserInfoData;

public class ModifyUser implements Initializable {

    @FXML
    private TableView<UserInfoData> tableView;

    @FXML
    private TableColumn<UserInfoData, String> QQ;

    @FXML
    private TableColumn<UserInfoData, String> Level;

    @FXML
    private TableColumn<UserInfoData, String> Note;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label QQLabel;

    @FXML
    private TextField NoteField;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button ModifyButton;

    @FXML
    private ComboBox<String> LevelComboBox;

    @FXML
    void Delete(ActionEvent event) {
        // Clear the Warning in the view
        gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node)==6 && GridPane.getColumnIndex(node)==1);
        // Raise the Confirmation alert to confirm delete
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除确认！");
        alert.setHeaderText(null);
        alert.setContentText("确认删除QQ为："+QQLabel.getText()+"的用户吗？");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() != ButtonType.OK){
            return;
        }
        // Try and Get the Exception Thrown by the SQL
        try {
            // Use the DeleteWorkInfo of the SQLConnection to update the basicinfo table in the MYSQL
            if (SQLConnection.DeleteUserInfo(QQLabel.getText())) {
                // Raise the Information Alter to Notice Success
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("运行结果");
                alert.setHeaderText(null);
                alert.setContentText("删除成功！");
                alert.showAndWait();
                // Clear All the Input Before
                QQLabel.setText("");
                LevelComboBox.valueProperty().set(null);
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
        search();
        ModifyButton.setDisable(true);
        DeleteButton.setDisable(true);
    }

    @FXML
    void Modify(ActionEvent event) {
        // Clear the Warning in the view
        gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node)==5 && GridPane.getColumnIndex(node)==1);
        // Verify the input has null or not (Except the NoteField)
        String level_string;
        level_string = LevelComboBox.getValue().toString();
        if(Objects.equals(NoteField.getText(), "") || LevelComboBox.getValue()==null){
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
            if (SQLConnection.UpdateUserInfo(QQLabel.getText(), level_string, NoteField.getText())) {
                // Raise the Information Alter to Notice Success
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("运行结果");
                alert.setHeaderText(null);
                alert.setContentText("修改成功！");
                alert.showAndWait();
                // Clear All the Input Before
                QQLabel.setText("");
                NoteField.clear();
                LevelComboBox.valueProperty().set(null);
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
        ModifyButton.setDisable(true);
        DeleteButton.setDisable(true);

    }

    @FXML
    public void initialize(URL url, ResourceBundle resources) {
        ObservableList<String> ComboBoxOptions = FXCollections.observableArrayList();
        for(int i = 0; i< StaticResourcesConfig.UserLevel-1; i++) {
            ComboBoxOptions.add(StaticResourcesConfig.USERLEVEL_OPTIONS.get(i));
        }
        LevelComboBox = new ComboBox<String>(ComboBoxOptions);
        gridPane.add(LevelComboBox, 1, 1);
        QQ.setCellValueFactory(new PropertyValueFactory<UserInfoData, String>("QQ"));
        Level.setCellValueFactory(new PropertyValueFactory<UserInfoData, String>("Level"));
        Note.setCellValueFactory(new PropertyValueFactory<UserInfoData,String>("Note"));
        // Register the double click event
        tableView.setRowFactory( tv -> {
            TableRow<UserInfoData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    // Get the data of the double-clicked row
                    UserInfoData rowData = row.getItem();
                    // Use the SetEditText function to set the value in the gridPane
                    SetEditText(rowData);
                    // Set the SubmitButton clickable which is disabled when rendering the page
                    ModifyButton.setDisable(false);
                    DeleteButton.setDisable(false);
                }
            });
            return row;
        });
        search();
    }

    public void search(){
        // Try and catch fail
        try{
            // Initialize the resultList
            List<Map<String, Object>> resultList = null;
            // Use the SearchUserInfo of the SQLConnection to query info in the userid table in the MYSQL
            resultList = SQLConnection.SearchUserInfo(StaticResourcesConfig.UserLevel);
            // If is null or size == 0, raise alter to warn no result in this query
            if(resultList==null || resultList.size()==0){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("查询无结果");
                alert.setHeaderText(null);
                alert.setContentText("系统中没有用户信息！");
                alert.showAndWait();
                return;
            }
            // Construct the ObservableList to reconnect the data in the resultList
            ObservableList<UserInfoData> info_list = FXCollections.observableArrayList();
            for(Map<String, Object> info_map : resultList){
                UserInfoData info = new UserInfoData(info_map);
                info_list.add(info);
            }
            // Fill the ObservableList which containing the query result into the content tableview
            tableView.setItems(info_list);
        }catch(Exception e){
            // Raise the Error Alter to Notice the ERROR when Executing the SQL
            ErrorExceptionAlter.RaiseAlter(e);
        }
    }

    public void SetEditText(UserInfoData data){
        QQLabel.setText(data.getQQ());
        LevelComboBox.setValue(data.getLevel());
        NoteField.setText(data.getNote());
    }
}

