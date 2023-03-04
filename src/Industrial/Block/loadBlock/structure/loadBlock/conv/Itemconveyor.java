package Industrial.Block.loadBlock.structure.loadBlock.conv;

import Industrial.Block.SuperBlock;
import Industrial.Block.SuperBuild;
import Industrial.item.Item;
import Industrial.item.Items;
import Industrial.table.WorldTable;
import Industrial.time.ifBuilding;
import arc.util.Log;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.world.Block;

public class Itemconveyor extends SuperBlock {

    public Itemconveyor(Block prototype, String name) {
        super(prototype, name);
    }

    @Override
    public SuperBuild create(Building building) {
        return new ItemconverterB(building,this);
    }

    public static class ItemconverterB extends SuperBuild
    {


        public ItemconverterB(Building build,SuperBlock block) {
            super(build, block);
            SuperBuild.addTask(task3);
        }

        @Override
        public boolean acceptItem(SuperBuild b,Item item, int number) {
            if (b.build!= build.nearby(build.rotation)) {
                return super.acceptItem(b, item, number);
            }else {
                return false;
            }
        }

        public ifBuilding task3 = new ifBuilding(()->{
            SuperBuild target = WorldTable.getSuperBuilder(build.nearby(build.rotation));
            //Log.info(target);
            if (target==null)
                return;
            Item item = store.first();
            if (store.get(item)<1)
                return;
            if (target.acceptItem(this,item,1)){
                target.addItem(this,item,1);
                store.add(item,-1);
            }
        },1,this);
    }
}
