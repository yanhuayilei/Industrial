package Industrial.Block.loadBlock.structure.loadBlock.Net;

import Industrial.Block.SuperBlock;
import Industrial.Block.SuperBuild;
import Industrial.load.setupItems;
import Industrial.table.Menudispose;
import Industrial.table.PlayerInfo;
import Industrial.table.WorldTable;
import Industrial.time.ifBuilding;
import arc.struct.ObjectMap;
import arc.struct.Seq;
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



        public InpNodeB(Building build, SuperBlock block) {
            super(build, block);
            SuperBuild.addTask(task1);

        }

        public ifBuilding task1 = new ifBuilding(()->{
            //Log.info(core);
            if (core==null||channel==0)
                return;
            Building b1 = build;
            if (b1!=null){
                if (b1.items!=null){
                    aPairOfIncompatibles pair = core.allpair.get(channel);
                    if (pair.out!=null) {
                        Item item = b1.items().first();
                        if (item==null)
                            return;
                        //Log.info("1");
                        if (acceptItem(this,item,-10)) {

                            //Log.info("2");
                            if (pair.out.acceptItem(this,item, 10)) {
                                //Log.info("3");
                                pair.out.addItem(this,item, 10);
                                addItem(this,item,-10);
                            }
                        }else if (b1.items.get(item)!=0){
                            if (pair.out.acceptItem(this,item,b1.items().get(item))){
                                pair.out.addItem(this,item,b1.items().get(item));
                                addItem(this, item,-b1.items.get(item));
                            }
                        }
                    }
                }
            }
        },1,this);


        @Override
        public void run() {
            if (core!=null&&channel!=0){
                core.allpair.get(channel).inp = this;
            }
        }


        @Override
        public void clickProcess(PlayerInfo player, int option) {
            if (core==null)
                return;
            if (option==0){//减
                for (int i = channel;i>0;i--) {
                    if (i==0) {
                        player.player.sendMessage("没找到");
                        continue;
                    }
                    aPairOfIncompatibles q = core.allpair.get(i);
                    if (q.inp==null){
                        channel = i;
                        player.player.sendMessage("切换频道:"+i);
                        break;
                    }
                }
            }else if (option == 1) {//加
                for (int i = channel;i<10;i++){
                    if (i==0)
                        continue;
                    aPairOfIncompatibles q = core.allpair.get(i);
                    if (q.inp==null){
                        channel = i;
                        player.player.sendMessage("切换频道:"+i);
                        break;
                    }
                }
            }
        }

        @Override
        public void click(PlayerInfo player) {
            Menudispose menudispose = new Menudispose<>(this);
            addMenu(player,menudispose);
            Call.menu(player.player.con(),SuperBuild.menuid,"频道选择","当前频道是:"+channel,new String[][]{{"<",">"}});
        }
    }
}
