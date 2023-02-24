
package Industrial.Block;

import Industrial.Block.loadBlock.structure.loadBlock.Net.*;
import Industrial.Block.loadBlock.structure.structure;
import Industrial.table.Buildmode;
import Industrial.table.PlayerInfo;
import Industrial.table.WorldTable;
import Industrial.update.Builddead;
import arc.Events;
import arc.struct.Seq;
import mindustry.game.EventType;
import mindustry.gen.Call;
import mindustry.ui.Menus;

public class BlockLoad {
    //建造菜单

    public final static Menus.MenuListener listener = (player, option) -> {

    };
    public final static int menuid = Menus.registerMenu(listener);
    public static void load() {
        //new Test();
        new OutNode(mindustry.content.Blocks.titaniumWall,"输出节点");
        new InpNode(mindustry.content.Blocks.thoriumWall,"输入节点");
        new transportCore(mindustry.content.Blocks.thoriumWallLarge,"控制器");
        //new ItemNetnode(mindustry.content.Blocks.titaniumWall,"测试节点");
        new conveyorBelt(mindustry.content.Blocks.copperWall,"传输管道");
        //new structure();
    }

    public BlockLoad() {
        load();
        Events.on(EventType.BlockBuildEndEvent.class, (i) -> {
            if (!i.breaking && i.unit != null && i.unit.isPlayer() && PlayerInfo.GetPlayerInfo(i.unit.getPlayer()) != null) {
                if (PlayerInfo.GetPlayerInfo(i.unit.getPlayer()).mode != Buildmode.original) {
                    SuperBlock block = Blocks.getBlock(i.tile.block());
                    Seq<SuperBlock> allBlock = Blocks.getAllBlock(i.tile.block());
                    if (allBlock.size==0)
                        return;

                    Call.menu(i.unit.getPlayer().con(),menuid,"建造菜单","选择对应方块",partition(getName(allBlock.toArray()),0));
                    /*if (block != null) {
                        SuperBuild build = block.create(i.tile.build);
                        WorldTable.setSuperBuilder(i.tile.build.x() / 8.0F, i.tile.build.y() / 8.0F, build);
                        Builddead builder = new Builddead(build, build, SuperBuild.allbuildupdate.size());
                        build.update = builder;
                        SuperBuild.allbuildupdate.add(builder);
                        i.unit.getPlayer().sendMessage("[green]你建造了" + block.name);

                    }*/
                }
            }
        });
    }
    public static String[][] partition(String[] strings,int number){
        if (number==0)
            number = 4;
        String[][] result = new String[(strings.length/number)+1][number];
        int ii = 0;
        int i = 0;
        for (String string:strings){
            result[ii][i++] = string;
            if (i%number==0){
                ii++;
                i = 0;
            }
        }
        return result;
    }
    public static String[] getName(SuperBlock[] block){
        String[] name = new String[block.length];
        for (int i = 0; i < block.length; i++) {
            name[i] = block[i].name;
        }
        return name;
    }
}
