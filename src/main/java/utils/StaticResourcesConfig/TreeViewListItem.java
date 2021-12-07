package utils.StaticResourcesConfig;

import utils.StaticResourcesConfig.ItemStruct;

import java.util.List;

public class TreeViewListItem{
    public String list_name;
    public List<ItemStruct> item_list;
    public TreeViewListItem(String list_name, List<ItemStruct> item_list){
        this.list_name = list_name;
        this.item_list = item_list;
    }
}