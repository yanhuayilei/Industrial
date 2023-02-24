package Industrial.Block.loadBlock.structure.loadBlock.Net;

import Industrial.Block.SuperBlock;
import Industrial.Block.SuperBuild;
import Industrial.load.setupItems;
import Industrial.time.ifBuilding;
import arc.struct.ObjectMap;
import arc.util.Log;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.gen.Call;
import mindustry.type.Item;
import mindustry.world.Block;

public class InpNode extends SuperBlock {
    public InpNode(Block prototype, String name) {
        super(prototype, name);
    }

    @Override
    public SuperBuild create(Building building) {
        return new InpNodeB(building,this);
    }
    public static class InpNodeB extends ItemNetnode.ItemNetnodeB {


        public Building[] blocks = new Building[]{null,null,null,null};

        public InpNodeB(Building build, SuperBlock block) {
            super(build, block);
        }
        public ifBuilding task = new ifBuilding(()->{
            Log.info(core);
            if (core==null)
                return;
            Building b1 = blocks[0];
            if (b1!=null){
                if (b1.items!=null){
                    aPairOfIncompatibles pair = core.allpair.get(channel);
                    if (pair.out!=null) {
                                if (pair.out.acceptOutput(b1.items.first())) {
                                    int a = b1.items.get(b1.items.first())-10;
                                    if (a>=0) {
                                        pair.out.addItem(b1.items.first(),10);
                                        setupItems.addItem(b1,b1.items.first(),-10);
                                    }else {
                                        pair.out.addItem(b1.items.first(),b1.items.get(b1.items.first()));
                                        Call.clearItems(b1);
                                    }
                                }
                    }
                }
            }

            b1 = blocks[1];

            if (b1!=null){
                if (b1.items!=null){
                    aPairOfIncompatibles pair = core.allpair.get(channel);
                    if (pair.out!=null) {
                        if (pair.out.acceptOutput(b1.items.first())) {
                            int a = b1.items.get(b1.items.first())-10;
                            if (a>=0) {
                                pair.out.addItem(b1.items.first(),10);
                                setupItems.addItem(b1,b1.items.first(),-10);
                            }else {
                                pair.out.addItem(b1.items.first(),b1.items.get(b1.items.first()));
                                Call.clearItems(b1);
                            }
                        }
                    }
                }
            }

            b1 = blocks[2];
            if (b1!=null){
                if (b1.items!=null){
                    aPairOfIncompatibles pair = core.allpair.get(channel);
                    if (pair.out!=null) {
                        if (pair.out.acceptOutput(b1.items.first())) {
                            int a = b1.items.get(b1.items.first())-10;
                            if (a>=0) {
                                pair.out.addItem(b1.items.first(),10);
                                setupItems.addItem(b1,b1.items.first(),-10);
                            }else {
                                pair.out.addItem(b1.items.first(),b1.items.get(b1.items.first()));
                                Call.clearItems(b1);
                            }
                        }
                    }
                }
            }

            b1 = blocks[3];
            if (b1!=null){
                if (b1.items!=null){
                    aPairOfIncompatibles pair = core.allpair.get(channel);
                    if (pair.out!=null) {
                        if (pair.out.acceptOutput(b1.items.first())) {
                            int a = b1.items.get(b1.items.first())-10;
                            if (a>=0) {
                                pair.out.addItem(b1.items.first(),10);
                                setupItems.addItem(b1,b1.items.first(),-10);
                            }else {
                                pair.out.addItem(b1.items.first(),b1.items.get(b1.items.first()));
                                Call.clearItems(b1);
                            }
                        }
                    }
                }
            }

        },1,this);


        @Override
        public void run() {
            if (core!=null){
                core.allpair.get(channel).inp = this;
            }
            blocks[0] = Vars.world.build(Math.round((build.x()/8)-build.block().size),(Math.round(build.y() / 8)));
            blocks[1] = Vars.world.build(Math.round((build.x()/8)),(Math.round(build.y() / 8))+build.block().size);
            blocks[2] = Vars.world.build(Math.round((build.x()/8)+build.block().size),(Math.round(build.y() / 8)));
            blocks[3] = Vars.world.build(Math.round((build.x()/8)),(Math.round(build.y() / 8))-build.block().size);
            SuperBuild.addTask(task);
        }
    }
}
