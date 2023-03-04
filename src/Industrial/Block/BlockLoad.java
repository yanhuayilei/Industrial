
package Industrial.Block;

import Industrial.Block.loadBlock.structure.loadBlock.Net.*;
import Industrial.Block.loadBlock.structure.loadBlock.conv.Itemconveyor;
import Industrial.Block.loadBlock.structure.loadBlock.conv.container;
import Industrial.Block.loadBlock.structure.loadBlock.fort.Turret;
import Industrial.Block.loadBlock.structure.structure;
import Industrial.table.Buildmode;
import Industrial.table.Menudispose;
import Industrial.table.PlayerInfo;
import Industrial.table.WorldTable;
import Industrial.update.Builddead;
import arc.Events;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Call;
import mindustry.ui.Menus;

public class BlockLoad {
    //建造菜单

    public final static Menus.MenuListener listener = (player, option) -> {
        if (option<0)
            return;
        if (PlayerInfo.GetPlayerInfo(player)==null)
            return;
        Menudispose<EventType.BlockBuildEndEvent> menudispose = PlayerInfo.GetPlayerInfo(player).pull(BlockLoad.class);
        if (menudispose==null||menudispose.content==null||menudispose.content.tile==null|| Vars.world.build(menudispose.content.tile.x,menudispose.content.tile.y)!=menudispose.content.tile.build)
            return;
        Seq<SuperBlock> allblock = Blocks.getAllBlock(menudispose.content.tile.block());
        if (allblock == null || option > allblock.size)
            return;
        EventType.BlockBuildEndEvent i = menudispose.content;


        SuperBuild build = allblock.get(option).create(i.tile.build);
        WorldTable.setSuperBuilder(Math.round(i.tile.build.x() / 8.0F), Math.round(i.tile.build.y() / 8.0F), build);
        Builddead builder = new Builddead(build, build, SuperBuild.allbuildupdate.size());
        build.update = builder;
        SuperBuild.allbuildupdate.add(builder);
        i.unit.getPlayer().sendMessage("[green]你建造了" + allblock.get(option).name);

        //Log.info(option);
    };
    public final static int menuid = Menus.registerMenu(listener);
    public static void load() {
        //new Test();
        new structure(mindustry.content.Blocks.copperWall,"test"){{
            hasitem =  true;

        }};
        new OutNode(mindustry.content.Blocks.container,"输出节点"){{
            hasitem = true;
            maxItem = 300;
        }};
        new InpNode(mindustry.content.Blocks.container,"输入节点"){{
            hasitem = true;
            maxItem = 300;
        }};
        new transportCore(mindustry.content.Blocks.thoriumWallLarge,"控制器");
        //new ItemNetnode(mindustry.content.Blocks.titaniumWall,"测试节点");
        new conveyorBelt(mindustry.content.Blocks.copperWall,"传输管道");
        new ItemNetnode(mindustry.content.Blocks.copperWallLarge,"TestNet");
        new Itemconveyor(mindustry.content.Blocks.titaniumConveyor,"传输带"){{
            maxItem = 10;
            hasitem = true;
        }};
        new container(mindustry.content.Blocks.container,"容器"){{
            maxItem = 300;
            hasitem = true;
        }};
        new Turret(mindustry.content.Blocks.container,"炮台");

//        new structure(mindustry.content.Blocks.copperWall,"TesT");
//        new structure(mindustry.content.Blocks.copperWall,"TT");
//        new structure(mindustry.content.Blocks.copperWall,"TTT");
//        new structure(mindustry.content.Blocks.copperWall,"TTTT");
//        new structure(mindustry.content.Blocks.copperWall,"TTTTT");
        //Log.info(Jval.read("[{\"hi\":10}]"));
    }

    public BlockLoad() {
        load();
        Events.on(EventType.BlockBuildEndEvent.class, (i) -> {
            if (!i.breaking && i.unit != null && i.unit.isPlayer() && PlayerInfo.GetPlayerInfo(i.unit.getPlayer()) != null) {
                if (PlayerInfo.GetPlayerInfo(i.unit.getPlayer()).mode != Buildmode.original) {
                    //SuperBlock block = Blocks.getBlock(i.tile.block());
                    Seq<SuperBlock> allBlock = Blocks.getAllBlock(i.tile.block());
                    if (allBlock.size==0)
                        return;

                    Call.menu(i.unit.getPlayer().con(),menuid,"建造菜单","选择对应方块",partition(getName(allBlock.toArray()),0));
                    Menudispose di = new Menudispose<EventType.BlockBuildEndEvent>(i);
                    PlayerInfo.GetPlayerInfo(i.unit.getPlayer()).push(di, BlockLoad.class);
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
    public static String[] getName(Object[] block){
        String[] name = new String[block.length];
        for (int i = 0; i < block.length; i++) {
            if (block[i] instanceof SuperBlock)
            name[i] = ((SuperBlock)block[i]).name;
        }
        return name;
    }
}
