//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Industrial;

import Industrial.Block.SuperBuild;
import Industrial.Block.loadBlock.structure.loadBlock.Net.ItemNetnode;
import Industrial.Block.loadBlock.structure.loadBlock.Net.conveyorBelt;
import Industrial.Block.loadBlock.structure.loadBlock.conv.container;
import Industrial.Block.loadBlock.structure.structure;
import Industrial.item.Item;
import Industrial.item.Items;
import Industrial.table.Buildmode;
import Industrial.table.PlayerInfo;
import Industrial.table.WorldTable;
import Industrial.time.Task;
import Industrial.time.Timer;
import arc.util.Log;
import mindustry.Vars;
import mindustry.gen.Call;
import mindustry.gen.Groups;

public class PlayerAimBlock {
    private static boolean isAdd = false;

    public PlayerAimBlock() {
        if (!isAdd) {
            isAdd = true;
            Timer.allTime.add(new Task(1, () -> {
                Groups.player.each((player) -> {
                    if (player != null && PlayerInfo.GetPlayerInfo(player) != null) {
                        String[] text = {"------信息面板------\n"};
                        text[0] = text[0] + "玩家:" + player.name() + "[]\n\n";
                        text[0] = text[0] + "当前模式:" + (PlayerInfo.GetPlayerInfo(player).mode == Buildmode.original ? "原版模式" : "插件模式") + "\n";
                        SuperBuild build = WorldTable.getSuperBuilder(Math.round(player.mouseX / 8.0F), Math.round(player.mouseY / 8.0F));
                        if (build != null) {
                            text[0] = text[0] + "插件方块:" + build.block.name+"\n";
                        }
                        if (build instanceof ItemNetnode.ItemNetnodeB){
                            //Log.info(build);
                            text[0] = text[0] + "是否连接网络:";
                            text[0] +=(((ItemNetnode.ItemNetnodeB)build).core==null?"否\n":"是\n")+"";
                            text[0] +="频道："+((ItemNetnode.ItemNetnodeB)build).channel+"\n";
                        }
                        if (build!=null&&build.block.hasitem){
                            text[0] +="物品：";
                            Items.getSeq().each(i->{
                                text[0] +=i.getName()+":"+build.store.get(i)+"\n";
                            });
                            //text[0] +="物品:"+build.store.get(build.store.first());
                        }
                        if (build instanceof structure.structurecB) {
                            text[0] = addItemsText(text[0], build);
                        }

                        Call.infoPopup(player.con(), text[0], 1.05F, 10, 400, 0, 0, 0);
                    }
                });
            }));
        }

    }

    public static String addItemsText(String text, SuperBuild build) {
        text = text + "存储物品:";
        String[] i = new String[]{text};
        Vars.content.items().each((item) -> {
            int num = ((structure.structurecB)build).storage.get(item);
            if (num != 0) {
                i[0] = i[0] + item.localizedName + ":" + num;
            }
        });
        i[0] = i[0] + "\n";
        return i[0];
    }
}
