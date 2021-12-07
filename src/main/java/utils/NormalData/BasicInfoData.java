package utils.NormalData;

import java.util.Map;

public class BasicInfoData{
    private String StudentId;
    private String Name;
    private String Gender;
    private String Nationality;
    private String BirthDate;
    private String PClass;
    private String Note;
    public BasicInfoData(Map<String, Object> info_map){
        StudentId = info_map.get("StudentId").toString();
        Name = info_map.get("Name").toString();
        Gender = info_map.get("Gender").toString();
        Nationality = info_map.get("Nationality").toString();
        BirthDate = info_map.get("BirthDate").toString();
        PClass = info_map.get("Class").toString();
        Note = info_map.get("Note").toString();
    }
    public String getStudentId(){return StudentId;}
    public String getName(){return Name;}
    public String getGender(){return Gender;}
    public String getNationality(){return Nationality;}
    public String getBirthDate(){return BirthDate;}
    public String getPClass(){return PClass;}
    public String getNote(){return Note;}
}
