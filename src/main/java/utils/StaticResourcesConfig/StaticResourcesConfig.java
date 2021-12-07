package utils.StaticResourcesConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import utils.SQLConnection;

public final class StaticResourcesConfig {
    public final static int STAGE_WIDTH = 1000;
    public final static int STAGE_HEIGHT = 600;

    public final static String LOGIN_VIEW_PATH = "../login/login.fxml";
    public final static String MAIN_VIEW_PATH = "../mainview/MainView.fxml";

    public static final String MYSQL_IP = "localhost";
    public static final String MYSQL_PORT = "3306";
    public static final String MYSQL_USER_NAME = "root";
    public static final String MYSQL_USER_PASSWD = "PASSWORD";
    public static final String MYSQL_DATABASE_NAME = "scu_cs_stu_info";

    public final static String MAIN_TREE_HEADER = "管理系统功能";
    public final static List<TreeViewListItem> MAIN_TREE_ITEM_LIST = Arrays.<TreeViewListItem>asList(
        new TreeViewListItem("数据插入", Arrays.<ItemStruct>asList(
                new ItemStruct("基本信息插入","../insert/BasicInfo/BasicInfo.fxml"),
                new ItemStruct("具体信息插入","../insert/DetailInfo/DetailInfo.fxml"),
                new ItemStruct("学生工作信息插入", "../insert/WorkInfo/WorkInfo.fxml")
            )
        ),
        new TreeViewListItem("数据修改", Arrays.<ItemStruct>asList(
                new ItemStruct("基本信息修改","../modify/BasicInfo/BasicInfo.fxml"),
                new ItemStruct("具体信息修改","../modify/DetailInfo/DetailInfo.fxml"),
                new ItemStruct("学生工作信息修改", "../modify/WorkInfo/WorkInfo.fxml")
            )
        ),
        new TreeViewListItem("数据导入", Arrays.<ItemStruct>asList(
                new ItemStruct("基本信息导入","../dataimport/BasicInfo/BasicInfo.fxml"),
                new ItemStruct("具体信息导入","../dataimport/DetailInfo/DetailInfo.fxml"),
                new ItemStruct("学生工作信息导入","../dataimport/WorkInfo/WorkInfo.fxml")
        )),
        new TreeViewListItem("用户管理", Arrays.<ItemStruct>asList(
                new ItemStruct("增加用户","../user/InsertUser/InsertUser.fxml"),
                new ItemStruct("修改用户","../user/ModifyUser/ModifyUser.fxml")
        )),
        new TreeViewListItem("个人中心", Arrays.<ItemStruct>asList(
                new ItemStruct("个人信息","../myinfo/UserCenter/UserCenter.fxml")
                // , new ItemStruct("操作日志查看","../myinfo/UserCenter/ProcessLog.fxml")
        ))
    );

    public static final ObservableList<String> CLASS_OPTIONS = FXCollections.observableArrayList(
            "行政一班","行政二班","行政三班","行政四班","行政五班","行政六班","拔尖班","计金班","物联网班","人工智能班"
    );

    public static final ObservableList<String> POLICY_OPTIONS = FXCollections.observableArrayList(
            "群众","共青团员","党员"
    );

    public static final ObservableList<String> POORGRADE_OPTIONS = FXCollections.observableArrayList(
            "不困难","一般困难","困难","特别困难"
    );

    public static final ObservableList<String> CLASSWORK_OPTIONS = FXCollections.observableArrayList(
            "班长","团支书","学习委员","生活委员","文体委员","寝室长"
    );

    public static final List<String> USERLEVEL_OPTIONS = Arrays.<String>asList(
            "普通查询用户","高级查询用户","管理员用户","超级管理员用户","系统工程师"
    );

    public static final String ProgrammingReferenceRelativePath = File.separator + "src" + File.separator + "main" + File.separator + "resources";

    public static int UserLevel = 0;
    public static String UserQQ = "";

    public static String getReferenceFileDirectory(){
        String base_path = System.getProperty("user.dir");
        return base_path + ProgrammingReferenceRelativePath;
    }

}