package Industrial.Block.loadBlock.structure.loadBlock.Net;

import Industrial.Block.SuperBlock;
import Industrial.Block.SuperBuild;
import Industrial.table.WorldTable;
import Industrial.time.ifBuilding;
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

        public SuperBuild convert1 = null;
        public SuperBuild convert2 = null;
        public SuperBuild convert3 = null;
        public SuperBuild convert4 = null;
        public ifBuilding task = new ifBuilding(()->{
            convert1 = getthisBuild(-build.block().size,0);
            convert2 = getthisBuild(0,build.block().size);
            convert3 = getthisBuild(build.block().size,0);
            convert4 = getthisBuild(0,-build.block().size);
            if (convert1!=null&&convert1.build.team()==build.team()){
                if (isCore(convert1)){
                    transportCore.transportCoreB coreB = (transportCore.transportCoreB) convert4;
                    if (coreB.open) {
                        Run(convert1);
                        return;
                    }
                }else {
                    conveyorBelt.conveyorBeltB c = (conveyorBelt.conveyorBeltB) convert1;
                    SuperBuild core = c.getCore(new HashSet<SuperBuild>());
                    //Log.info("1find2");
                    if (core!=null){
                        Run(core);
                        //Log.info("1find");
                        return;
                    }
                }
            }
            if (convert2!=null&&convert2.build.team()==build.team()){
                if (isCore(convert2)){
                    transportCore.transportCoreB coreB = (transportCore.transportCoreB) convert2;
                    if (coreB.open) {
                        Run(convert2);
                        return;
                    }
                }else {
                    //Log.info("test1");
                    conveyorBelt.conveyorBeltB c = (conveyorBelt.conveyorBeltB) convert2;

                    SuperBuild core = c.getCore(new HashSet<SuperBuild>());
                    if (core!=null){
                        //Log.info("test2");
                        Run(core);
                        return;
                    }
                }
            }
            if (convert3!=null&&convert3.build.team()==build.team()){
                if (isCore(convert3)){
                    transportCore.transportCoreB coreB = (transportCore.transportCoreB) convert3;
                    if (coreB.open) {
                        Run(convert3);
                        return;
                    }
                }else {
                    conveyorBelt.conveyorBeltB c = (conveyorBelt.conveyorBeltB) convert3;
                    SuperBuild core = c.getCore(new HashSet<SuperBuild>());
                    if (core!=null){
                        Run(core);
                        return;
                    }
                }
            }
            if (convert4!=null&&convert4.build.team()==build.team()){
                if (isCore(convert4)){
                    transportCore.transportCoreB coreB = (transportCore.transportCoreB) convert4;
                    if (coreB.open) {
                        Run(convert4);
                        return;
                    }
                }else {
                    conveyorBelt.conveyorBeltB c = (conveyorBelt.conveyorBeltB) convert4;
                    SuperBuild core = c.getCore(new HashSet<SuperBuild>());
                    if (core!=null){
                        Run(core);
                        return;
                    }
                }
            }
            //this.core = null;
            Run(null);
        },1,this);

        public ItemNetnodeB(Building build, SuperBlock block) {
            super(build, block);
            SuperBuild.addTask(task);
        }
        public int channel = 1;

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
