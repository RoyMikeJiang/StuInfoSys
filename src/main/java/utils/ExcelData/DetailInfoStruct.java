package utils.ExcelData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class DetailInfoStruct {
    private String StudentId;
    private String PName;
    private String Dorm;
    private String Id;
    private String BirthPlace;
    private String PhoneNum;
    private String PolStatus;
    private String HouseholdTransfer;
    private String SeniorSchool;
    private String FatherName;
    private String FatherPhone;
    private String FatherWorkPlace;
    private String FatherWorkPosition;
    private String MotherName;
    private String MotherPhone;
    private String MotherWorkPlace;
    private String MotherWorkPosition;
    private String HomePlace;
    private String PostCode;
    private String PoorGrade;
    private String DetailNote;
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
