package utils.ExcelData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class WorkInfoStruct {
    private String StudentId;
    private String Name;
    private String PClass;
    private String Work;
    private String Dorm;
    public String getStudentId(){return StudentId;}
    public String getName(){return Name;}
    public String getPClass(){return PClass;}
    public String getWork(){return Work;}
    public String getDorm(){return Dorm;}
}
