
package Industrial.table;

import Industrial.time.Task;
import Industrial.time.Timer;
import Industrial.time.ifBuilding;
import Industrial.update.updateTask;
import arc.Events;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import arc.struct.Seq;
import mindustry.game.EventType;
import mindustry.gen.Groups;
import mindustry.gen.Player;

public class PlayerInfo{
    private static boolean isEvents = false;
    public static final ArrayList<PlayerInfo> ListInfo = new ArrayList();
    public Buildmode mode;
    public final Player player;
    private Seq<Menudispose> warehouse = new Seq<>();

    public static Player GetPlayer(PlayerInfo player) {
        return ListInfo.contains(player) ? player.player : null;
    }

    public static PlayerInfo GetPlayerInfo(Player player) {
        PlayerInfo[] playerInfo = new PlayerInfo[]{null};
        ListInfo.forEach((i) -> {
            if (i.player == player) {
                playerInfo[0] = i;
            }
        });
        return playerInfo[0];
    }
    public PlayerInfo(Player player,Seq<Menudispose> dispose){
        this(player);
        this.warehouse = dispose;
    }
    public Menudispose pull(){
        if (this.warehouse.size==0){
            return null;
        }else {
            Menudispose menudispose = this.warehouse.get(this.warehouse.size-1);
            this.warehouse.remove(menudispose);
            return menudispose;
        }
    }
    public void push(Menudispose dispose){
        this.warehouse.add(dispose);
    }
    public static void addEvents() {
        if (!isEvents) {
            isEvents = true;
            Events.on(EventType.PlayerConnectionConfirmed.class, (i) -> {
                new PlayerInfo(i.player);
                if (ListInfo.size() != Groups.player.size()) {
                    ListInfo.clear();
                    Groups.player.each(player1 -> {
                        if (PlayerInfo.GetPlayerInfo(player1)!=null){
                            new PlayerInfo(player1,PlayerInfo.GetPlayerInfo(player1).warehouse);
                        }else {
                            new PlayerInfo(player1);
                        }
                    });
                }
            });
            Events.on(EventType.PlayerLeave.class, (i) -> {
                //updateTask.task.remove(GetPlayerInfo(i.player));
                ListInfo.remove(GetPlayerInfo(i.player));

                if (ListInfo.size() != Groups.player.size()) {
                    ListInfo.clear();
                    Groups.player.each(player1 -> {
                        if (PlayerInfo.GetPlayerInfo(player1)!=null){
                            new PlayerInfo(player1,PlayerInfo.GetPlayerInfo(player1).warehouse);
                        }else {
                            new PlayerInfo(player1);
                        }
                    });
                }
            });
        }

    }

    public PlayerInfo(Player player) {
        this.mode = Buildmode.original;
        this.player = player;
        ListInfo.add(this);
        //updateTask.task.add(this);
    }
}

