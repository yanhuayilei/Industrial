package Industrial.load;

import Industrial.PlayerAimBlock;
import Industrial.Block.BlockLoad;
import Industrial.Block.SuperBuild;
import Industrial.item.ItemLoad;
import Industrial.json.Jsonfactory;
import Industrial.table.Buildmode;
import Industrial.table.PlayerInfo;
import Industrial.table.WorldTable;
import Industrial.time.Timer;
import Industrial.update.Builddead;
import Industrial.update.updateTask;
import arc.Events;
import arc.util.CommandHandler;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.mod.Plugin;

public class Main extends Plugin {
    public static updateTask update;

    public Main() {
    }

    public void init() {
        Jsonfactory.load();
        ItemLoad.load();
        new BlockLoad();
        new PlayerAimBlock();
        new WorldTable();
        Timer timer = new Timer();
        Log.info("loading...");
        PlayerInfo.addEvents();
        update = new updateTask();
        updateTask.task.add(() -> {
            Object[] var0 = SuperBuild.allbuildupdate.toArray();
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                Object obj = var0[var2];
                Builddead update = (Builddead)obj;
                if (!update.b.dead) {
                    update.run();
                } else {
                    SuperBuild.allbuildupdate.remove(update);
                }
            }

        });
        //Vars.world.build(0).back();
        Events.on(EventType.TapEvent.class,i->{
            if (i==null||i.tile==null||i.tile.build==null)
                return;
            SuperBuild superBuild = WorldTable.getSuperBuilder(Math.round(i.tile.build.x()/8),Math.round(i.tile.build.y()/8));
            if (superBuild!=null){
                superBuild.click(PlayerInfo.GetPlayerInfo(i.player));
                //Log.info("test2");
            }
            //Log.info("test1");
        });
        update.start();
        timer.start();
    }

    public void registerClientCommands(CommandHandler handler) {
        handler.register("s", "建筑模式切换", (CommandHandler.CommandRunner<Player>) (args, parameter) -> {
            if (PlayerInfo.GetPlayerInfo(parameter).mode == Buildmode.original) {
                PlayerInfo.GetPlayerInfo(parameter).mode = Buildmode.newEdition;
                parameter.sendMessage("[green]已切换建筑模式：插件");
            } else {
                PlayerInfo.GetPlayerInfo(parameter).mode = Buildmode.original;
                parameter.sendMessage("[green]已切换建筑模式：原版");
            }
        });
        handler.register("debug", "debug",  (CommandHandler.CommandRunner<Player>)  (args, player) -> {
            player.sendMessage(Timer.allTime.size + "::" + SuperBuild.alltimeTask.size() + "::" + PlayerInfo.ListInfo.size() + "::" + Groups.player.size());
        });
    }
}
