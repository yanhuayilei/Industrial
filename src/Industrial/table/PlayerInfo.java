
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

import arc.struct.ObjectMap;
import arc.struct.Seq;
import mindustry.game.EventType;
import mindustry.gen.Groups;
import mindustry.gen.Player;

public class PlayerInfo{
    private static boolean isEvents = false;
    public static final ArrayList<PlayerInfo> ListInfo = new ArrayList();
    public Buildmode mode;
    public final Player player;
    private ObjectMap<Class,Seq<Menudispose>> warehouse = new ObjectMap<>();

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
    public PlayerInfo(Player player,ObjectMap dispose){
        this(player);
        this.warehouse = dispose;
    }
    public Menudispose pull(Class cla){
        if (!this.warehouse.containsKey(cla))
            this.warehouse.put(cla,new Seq<>());
        if (this.warehouse.get(cla).size==0){
            return null;
        }else {
            Menudispose menudispose = this.warehouse.get(cla).get(this.warehouse.get(cla).size-1);
            this.warehouse.get(cla).remove(menudispose);
            return menudispose;
        }
    }
    public void push(Menudispose dispose){
        if (!this.warehouse.containsKey(dispose.content.getClass()))
            this.warehouse.put(dispose.content.getClass(),new Seq<>());
        this.warehouse.get(dispose.content.getClass()).add(dispose);
    }
    public void push(Menudispose menudispose,Class addClass){
        if (!this.warehouse.containsKey(addClass))
            this.warehouse.put(addClass,new Seq<>());
        this.warehouse.get(addClass).add(menudispose);
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

