
package Industrial.Block;

import arc.struct.ObjectMap;
import mindustry.Vars;
import mindustry.content.Items;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.Block;

public class SuperBlock {
    public final Block block;
    public int maxItem = 0;
    public ObjectMap<Item, Integer> formulation = new ObjectMap();
    public final String name;
    public boolean hasitem = false;
    public boolean hasnowitem = false;

    public SuperBlock(Block prototype, String name) {
        this.name = name;
        this.block = prototype;
        /*Vars.content.items().each((item) -> {
            Integer var10000 = (Integer)this.formulation.put(item, 0);
        });
        this.formulation.put(Items.copper, 100);*/
        Blocks.add(name, this);
    }

    public SuperBuild create(Building building) {
        return null;
    }
}
