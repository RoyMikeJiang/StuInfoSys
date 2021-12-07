package mainview;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import utils.StaticResourcesConfig.ItemStruct;
import utils.StaticResourcesConfig.StaticResourcesConfig;
import utils.StaticResourcesConfig.TreeViewListItem;
import viewalter.ViewAlter;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

public class MainViewController implements Initializable {
    private ViewAlter viewAlter;

    @FXML
    private TreeView<String> main_treeview;

    @FXML
    private ScrollPane main_scroll_pane;

    @FXML
    private AnchorPane main_pane_under_scroll;

    public void setApp(ViewAlter viewAlter) {
        this.viewAlter = viewAlter;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTreeView();
    }

    /**
     * 设置TreeView
     */
    @SuppressWarnings("unchecked")
    public void setTreeView() {
        TreeItem<String> root = new TreeItem<String>(StaticResourcesConfig.MAIN_TREE_HEADER);
        root.setExpanded(true);
        for(TreeViewListItem func_list : StaticResourcesConfig.MAIN_TREE_ITEM_LIST){
            TreeItem<String> node = new TreeItem<String>(func_list.list_name);
            for(ItemStruct item : func_list.item_list){
                node.getChildren().add(new TreeItem<String>(item.func_name));
            }
            node.setExpanded(true);
            root.getChildren().add(node);
        }
        main_treeview.setRoot(root);
    }

    /**
     * TreeView 点击事件
     * @throws IOException
     */
    public void mainTreeViewClick() throws IOException {
        // 获取鼠标当前点击的Item
        TreeItem<String> selectedItem = main_treeview.getSelectionModel().getSelectedItem();

        String pagePath = "";
        for(TreeViewListItem func_list : StaticResourcesConfig.MAIN_TREE_ITEM_LIST) {
            for (ItemStruct item : func_list.item_list) {
                if(selectedItem.getValue().equals(item.func_name)){
                    pagePath = item.file_path;
                }
            }
        }
        if(pagePath.equals("")){
            return;
        }
        skipView(pagePath);
    }

    /**
     * 改变右侧scroll的界面
     * @param pagePath
     * @throws IOException
     */
    private void skipView(String pagePath) throws IOException {
        ObservableList<Node> scrolChildren = main_pane_under_scroll.getChildren();
        scrolChildren.clear();
        scrolChildren.add(FXMLLoader.load(getClass().getResource(pagePath)));
    }

}