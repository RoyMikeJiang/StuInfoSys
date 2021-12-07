package utils.StaticResourcesConfig;

public class ItemStruct{
    public String file_path;
    public String func_name;
    public ItemStruct(String func_name, String file_path){
        this.func_name = func_name;
        this.file_path = file_path;
    }
}