package Industrial.item;

import arc.struct.ObjectMap;
import arc.struct.Seq;

public class Items {
    private static final ObjectMap<String,Item> allitem = new ObjectMap<>();
    public static void add(Item item) {
        if (item==null)
            return;
        allitem.put(item.getName(),item);
    }
    public static Seq<Item> getSeq(){
        return allitem.values().toSeq();
    }
    public static Item get(String name){
        return allitem.get(name);
    }
}
