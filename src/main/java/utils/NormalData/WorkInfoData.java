package utils.NormalData;

import java.util.Map;

public class WorkInfoData {
    private final String StudentId;
    private final String Name;
    private final String Class;
    private final String Work;
    private final String Dorm;
    public WorkInfoData(Map<String, Object> info_map){
        StudentId = info_map.get("StudentId").toString();
        Name = info_map.get("Name").toString();
        Class = info_map.get("Class").toString();
        Work = info_map.get("Work").toString();
        Dorm = info_map.get("Dorm").toString();
    }
    public String getStudentId(){return StudentId;}
    public String getName(){return Name;}
    public String getPClass(){return Class;}
    public String getWork(){return Work;}
    public String getDorm(){return Dorm;}
}
