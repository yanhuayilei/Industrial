
package Industrial.Block;

import Industrial.Block.loadBlock.structure.loadBlock.conv.ItemStorage;
import Industrial.Block.loadBlock.structure.loadBlock.conv.Itemconveyor;
import Industrial.item.Item;
import Industrial.table.Buildmode;
import Industrial.table.Menudispose;
import Industrial.table.PlayerInfo;
import Industrial.table.WorldTable;
import Industrial.time.ifBuilding;
import Industrial.update.Builddead;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import arc.Events;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.gen.Player;
import mindustry.ui.Menus;

public class SuperBuild implements Runnable {
    public static Menus.MenuListener listener = (player, option) -> {
        if (PlayerInfo.GetPlayerInfo(player)!=null&&PlayerInfo.GetPlayerInfo(player).mode == Buildmode.newEdition){
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
    public final ItemStorage store = new ItemStorage();
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
            alltimeTask.add(build);

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
    public boolean acceptItem(Item item, int number) {
        return block.hasitem&&(store.get(item)+number)<=block.maxItem&&(store.get(item)+number)>=0&&item!=null;
    }
    public void addItem(Item item, int number) {
        if (acceptItem(item,number)){
            store.add(item,number);
        }
    }
    public boolean addFwItem(Item item,int number,int revolve){
        ArrayList<Building> allBuild = neighborhood();
        //if (allBuild.size()==1){}
        if (allBuild.size()==0)
            return false;
        if (allBuild.size()<=revolve){
            return false;
        }
        SuperBuild b = WorldTable.getSuperBuilder(allBuild.get(revolve++));
        if (b!=null){
            if (b.acceptItem(item,number)){
                b.addItem(item,number);
                return true;
            }
        }
        return addFwItem(item,number,revolve);
    }
    public void clickProcess(PlayerInfo player,int option){

    }
    public ArrayList<Building> neighborhood(){
        int hornX,hornY,headX,headY;
        int[] args = getrange();
        hornX = args[0];
        hornY = args[1];
        headX=hornX+build.block().size+1;
        headY=hornY+build.block().size+1;
        ArrayList<Building> all = new ArrayList<>();
        int dqX = hornX,dqY = hornY;
        while (dqX<=headX||dqY<=headY){
            Building building = Vars.world.build(dqX++,dqY);
            if (!all.contains(building)&&building!=null) {
                all.add(building);
                //Log.info(building);
            }
            if (dqX>=(headX+1)){
                if (dqY==headY&&dqX>=headX){
                    break;
                }
                dqX = hornX;
                dqY++;
            }
        }

        /*int x1=hornX,y1=hornY+build.block().size+1;
        int x2=hornX+build.block().size+1,y2 = hornY + build.block.size+1;
        int x3=hornX,y3=hornY;
        int x4=hornX+build.block().size+1,y4=hornY;
        all.remove(Vars.world.build(x1,y1));
        all.remove(Vars.world.build(x2,y2));
        all.remove(Vars.world.build(x3,y3));
        all.remove(Vars.world.build(x4,y4));
        all.remove(build);*/
        //Log.info("end");
        //Log.info("头x:"+headX+",y:"+headY);
        //Log.info("角x"+hornX+",y"+hornY);
        all.remove(build);
        return all;
    }
    private int[] getrange(){
        if (build.block().size%2==0){
            int x1 = Math.round(build.x()/8)-1-(build.block().size/2);
            int y1 = Math.round(build.y()/8)-1-(build.block().size/2);
            return new int[]{x1,y1};
        }else {
            int x1 = Math.round(build.x()/8)-((build.block().size+1)/2);
            int y1 = Math.round(build.y()/8)-((build.block().size+1)/2);
            return new int[] {x1,y1};
        }
    }
}
