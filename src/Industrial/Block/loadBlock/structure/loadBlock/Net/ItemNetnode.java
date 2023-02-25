package Industrial.Block.loadBlock.structure.loadBlock.Net;

import Industrial.Block.SuperBlock;
import Industrial.Block.SuperBuild;
import Industrial.table.WorldTable;
import Industrial.time.ifBuilding;
import arc.struct.Seq;
import mindustry.gen.Building;
import mindustry.world.Block;

import java.util.HashSet;

public class ItemNetnode extends SuperBlock {

    public ItemNetnode(Block prototype, String name) {
        super(prototype, name);
    }

    @Override
    public SuperBuild create(Building building) {
        return new ItemNetnodeB(building,this);
    }

    public static class ItemNetnodeB extends conveyorBelt.conveyorBeltB {

        public transportCore.transportCoreB core = null;

        public Seq<SuperBuild> allconvert = new Seq<>();
        public ifBuilding task = new ifBuilding(()->{
            allconvert = new Seq<>();
            neighborhood().each(i->{
                if (i==null)
                    return;
                SuperBuild build1 = WorldTable.getSuperBuilder(Math.round(i.x()/8),Math.round(i.y()/8));
                allconvert.add(build1);
            });

            boolean[] b = {false};
            allconvert.each(i->{
                if (!b[0]){
                    b[0] = dispose(i);
                }
            });

            //this.core = null;
            if (!b[0])
                Run(null);
        },1,this);
        public boolean dispose(SuperBuild build){
            if (build!=null&&build.build.team()==this.build.team()){
                if (isCore(build)){
                    transportCore.transportCoreB coreB = (transportCore.transportCoreB) build;
                    if (coreB.open) {
                        Run(build);
                        return true;
                    }
                }else {
                    conveyorBelt.conveyorBeltB c = (conveyorBelt.conveyorBeltB) build;
                    SuperBuild core = c.getCore(new HashSet<>());
                    //Log.info("1find2");
                    if (core!=null){
                        Run(core);
                        //Log.info("1find");
                        return true;
                    }
                }
            }
            return false;
        }

        public ItemNetnodeB(Building build, SuperBlock block) {
            super(build, block);
            SuperBuild.addTask(task);
        }
        public int channel = 0;

        public void Run(SuperBuild core){
            this.core = (transportCore.transportCoreB) core;
        }
        @Override
        public void run() {
        }
        public SuperBuild getthisBuild(int offsetX,int offsetY){
            SuperBuild build = WorldTable.getSuperBuilder(Math.round((this.build.x()/8)+offsetX),Math.round((this.build.y()/8)+offsetY));
            if (build==null)
                return null;
            if (isconv(build)||isCore(build))
                return build;
            else
                return null;
        }


    }


}
