package utils.NormalData;

import java.util.Map;

public class DetailInfoData {
    private final String StudentId;
    private final String PName;
    private final String Dorm;
    private final String Id;
    private final String BirthPlace;
    private final String PhoneNum;
    private final String PolStatus;
    private final String HouseholdTransfer;
    private final String SeniorSchool;
    private final String FatherName;
    private final String FatherPhone;
    private final String FatherWorkPlace;
    private final String FatherWorkPosition;
    private final String MotherName;
    private final String MotherPhone;
    private final String MotherWorkPlace;
    private final String MotherWorkPosition;
    private final String HomePlace;
    private final String PostCode;
    private final String PoorGrade;
    private final String DetailNote;
    public DetailInfoData(Map<String, Object> info_map){
        StudentId = info_map.get("StudentId").toString();
        PName = info_map.get("Name").toString();
        Dorm = info_map.get("Dormitory").toString();
        Id = info_map.get("Id").toString();
        BirthPlace = info_map.get("BirthPlace").toString();
        PhoneNum = info_map.get("PhoneNum").toString();
        PolStatus = info_map.get("PolStatus").toString();
        HouseholdTransfer = info_map.get("HouseholdTransfer").toString();
        SeniorSchool = info_map.get("SeniorSchool").toString();
        FatherName = info_map.get("FatherName").toString();
        FatherPhone = info_map.get("FatherPhone").toString();
        FatherWorkPlace = info_map.get("FatherWorkPlace").toString();
        FatherWorkPosition = info_map.get("FatherWorkPosition").toString();
        MotherName = info_map.get("MotherName").toString();
        MotherPhone = info_map.get("MotherPhone").toString();
        MotherWorkPlace = info_map.get("MotherWorkPlace").toString();
        MotherWorkPosition = info_map.get("MotherWorkPosition").toString();
        HomePlace = info_map.get("HomePlace").toString();
        PostCode = info_map.get("PostCode").toString();
        PoorGrade = info_map.get("PoorGrade").toString();
        DetailNote = info_map.get("DetailNote").toString();
    }
    public String getStudentId() {return StudentId;}
    public String getPName() {return PName;}
    public String getDorm() {return Dorm;}
    public String getId() {return Id;}
    public String getBirthPlace() {return BirthPlace;}
    public String getPhoneNum() {return PhoneNum;}
    public String getPolStatus() {return PolStatus;}
    public String getHouseholdTransfer() {return HouseholdTransfer;}
    public String getSeniorSchool() {return SeniorSchool;}
    public String getFatherName() {return FatherName;}
    public String getFatherPhone() {return FatherPhone;}
    public String getFatherWorkPlace() {return FatherWorkPlace;}
    public String getFatherWorkPosition() {return FatherWorkPosition;}
    public String getMotherName() {return MotherName;}
    public String getMotherPhone() {return MotherPhone;}
    public String getMotherWorkPlace() {return MotherWorkPlace;}
    public String getMotherWorkPosition() {return MotherWorkPosition;}
    public String getHomePlace() {return HomePlace;}
    public String getPostCode() {return PostCode;}
    public String getPoorGrade() {return PoorGrade;}
    public String getDetailNote() {return DetailNote;}

}
