package utils;

import com.sun.istack.Nullable;
import utils.StaticResourcesConfig.StaticResourcesConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SQLConnection {
    // SQL Driver Version
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    // URL of the MYSQL
    static final String DB_URL =  "jdbc:mysql://"+ StaticResourcesConfig.MYSQL_IP+":"+StaticResourcesConfig.MYSQL_PORT+
            "/"+StaticResourcesConfig.MYSQL_DATABASE_NAME+"?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    // User and Password of the MYSQL Connection
    static final String USER = StaticResourcesConfig.MYSQL_USER_NAME;
    static final String PASS = StaticResourcesConfig.MYSQL_USER_PASSWD;

    // SQL Select Integrated Function
    public static List<Map<String, Object>> SQLQuery(String sql) throws Exception{
        // Initialize Connection, Statement and ResultSet
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        // Initialize the Return Result
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        Map<String, Object> row = null;
        // Try and Get the Error
        try{
            // SQL Driver Version
            Class.forName(JDBC_DRIVER);
            // Connect to the MYSQL
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // Get Statement
            stmt = conn.createStatement();
            // Get Execute Result
            rs = stmt.executeQuery(sql);
            // Store Data in the resultList
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                row = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                resultList.add(row);
            }
            // Close the Connection and Statement
            stmt.close();
            conn.close();
        } catch (Exception se){
            // If Error, Print Error Info and Throw to the Above Function
            se.printStackTrace();
            throw se;
        } finally{
            // Make Sure Connection Close
            try { if (rs != null) rs.close(); } catch (Exception ignored) {};
            try { if (stmt != null) stmt.close(); } catch (Exception ignored) {};
            try { if (conn != null) conn.close(); } catch (Exception ignored) {};
        }
        return resultList;
    }

    // SQL Update, Insert and Delete Integrated Function
    public static int SQLUpdate(String sql) throws Exception{
        // Initialize Connection, Statement and ResultInt
        Connection conn = null;
        Statement stmt = null;
        int resultInt = 0;
        // Try and Get the Error
        try{
            // SQL Driver Version
            Class.forName(JDBC_DRIVER);
            // Connect to the MYSQL
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // Get Statement
            stmt = conn.createStatement();
            // Get Execute Result
            resultInt = stmt.executeUpdate(sql);
            // Close the Connection and Statement
            stmt.close();
            conn.close();
        } catch (Exception se){
            se.printStackTrace();
            throw se;
        } finally{
            // If Error, Print Error Info and Throw to the Above Function
            try { if (stmt != null) stmt.close(); } catch (Exception ignored) {};
            try { if (conn != null) conn.close(); } catch (Exception ignored) {};
        }
        return resultInt;
    }

    public static boolean InsertBasicInfo(String StuId, String Name, String Gender, String Nationality, String Birthdate, String Class, String Note) throws Exception{
        // Construct SQL to insert data into the basicinfo table
        String sql = String.format("INSERT INTO basicinfo (StudentId, Name, Gender, Nationality, BirthDate, Class, Note) VALUES ('%s','%s','%s','%s','%s','%s','%s')",
                StuId, Name, Gender, Nationality, Birthdate, Class, Note);
        // Initialize the resultInt
        int resultInt = 0;
        // Try and get error
        try {
            // Get the SQL Update result
            resultInt = SQLUpdate(sql);
        }catch (Exception se){
            // If Error, Print Error Info and Throw to the Above Function
            se.printStackTrace();
            throw se;
        }
        // If result equals to 0, means didn't insert success, then return false
        if(resultInt==0){
            return false;
        }
        // Insert success, return true
        return true;
    }

    public static boolean InsertDetailInfo(String StudentId, String Name, String Dormitory, String Id, String BirthPlace, String PhoneNum, String PolStatus, String HouseholdTransfer,
                                           String SeniorSchool, String FatherName, String FatherPhone, String FatherWorkPlace, String FatherWorkPosition, String MotherName, String MotherPhone,
                                           String MotherWorkPlace, String MotherWorkPosition, String HomePlace, String PostCode, String PoorGrade, String DetailNote) throws Exception{
        // Construct SQL to insert data into the detailinfo table
        String sql = String.format("INSERT INTO detailinfo (StudentId, Name, Dormitory, Id, BirthPlace, PhoneNum, PolStatus, HouseholdTransfer," +
                        " SeniorSchool, FatherName, FatherPhone, FatherWorkPlace, FatherWorkPosition, MotherName, MotherPhone," +
                        " MotherWorkPlace, MotherWorkPosition, HomePlace, PostCode, PoorGrade, DetailNote) VALUES ('%s','%s','%s'," +
                        "'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",
                StudentId, Name, Dormitory, Id, BirthPlace, PhoneNum, PolStatus, HouseholdTransfer, SeniorSchool, FatherName, FatherPhone, FatherWorkPlace,
                FatherWorkPosition, MotherName, MotherPhone, MotherWorkPlace, MotherWorkPosition, HomePlace, PostCode, PoorGrade, DetailNote);
        // Initialize the resultInt
        int resultInt = 0;
        // Try and get error
        try {
            // Get the SQL Update result
            resultInt = SQLUpdate(sql);
        }catch (Exception se){
            // If Error, Print Error Info and Throw to the Above Function
            se.printStackTrace();
            throw se;
        }
        // If result equals to 0, means didn't insert success, then return false
        if(resultInt==0){
            return false;
        }
        // Insert success, return true
        return true;
    }

    public static boolean InsertClassWorkInfo(String StudentId, String Name, String Class, String Work, String Dorm) throws Exception{
        // Construct SQL to insert data into the classwork table
        String sql = String.format("INSERT INTO classwork (StudentId, Name, Class, Work, Dorm) VALUES ('%s','%s','%s','%s','%s')",
                StudentId, Name, Class, Work, Dorm);
        // Initialize the resultInt
        int resultInt = 0;
        // Try and get error
        try {
            // Get the SQL Update result
            resultInt = SQLUpdate(sql);
        }catch (Exception se){
            // If Error, Print Error Info and Throw to the Above Function
            se.printStackTrace();
            throw se;
        }
        // If result equals to 0, means didn't insert success, then return false
        if(resultInt==0){
            return false;
        }
        // Insert success, return true
        return true;
    }

    public static boolean Login(String QQ, String Password){
        // Construct SQL to query if the account exists and password correct
        String sql = String.format("SELECT count(*) AS c FROM userid WHERE QQ='%s' AND Password='%s'",QQ,Password);
        // Initialize the resultList
        List<Map<String, Object>> resultList = null;
        // Try and get error
        try{
            // Get the SQL Query result
            resultList = SQLQuery(sql);
        }catch (Exception e){
            // If Error, Print Error Info and Throw to the Above Function
            e.printStackTrace();
            return false;
        }
        if(resultList.get(0).get("c").toString().equals("0")){
            return false;
        }else{
            return true;
        }

    }

    public static List<Map<String, Object>> SearchBasicInfo(String StudentId, String Name){
        // Construct SQL to search in to basicinfo
        String sql = "SELECT * FROM basicinfo WHERE StudentId like '%"+StudentId+"%' AND Name like '%"+Name+"%'";
        // Initialize the resultList
        List<Map<String, Object>> resultList = null;
        // Try and get error
        try{
            // Get the SQL Query result
            resultList = SQLQuery(sql);
        }catch (Exception e){
            // If Error, Print Error Info and Throw to the Above Function
            e.printStackTrace();
            return null;
        }
        return resultList;
    }

    public static boolean UpdateBasicInfo(String StudentId, String Name, String Gender, String Nationality, String Birthdate, String Class, String Note) throws Exception{
        // Construct SQL to update the basicinfo table where StudentId is same
        String sql = String.format("UPDATE basicinfo SET Name='%s', Gender='%s', Nationality='%s', BirthDate='%s', Class='%s', Note='%s' WHERE StudentId='%s'",
                Name,Gender,Nationality,Birthdate,Class,Note,StudentId);
        // Initialize the resultInt
        int resultInt = 0;
        // Try and get error
        try {
            // Get the SQL Update result
            resultInt = SQLUpdate(sql);
        }catch (Exception se){
            // If Error, Print Error Info and Throw to the Above Function
            se.printStackTrace();
            throw se;
        }
        // If result equals to 0, means didn't insert success, then return false
        if(resultInt==0){
            return false;
        }
        // Insert success, return true
        return true;
    }

    public static boolean DeleteBasicInfo(String StudentId) throws Exception{
        // Construct SQL to delete a record in the basicinfo
        String sql = String.format("DELETE FROM basicinfo WHERE StudentId='%s'",StudentId);
        // Initialize the resultInt
        int resultInt = 0;
        // Try and get error
        try {
            // Get the SQL Update result
            resultInt = SQLUpdate(sql);
        }catch (Exception se){
            // If Error, Print Error Info and Throw to the Above Function
            se.printStackTrace();
            throw se;
        }
        // If result equals to 0, means didn't insert success, then return false
        if(resultInt==0){
            return false;
        }
        // Insert success, return true
        return true;
    }

    public static List<Map<String, Object>> SearchDetailInfo(String StudentId, String Name){
        // Construct SQL to search in to detailinfo
        String sql = "SELECT * FROM detailinfo WHERE StudentId like '%"+StudentId+"%' AND Name like '%"+Name+"%'";
        // Initialize the resultList
        List<Map<String, Object>> resultList = null;
        // Try and get error
        try{
            // Get the SQL Query result
            resultList = SQLQuery(sql);
        }catch (Exception e){
            // If Error, Print Error Info and Throw to the Above Function
            e.printStackTrace();
            return null;
        }
        return resultList;
    }

    public static boolean UpdateDetailInfo(String StudentId, String Name, String Dormitory, String Id, String BirthPlace, String PhoneNum, String PolStatus, String HouseholdTransfer,
                                           String SeniorSchool, String FatherName, String FatherPhone, String FatherWorkPlace, String FatherWorkPosition, String MotherName, String MotherPhone,
                                           String MotherWorkPlace, String MotherWorkPosition, String HomePlace, String PostCode, String PoorGrade, String DetailNote) throws Exception{
        String sql = String.format("UPDATE detailinfo SET Name='%s', Dormitory='%s', Id='%s', BirthPlace='%s', PhoneNum='%s', PolStatus='%s', HouseholdTransfer='%s'," +
                        " SeniorSchool='%s', FatherName='%s', FatherPhone='%s', FatherWorkPlace='%s', FatherWorkPosition='%s', MotherName='%s', MotherPhone='%s'," +
                        " MotherWorkPlace='%s', MotherWorkPosition='%s', HomePlace='%s', PostCode='%s', PoorGrade='%s', DetailNote='%s' WHERE StudentId='%s'",
                Name, Dormitory, Id, BirthPlace, PhoneNum, PolStatus, HouseholdTransfer, SeniorSchool, FatherName, FatherPhone, FatherWorkPlace,
                FatherWorkPosition, MotherName, MotherPhone, MotherWorkPlace, MotherWorkPosition, HomePlace, PostCode, PoorGrade, DetailNote, StudentId);
        // Initialize the resultInt
        int resultInt = 0;
        // Try and get error
        try {
            // Get the SQL Update result
            resultInt = SQLUpdate(sql);
        }catch (Exception se){
            // If Error, Print Error Info and Throw to the Above Function
            se.printStackTrace();
            throw se;
        }
        // If result equals to 0, means didn't insert success, then return false
        if(resultInt==0){
            return false;
        }
        // Insert success, return true
        return true;
    }

    public static boolean DeleteDetailInfo(String StudentId) throws Exception{
        // Construct SQL to delete a record in the detailinfo
        String sql = String.format("DELETE FROM detailinfo WHERE StudentId='%s'",StudentId);
        // Initialize the resultInt
        int resultInt = 0;
        // Try and get error
        try {
            // Get the SQL Update result
            resultInt = SQLUpdate(sql);
        }catch (Exception se){
            // If Error, Print Error Info and Throw to the Above Function
            se.printStackTrace();
            throw se;
        }
        // If result equals to 0, means didn't insert success, then return false
        if(resultInt==0){
            return false;
        }
        // Insert success, return true
        return true;
    }

    public static List<Map<String, Object>> SearchWorkInfo(String StudentId, String Name){
        // Construct SQL to search in to classwork
        String sql = "SELECT * FROM classwork WHERE StudentId like '%"+StudentId+"%' AND Name like '%"+Name+"%'";
        // Initialize the resultList
        List<Map<String, Object>> resultList = null;
        // Try and get error
        try{
            // Get the SQL Query result
            resultList = SQLQuery(sql);
        }catch (Exception e){
            // If Error, Print Error Info and Throw to the Above Function
            e.printStackTrace();
            return null;
        }
        return resultList;
    }

    public static boolean UpdateWorkInfo(String StudentId, String Name, String Class, String Work, String Dorm) throws Exception{
        // Construct SQL to update the classwork table where StudentId is same
        String sql = String.format("UPDATE classwork SET Name='%s', Class='%s', Work='%s', Dorm='%s' WHERE StudentId='%s'",
                Name, Class, Work, Dorm, StudentId);
        // Initialize the resultInt
        int resultInt = 0;
        // Try and get error
        try {
            // Get the SQL Update result
            resultInt = SQLUpdate(sql);
        }catch (Exception se){
            // If Error, Print Error Info and Throw to the Above Function
            se.printStackTrace();
            throw se;
        }
        // If result equals to 0, means didn't insert success, then return false
        if(resultInt==0){
            return false;
        }
        // Insert success, return true
        return true;
    }

    public static boolean DeleteWorkInfo(String StudentId) throws Exception{
        // Construct SQL to delete a record in the classwork
        String sql = String.format("DELETE FROM classwork WHERE StudentId='%s'",StudentId);
        // Initialize the resultInt
        int resultInt = 0;
        // Try and get error
        try {
            // Get the SQL Update result
            resultInt = SQLUpdate(sql);
        }catch (Exception se){
            // If Error, Print Error Info and Throw to the Above Function
            se.printStackTrace();
            throw se;
        }
        // If result equals to 0, means didn't insert success, then return false
        if(resultInt==0){
            return false;
        }
        // Insert success, return true
        return true;
    }

    public static int QueryUserLevel(String QQ) throws Exception{
        // Construct SQL to query a record of userinfo
        String sql = String.format("SELECT Class FROM userid WHERE QQ='%s'",QQ);
        // Initialize the resultList
        List<Map<String, Object>> resultList = null;
        // Try and get error
        try{
            // Get the SQL Query result
            resultList = SQLQuery(sql);
        }catch (Exception e){
            // If Error, Print Error Info and Throw to the Above Function
            e.printStackTrace();
            return 0;
        }
        return Integer.parseInt(resultList.get(0).get("Class").toString());
    }

    public static boolean InsertUser(String QQ, String Level, String Note, @Nullable String Password) throws Exception{
        int level = StaticResourcesConfig.USERLEVEL_OPTIONS.indexOf(Level);
        String sql = "";
        if(level<=1){
            sql = String.format("INSERT INTO userid (QQ, Class, Note) VALUES ('%s', %d, '%s')",QQ, level, Note);
        }else{
            sql = String.format("INSERT INTO userid (QQ, Class, Note, Password) VALUES ('%s', %d, '%s', '%s')",QQ, level, Note, Password);
        }
        // Initialize the resultInt
        int resultInt = 0;
        // Try and get error
        try {
            // Get the SQL Update result
            resultInt = SQLUpdate(sql);
        }catch (Exception se){
            // If Error, Print Error Info and Throw to the Above Function
            se.printStackTrace();
            throw se;
        }
        // If result equals to 0, means didn't insert success, then return false
        if(resultInt==0){
            return false;
        }
        // Insert success, return true
        return true;
    }

    public static List<Map<String, Object>> SearchUserInfo(int level){
        // Construct SQL to search in to userid
        String sql = "SELECT * FROM userid WHERE Class<"+level+" ORDER BY CLASS DESC";
        // Initialize the resultList
        List<Map<String, Object>> resultList = null;
        // Try and get error
        try{
            // Get the SQL Query result
            resultList = SQLQuery(sql);
        }catch (Exception e){
            // If Error, Print Error Info and Throw to the Above Function
            e.printStackTrace();
            return null;
        }
        return resultList;
    }

    public static boolean UpdateUserInfo(String QQ, String Level, String Note) throws Exception{
        int level = StaticResourcesConfig.USERLEVEL_OPTIONS.indexOf(Level);
        // Construct SQL to update the userid table where StudentId is same
        String sql = String.format("UPDATE userid SET Class=%d, Note='%s' WHERE QQ='%s'", level, Note, QQ);
        // Initialize the resultInt
        int resultInt = 0;
        // Try and get error
        try {
            // Get the SQL Update result
            resultInt = SQLUpdate(sql);
        }catch (Exception se){
            // If Error, Print Error Info and Throw to the Above Function
            se.printStackTrace();
            throw se;
        }
        // If result equals to 0, means didn't insert success, then return false
        if(resultInt==0){
            return false;
        }
        // Insert success, return true
        return true;
    }

    public static boolean DeleteUserInfo(String QQ) throws Exception{
        // Construct SQL to delete a record in the userid
        String sql = String.format("DELETE FROM userid WHERE QQ='%s'",QQ);
        // Initialize the resultInt
        int resultInt = 0;
        // Try and get error
        try {
            // Get the SQL Update result
            resultInt = SQLUpdate(sql);
        }catch (Exception se){
            // If Error, Print Error Info and Throw to the Above Function
            se.printStackTrace();
            throw se;
        }
        // If result equals to 0, means didn't insert success, then return false
        if(resultInt==0){
            return false;
        }
        // Insert success, return true
        return true;
    }

    public static List<Map<String, Object>> GetUserInfo(String QQ){
        // Construct SQL to search in to userid
        String sql = String.format("SELECT * FROM userid WHERE QQ='%s'",QQ);
        // Initialize the resultList
        List<Map<String, Object>> resultList = null;
        // Try and get error
        try{
            // Get the SQL Query result
            resultList = SQLQuery(sql);
        }catch (Exception e){
            // If Error, Print Error Info and Throw to the Above Function
            e.printStackTrace();
            return null;
        }
        return resultList;
    }

    public static boolean ChangeUserPassword(String QQ, String Password) throws Exception{
        // Construct SQL to update password in the userid
        String sql = String.format("UPDATE userid SET Password='%s' WHERE QQ='%s'",Password, QQ);
        // Initialize the resultInt
        int resultInt = 0;
        // Try and get error
        try {
            // Get the SQL Update result
            resultInt = SQLUpdate(sql);
        }catch (Exception se){
            // If Error, Print Error Info and Throw to the Above Function
            se.printStackTrace();
            throw se;
        }
        // If result equals to 0, means didn't insert success, then return false
        if(resultInt==0){
            return false;
        }
        // Insert success, return true
        return true;
    }
}
