package utils.NormalData;

import java.util.Map;
import utils.StaticResourcesConfig.StaticResourcesConfig;

public class UserInfoData {
    private final String QQ;
    private final String Level;
    private final String Note;
    public UserInfoData(Map<String, Object> info_map) {
        QQ = info_map.get("QQ").toString();
        Level = StaticResourcesConfig.USERLEVEL_OPTIONS.get(Integer.parseInt(info_map.get("Class").toString())-1);
        Note = info_map.get("Note").toString();
    }
    public String getQQ(){return QQ;}
    public String getLevel(){return Level;}
    public String getNote(){return Note;}
}
