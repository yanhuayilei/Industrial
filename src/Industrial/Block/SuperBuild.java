
package Industrial.Block;

import Industrial.table.Menudispose;
import Industrial.table.PlayerInfo;
import Industrial.time.ifBuilding;
import Industrial.update.Builddead;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import arc.Events;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.gen.Player;
import mindustry.ui.Menus;

public class SuperBuild implements Runnable {
    public static Menus.MenuListener listener = (player, option) -> {
        if (PlayerInfo.GetPlayerInfo(player)!=null){
            PlayerInfo playerInfo = PlayerInfo.GetPlayerInfo(player);
            Menudispose<SuperBuild> menudispose = playerInfo.pull(SuperBuild.class);
            if (menudispose==null)
                return;
            if (menudispose.content==null)
                return;
            if (!menudispose.content.dead&&playerInfo!=null)
            menudispose.content.clickProcess(playerInfo,option);
        }
    };
    static{
        Events.on(EventType.MenuOptionChooseEvent.class,listener->{

        });
    }
    public static final int menuid = Menus.registerMenu(listener);
    public static final Set<ifBuilding> alltimeTask = new HashSet();
    public static final ArrayList<Builddead> allbuildupdate = new ArrayList();
    public final Building build;
    public Builddead update;
    public boolean dead = false;
    public final SuperBlock block;
    public static void addMenu(PlayerInfo playerInfo,Menudispose menudispose){
        playerInfo.push(menudispose,SuperBuild.class);
    }

    public static void addTask(ifBuilding build) {
        if (!build.build.dead) {
            if (!alltimeTask.contains(build)) {
                alltimeTask.add(build);
            }

        }
    }

    public SuperBuild(Building build, SuperBlock block) {
        this.build = build;
        this.block = block;
    }

    public void run() {
    }

    public void dead() {
    }
    public void click(PlayerInfo player){

    }
    public void clickProcess(PlayerInfo player,int option){

    }
}
