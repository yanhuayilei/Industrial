package Industrial.Block.loadBlock.structure.loadBlock.Net;

import Industrial.Block.SuperBlock;
import Industrial.Block.SuperBuild;
import Industrial.table.WorldTable;
import arc.struct.Seq;
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
        public Seq<SuperBuild> allconvert = new Seq<>();
        public SuperBuild getCore(Set<SuperBuild> findBuilds){
            allconvert = new Seq<>();
            neighborhood().forEach(i->{
                //Log.info(i);
                if (i==null)
                    return;
                SuperBuild build1 = WorldTable.getSuperBuilder(Math.round(i.x()/8),Math.round(i.y()/8));
                //Log.info(build1+"：："+i);
                allconvert.add(build1);
            });
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
            SuperBuild[] cores = {null};
            allconvert.each(i->{
                if (cores[0]!=null)
                    return;
                cores[0] = singleProcessing(i,findBuilds);
            });
            //Log.info(cores[0]);
            return cores[0];

        }
        public SuperBuild singleProcessing(SuperBuild builder,Set<SuperBuild> find){
            if (builder!=null&&builder.build.team()==build.team()){//Log.info(builder);
                if (isCore(builder)){
                    transportCore.transportCoreB coreB = (transportCore.transportCoreB) builder;
                    if (coreB.open) {
                        return coreB;
                    }
                }else if (isconv(builder)){
                    SuperBuild conv = ((conveyorBelt.conveyorBeltB) builder).getCore(find);
                    return conv;
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
