package login;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import utils.StaticResourcesConfig.StaticResourcesConfig;
import viewalter.ViewAlter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import utils.SQLConnection;

public class LoginController implements Initializable {
    private static final Logger logger = Logger.getLogger(LoginController.class.getName());
    private ViewAlter viewAlter;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button login_button;

    @FXML
    private TextField login_username;

    @FXML
    private TextField login_password;

    public void loginButtonClick() {
        if(SQLConnection.Login(login_username.getText(),login_password.getText())) {
            try {
                StaticResourcesConfig.UserQQ = login_username.getText();
                StaticResourcesConfig.UserLevel = SQLConnection.QueryUserLevel(login_username.getText());
            }catch(Exception e){
                e.printStackTrace();
            }
            viewAlter.gotoMain();
        } else {
            // Raise the Warning Alter to Notice Fail
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("登录失败");
            alert.setHeaderText(null);
            alert.setContentText("账号密码错误！");
            alert.showAndWait();
            login_password.clear();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setApp(ViewAlter viewAlter) {
        this.viewAlter = viewAlter;
    }
}
