package viewalter;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.image.Image;
import utils.StaticResourcesConfig.StaticResourcesConfig;
import login.LoginController;
import mainview.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewAlter extends Application {

    private static final Logger logger = Logger.getLogger(ViewAlter.class.getName());
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("学生信息管理系统");
        gotoLogin();
        stage.show();
        stage.setResizable(false);
        stage.getIcons().add(new Image("file:src/main/resources/icon.png"));
    }

    /**
     * 跳转到登录界面
     */
    public void gotoLogin() {
        try {
            LoginController login = (LoginController) replaceSceneContent(StaticResourcesConfig.LOGIN_VIEW_PATH);
            login.setApp(this);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 跳转到主界面
     */
    public void gotoMain() {
        try {
            MainViewController main = (MainViewController) replaceSceneContent(StaticResourcesConfig.MAIN_VIEW_PATH);
            main.setApp(this);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 替换场景
     */
    private Initializable replaceSceneContent(String fxml) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        InputStream in = ViewAlter.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(ViewAlter.class.getResource(fxml));
        try {
            AnchorPane page = (AnchorPane) loader.load(in);
            Scene scene = new Scene(page, StaticResourcesConfig.STAGE_WIDTH, StaticResourcesConfig.STAGE_HEIGHT);
            stage.setScene(scene);
            stage.sizeToScene();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "页面加载异常！");
        } finally {
            in.close();
        }
        return (Initializable) loader.getController();
    }



    public static void main(String[] args) {
        launch(args);
    }

}
