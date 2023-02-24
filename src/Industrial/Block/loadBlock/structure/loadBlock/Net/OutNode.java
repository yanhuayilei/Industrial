package Industrial.Block.loadBlock.structure.loadBlock.Net;

import Industrial.Block.SuperBlock;
import Industrial.Block.SuperBuild;
import Industrial.load.setupItems;
import arc.util.Log;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.Block;

public class OutNode extends SuperBlock {
    public OutNode(Block prototype, String name) {
        super(prototype, name);
    }

    @Override
    public SuperBuild create(Building building) {
        return new OutNodeB(building,this);
    }

    public static class OutNodeB extends ItemNetnode.ItemNetnodeB{
        public Building[] blocks = new Building[]{null,null,null,null};
        public OutNodeB(Building build, SuperBlock block) {
            super(build, block);
        }

        @Override
        public void run() {
            if (core!=null){
                core.allpair.get(channel).out = this;
            }
            blocks[0] = Vars.world.build(Math.round((build.x()/8)-build.block().size),(Math.round(build.y() / 8)));
            blocks[1] = Vars.world.build(Math.round((build.x()/8)),(Math.round(build.y() / 8))+build.block().size);
            blocks[2] = Vars.world.build(Math.round((build.x()/8)+build.block().size),(Math.round(build.y() / 8)));
            blocks[3] = Vars.world.build(Math.round((build.x()/8)),(Math.round(build.y() / 8))-build.block().size);
        }

        public boolean acceptOutput(Item item){
            if (item!=null){
                if (blocks[0]!=null){
                    if (blocks[0].items!=null&&blocks[0].acceptItem(blocks[0],item)&&blocks[0].items.get(item)<blocks[0].block().itemCapacity){
                        Log.info("o0");
                        return true;
                    }
                }

                if (blocks[1]!=null){
                    /*Log.info(blocks[1].items);
                    Log.info(blocks[1].acceptItem(blocks[1],item));
                    Log.info(blocks[1].items.get(item)<blocks[1].block().itemCapacity);*/
                    if (blocks[1].items!=null&&blocks[1].acceptItem(blocks[1],item)&&blocks[1].items.get(item)<blocks[1].block().itemCapacity){
                        Log.info("1o");
                        return true;
                    }
                }

                if (blocks[2]!=null){
                    if (blocks[2].items!=null&&blocks[2].acceptItem(blocks[2],item)&&blocks[2].items.get(item)<blocks[2].block().itemCapacity){
                        Log.info("o2");
                        return true;
                    }
                }
                if (blocks[3]!=null){
                    if (blocks[3].items!=null&&blocks[3].acceptItem(blocks[3],item)&&blocks[3].items.get(item)<blocks[3].block().itemCapacity){
                        Log.info("o3");
                        return true;
                    }
                }
                return false;
            }else {
                return false;
            }
        }
        public void addItem(Item item){
            addItem(item,1);
        }
        public void addItem(Item item,int amount){
            if (item!=null){
                if (acceptOutput(item)){
                    if (blocks[0]!=null){
                        if (blocks[0].items!=null&&blocks[0].acceptItem(blocks[0],item)&&blocks[0].items.get(item)<blocks[0].block().itemCapacity){
                            setupItems.addItem(blocks[0],item,amount);
                            Log.info("0out");
                            return;
                        }
                    }
                    if (blocks[1]!=null){
                        if (blocks[1].items!=null&&blocks[1].acceptItem(blocks[1],item)&&blocks[1].items.get(item)<blocks[1].block().itemCapacity){
                            setupItems.addItem(blocks[1],item,amount);
                            Log.info("1out");
                            return;
                        }

                    }
                    if (blocks[2]!=null){
                        if (blocks[2].items!=null&&blocks[2].acceptItem(blocks[2],item)&&blocks[2].items.get(item)<blocks[2].block().itemCapacity){
                            setupItems.addItem(blocks[2],item,amount);
                            Log.info("2out");
                            return;
                        }

                    }
                    if (blocks[3]!=null){
                        if (blocks[3].items!=null&&blocks[3].acceptItem(blocks[3],item)&&blocks[3].items.get(item)<blocks[3].block().itemCapacity){
                            setupItems.addItem(blocks[3],item,amount);
                            Log.info("3out");
                            return;
                        }

                    }
                }
            }
        }
    }
}
