package dataimport.DetailInfo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import utils.ExcelReader;
import utils.StaticResourcesConfig.StaticResourcesConfig;

public class DetailInfo implements Initializable {

    @FXML
    private Button SubmitButton;

    @FXML
    private Button BrowseButton;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label FilePathLabel;

    @FXML
    private TextArea ResultTextArea;

    @FXML
    void OpenRefDir(){
        Desktop desktop = Desktop.getDesktop();
        File dirToOpen = null;
        try {
            dirToOpen = new File(StaticResourcesConfig.getReferenceFileDirectory());
            desktop.open(dirToOpen);
        } catch (IllegalArgumentException | IOException iae) {
            System.out.println("File Not Found");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resources){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel Files", "*.xls","*.xlsx")
        );
        BrowseButton.setOnAction(e->{
            Node node = (Node) e.getSource();
            File selectedFile = fileChooser.showOpenDialog(node.getScene().getWindow());
            if(selectedFile!=null) {
                FilePathLabel.setText(selectedFile.getPath());
                SubmitButton.setDisable(false);
            }
        });
    }

    @FXML
    void Submit(){
        String file_path = FilePathLabel.getText();
        ResultTextArea.setText(ExcelReader.ImportDetailInfo(file_path));
    }
}

