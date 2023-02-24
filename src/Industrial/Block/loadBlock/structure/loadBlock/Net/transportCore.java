package Industrial.Block.loadBlock.structure.loadBlock.Net;

import Industrial.Block.SuperBlock;
import Industrial.Block.SuperBuild;
import Industrial.Block.loadBlock.structure.loadBlock.Net.conveyorBelt;
import arc.struct.ObjectMap;
import mindustry.gen.Building;
import mindustry.world.Block;

public class transportCore extends SuperBlock {

    public transportCore(Block prototype, String name) {
        super(prototype, name);
    }

    @Override
    public SuperBuild create(Building building) {
        return new transportCoreB(building, this);
    }

    public static class transportCoreB extends conveyorBelt.conveyorBeltB {
        public boolean open = true;
        public ObjectMap<Integer,aPairOfIncompatibles> allpair = new ObjectMap<>();
        //public static ObjectMap<Team>
        public transportCoreB(Building build, SuperBlock block) {
            super(build, block);
            for (int i = 1;i<10;i++){
                allpair.put(i,new aPairOfIncompatibles());
            }
        }

        @Override
        public void run() {
            for (int i = 1;i<10;i++){
                aPairOfIncompatibles part = allpair.get(i);
                if (part.inp!=null&&i!=part.inp.channel){
                    part.inp = null;
                }
                if (part.out!=null&&i!=part.out.channel){
                    part.out = null;
                }
            }
            /*Set<SuperBuild> is = new HashSet<>();
            is.add(this);
            SuperBuild build = getCore(is);
            if (build==null) {
                open = true;
            }else {
                open = false;
                transportCoreB core = (transportCoreB) build;
                core.open = false;
            }*/
        }

        @Override
        public void dead() {
            open = false;
        }
    }
}
