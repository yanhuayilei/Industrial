package Industrial.Block.loadBlock.structure.loadBlock.Net;

import Industrial.Block.SuperBlock;
import Industrial.Block.SuperBuild;
import Industrial.load.setupItems;
import Industrial.table.Menudispose;
import Industrial.table.PlayerInfo;
import arc.util.Log;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.gen.Call;
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
            if (core!=null&&channel!=0){
                core.allpair.get(channel).out = this;
            }
        }

        public boolean acceptOutput(Item item){
            if (item!=null){
                if (build!=null){
                    return build.items != null && build.acceptItem(build, item) && build.items.get(item) < build.block().itemCapacity;
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
                    if (build!=null){
                        if (build.items!=null&&build.acceptItem(build,item)&&build.items.get(item)<build.block().itemCapacity){
                            setupItems.addItem(build,item,amount);
                        }
                    }
                }
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
                    if (q.out==null){
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
                    if (q.out==null){
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
