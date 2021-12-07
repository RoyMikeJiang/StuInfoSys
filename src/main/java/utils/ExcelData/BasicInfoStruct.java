package utils.ExcelData;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class BasicInfoStruct {
    private String StudentId;
    private String Name;
    private String Gender;
    private String Nationality;
    private String BirthDate;
    private String PClass;
    private String Note;
    public String getStudentId(){return StudentId;}
    public String getName(){return Name;}
    public String getGender(){return Gender;}
    public String getNationality(){return Nationality;}
    public String getBirthDate(){return BirthDate;}
    public String getPClass(){return PClass;}
    public String getNote(){return Note;}
}
