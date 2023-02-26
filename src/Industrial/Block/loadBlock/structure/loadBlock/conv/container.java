package Industrial.Block.loadBlock.structure.loadBlock.conv;

import Industrial.Block.SuperBlock;
import Industrial.Block.SuperBuild;
import Industrial.item.Item;
import mindustry.gen.Building;
import mindustry.world.Block;

public class container extends SuperBlock {
    public container(Block prototype, String name) {
        super(prototype, name);
    }

    @Override
    public SuperBuild create(Building building) {
        return new containerB(building,this);
    }

    public static class containerB extends SuperBuild {

        public containerB(Building build, SuperBlock block) {
            super(build, block);
        }

        @Override
        public boolean acceptItem(Item item, int number) {
            if (item==null)
                return false;
            return (store.get(item)+number)<=200;
        }

        @Override
        public void addItem(Item item, int number) {
            if (acceptItem(item,number)){
                store.add(item,number);
            }
        }
    }
}
