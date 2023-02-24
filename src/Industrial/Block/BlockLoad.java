
package Industrial.Block;

import Industrial.Block.loadBlock.structure.loadBlock.Net.*;
import Industrial.Block.loadBlock.structure.structure;
import Industrial.table.Buildmode;
import Industrial.table.PlayerInfo;
import Industrial.table.WorldTable;
import Industrial.update.Builddead;
import arc.Events;
import mindustry.game.EventType;

public class BlockLoad {
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
            if (!i.breaking && i.unit != null && i.unit.isPlayer() && i != null && PlayerInfo.GetPlayerInfo(i.unit.getPlayer()) != null) {
                if (PlayerInfo.GetPlayerInfo(i.unit.getPlayer()).mode != Buildmode.original) {
                    SuperBlock block = Blocks.getBlock(i.tile.block());
                    if (block != null) {
                        SuperBuild build = block.create(i.tile.build);
                        WorldTable.setSuperBuilder(i.tile.build.x() / 8.0F, i.tile.build.y() / 8.0F, build);
                        Builddead builder = new Builddead(build, build, SuperBuild.allbuildupdate.size());
                        build.update = builder;
                        SuperBuild.allbuildupdate.add(builder);
                        i.unit.getPlayer().sendMessage("[green]你建造了" + block.name);

                    }
                }
            }
        });
    }
}
