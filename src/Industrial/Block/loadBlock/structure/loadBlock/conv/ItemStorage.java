package Industrial.Block.loadBlock.structure.loadBlock.conv;

import Industrial.item.Item;
import Industrial.item.ItemLoad;
import Industrial.item.Items;
import arc.struct.ObjectMap;

public class ItemStorage {
    protected ObjectMap<Item,Integer> map = new ObjectMap<>();
    public ItemStorage() {
        Items.getSeq().each(i->{
            map.put(i,0);
        });
    }
    public void add(Item item,int number){
        int num = map.get(item);
        map.put(item,num+number);
    }
    public int get(Item item){
        return map.get(item);
    }
    public void set(Item item,int number){
        map.put(item,number);
    }
    public Item first(){
        return Items.get("小铜");
    }
}
