package Industrial.json;

import Industrial.Block.SuperBlock;
import Industrial.Block.SuperBuild;
import Industrial.item.Item;
import Industrial.load.setupItems;
import Industrial.time.ifBuilding;
import arc.util.Log;
import mindustry.gen.Building;
import mindustry.world.Block;

public class factory extends SuperBlock {
    public mindustry.type.Item out1 = null;
    public int inpam = 1;
    public int outam = 1;
    public mindustry.type.Item inp1 = null;
    public Item out = null;
    public Item inp = null;
    public boolean outisoriginal = false;
    public boolean inpisoriginal = false;
    public int time = 1;

    public factory(Block prototype, String name) {
        super(prototype, name);
    }

    @Override
    public SuperBuild create(Building building) {
        return new factoryB(building,this);
    }

    static class factoryB extends SuperBuild{
        public factory f;

        public factoryB(Building build, SuperBlock block) {
            super(build, block);
            f = (factory) block;
            tk = new ifBuilding(()->{
                if (f.inpisoriginal){
                    if (f.inp1==null)
                        return;
                    //Log.info(1+":"+f.inp1);
                    if (build.items!=null&&build.items.get(f.inp1)>=f.inpam){
                        if (f.outisoriginal){
                            if ((build.items.get(f.out1)+f.outam)<=block.maxItem) {
                                setupItems.addItem(build,f.inp1,-f.inpam);//jian掉输入
                                setupItems.addItem(build, f.out1, f.outam);//加输出
                            }
                        }else {
                            if (acceptItem(this,f.out,f.outam)) {
                                addItem(this,f.out, f.outam);
                                setupItems.addItem(build,f.inp1,-f.inpam);
                            }
                        }
                    }
                }else {
                    if (f.inp==null)
                        return;
                    //Log.info(2+":"+f.inp);
                    if (block.hasitem&&store.get(f.inp)>=f.inpam){
                        //store.add(f.inp,-f.inpam);
                        if (f.outisoriginal){
                            if ((setupItems.getItem(build,f.out1)+f.outam)<=block.maxItem) {
                                setupItems.addItem(build, f.out1, f.outam);
                                addItem(this,f.inp, -f.inpam);
                            }
                        }else {
                            if (acceptItem(this,f.out,f.outam)) {
                                addItem(this,f.out, f.outam);
                                addItem(this,f.inp, -f.inpam);
                            }
                        }
                    }
                }
            },f.time,this);

            SuperBuild.addTask(tk);
        }
        public ifBuilding tk;

        @Override
        public void run() {

        }
    }

}
