package Industrial.load;

import mindustry.gen.Building;
import mindustry.gen.Call;
import mindustry.type.Item;

public class setupItems {
    public static void addItem(Building building, Item item,int amount){
        int set = building.items.get(item);
        Call.setItem(building,item,set+amount);
    }
    public static int getItem(Building building,Item item){
        return building.items.get(item);
    }
    public static void clearItem(Building building){
        Call.clearItems(building);
    }
}
