package Industrial.Block.loadBlock.structure.loadBlock.Net;

import Industrial.Block.SuperBlock;
import Industrial.Block.SuperBuild;
import Industrial.table.WorldTable;
import mindustry.gen.Building;
import mindustry.world.Block;

import java.util.Set;

public class conveyorBelt extends SuperBlock {

    public conveyorBelt(Block prototype, String name) {
        super(prototype, name);
    }

    @Override
    public SuperBuild create(Building building) {
        return new conveyorBeltB(building, this);
    }

    public static class conveyorBeltB extends SuperBuild {
        //public boolean isfind = false;
        public conveyorBeltB(Building build, SuperBlock block) {
            super(build, block);
        }

        @Override
        public void run() {

        }
        public SuperBuild convert1 = null;
        public SuperBuild convert2 = null;
        public SuperBuild convert3 = null;
        public SuperBuild convert4 = null;
        public SuperBuild getCore(Set<SuperBuild> findBuilds){
            convert1 = getthisBuild(-build.block().size,0);
            convert2 = getthisBuild(0,build.block().size);
            convert3 = getthisBuild(build.block().size,0);
            convert4 = getthisBuild(0,-build.block().size);
            if (findBuilds.contains(this)) {
                //Log.info("error!!!!!!!!!!!!!");
                return null;
            }
            /*Log.info("------------------------");
            Log.info(convert1);
            Log.info(convert2);
            Log.info(convert3);
            Log.info(convert4);
            Log.info("------------------------");*/
            findBuilds.add(this);
            if (convert1!=null&&convert1.build.team()==build.team()){
                if (isCore(convert1)){
                    transportCore.transportCoreB coreB = (transportCore.transportCoreB) convert1;
                    if (coreB.open) {
                        return convert1;
                    }
                }else if (isconv(convert1)){
                    //Log.info("con1");
                    SuperBuild conv = ((conveyorBelt.conveyorBeltB) convert1).getCore(findBuilds);
                    if (conv!=null){
                        return conv;
                    }
                    //Log.info("concon2");
                }
            }
            if (convert2!=null&&convert2.build.team()==build.team()){
                if (isCore(convert2)){
                    transportCore.transportCoreB coreB = (transportCore.transportCoreB) convert2;
                    if (coreB.open) {
                        return convert2;
                    }
                }else if (isconv(convert2)){
                    SuperBuild conv = ((conveyorBelt.conveyorBeltB) convert2).getCore(findBuilds);
                    if (conv!=null){
                        return conv;
                    }
                    //Log.info("2");
                }
            }

            if (convert3!=null&&convert3.build.team()==build.team()){
                if (isCore(convert3)){
                    transportCore.transportCoreB coreB = (transportCore.transportCoreB) convert3;
                    if (coreB.open) {
                        return convert3;
                    }
                }else if (isconv(convert3)){
                    SuperBuild conv = ((conveyorBelt.conveyorBeltB) convert3).getCore(findBuilds);
                    if (conv!=null){
                        return conv;
                    }
                }
            }
            if (convert4!=null&&convert4.build.team()==build.team()){
                if (isCore(convert4)){
                    transportCore.transportCoreB coreB = (transportCore.transportCoreB) convert4;
                    if (coreB.open) {
                        return convert4;
                    }
                }else if (isconv(convert4)){
                    SuperBuild conv = ((conveyorBelt.conveyorBeltB) convert4).getCore(findBuilds);
                    if (conv!=null){
                        return conv;
                    }
                }
            }
            return null;

        }
        public SuperBuild getthisBuild(int offsetX,int offsetY){
            return WorldTable.getSuperBuilder(Math.round((this.build.x()/8)+offsetX),Math.round((this.build.y()/8)+offsetY));
        }
        public boolean isCore(SuperBuild b){
            return b.getClass() == transportCore.transportCoreB.class;
        }
        public boolean isconv(SuperBuild b){
            return b instanceof conveyorBeltB;
        }

    }
}
