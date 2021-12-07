package myinfo.UserCenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import utils.ErrorExceptionAlter;
import utils.NormalData.UserInfoData;
import utils.SQLConnection;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import utils.StaticResourcesConfig.StaticResourcesConfig;

public class UserCenter implements Initializable {

    @FXML
    private GridPane gridPane;

    @FXML
    private Label QQLabel;

    @FXML
    private Label LevelLabel;

    @FXML
    private Label NoteLabel;

    @FXML
    private PasswordField PasswordChange;

    @FXML
    private PasswordField PasswordChangeConfirm;

    @FXML
    private Button ChangeButton;

    @FXML
    void Change(ActionEvent event) {
        // Clear the Warning in the view
        gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node)==15 && GridPane.getColumnIndex(node)==1);
        // Verify the input has null or not (Except the NoteField)
        if(Objects.equals(PasswordChange.getText(), "") ||  !Objects.equals(PasswordChange.getText(), PasswordChangeConfirm.getText())) {
            // Add Warning in the gridPane(1,15)
            Label WarnLabel = new Label();
            WarnLabel.setText("请补充完所有必要信息并核对两次密码是否相等！");
            WarnLabel.setTextFill(Color.RED);
            gridPane.add(WarnLabel,1,15);
            // Return Without go to the SQL
            return;
        }
        // Try and Get the Exception Thrown by the SQL
        try {
            // Use the InsertClassWorkInfo of the SQLConnection to insert into the MYSQL
            if (SQLConnection.ChangeUserPassword(QQLabel.getText(), PasswordChange.getText())) {
                // Raise the Information Alter to Notice Success
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("运行结果");
                alert.setHeaderText(null);
                alert.setContentText("修改成功！");
                alert.showAndWait();
                // Clear All the Input Before
                PasswordChange.clear();
                PasswordChangeConfirm.clear();
            } else {
                // Raise the Warning Alter to Notice Fail
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("运行结果");
                alert.setHeaderText(null);
                alert.setContentText("修改失败，请检查密码是否包含不正当字符！");
                alert.showAndWait();
            }
        }catch (Exception e){
            // Raise the Error Alter to Notice the ERROR when Executing the SQL
            ErrorExceptionAlter.RaiseAlter(e);
        }
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        // Try and catch fail
        try{
            // Initialize the resultList
            List<Map<String, Object>> resultList = null;
            // Use the SearchUserInfo of the SQLConnection to query info in the userid table in the MYSQL
            resultList = SQLConnection.GerUserInfo(StaticResourcesConfig.UserQQ);
            FillData(new UserInfoData(resultList.get(0)));
        }catch(Exception e){
            // Raise the Error Alter to Notice the ERROR when Executing the SQL
            ErrorExceptionAlter.RaiseAlter(e);
        }
    }

    private void FillData(UserInfoData data){
        QQLabel.setText(data.getQQ());
        LevelLabel.setText(data.getLevel());
        NoteLabel.setText(data.getNote());
    }
}
