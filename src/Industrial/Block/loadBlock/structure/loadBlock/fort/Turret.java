package Industrial.Block.loadBlock.structure.loadBlock.fort;

import Industrial.Block.SuperBlock;
import Industrial.Block.SuperBuild;
import Industrial.item.Item;
import Industrial.time.ifBuilding;
import arc.Events;
import arc.math.geom.Rect;
import arc.struct.IntSeq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.entities.UnitSorts;
import mindustry.entities.Units;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Call;
import mindustry.gen.Player;
import mindustry.world.Block;

import java.awt.*;

public class Turret extends SuperBlock {
    public Item cannonball = null;
    public int deplete = 1;
    public float range = 10f;

    public Turret(Block prototype, String name) {
        super(prototype, name);
        hasitem = true;
    }

    @Override
    public SuperBuild create(Building building) {
        return new TurretB(building,this);
    }

    static class TurretB extends SuperBuild{
        public Turret superBlock;
        public TurretB(Building build, SuperBlock block) {
            super(build, block);
            Call.setTeam(build,Team.crux);
            superBlock = (Turret) block;
            ifBuilding task = new ifBuilding(()->{
                Boolean[] islaunch = {false};
                //Log.info("1...");
                //Rect rect = new Rect();
                //Units.bestEnemy(build.team(),build.x(),build.y(),superBlock.range*8,unit -> {
                //    Log.info(unit);
                //    return false;
                //},UnitSorts.closest)
                Units.bestTarget(build.team(),build.x(),build.y(),superBlock.range*8,unit -> {
                    unit.health = 0;
                    islaunch[0] = true;
                    //Log.info(1+unit.toString());
                    //Call.buildHealthUpdate();
                    //IntSeq seq = new IntSeq();
                    //seq.set(unit,Float.floatToRawIntBits(10));
                    //Call.buildHealthUpdate(seq);
                    //Player.create().unit().he
                  return false;
                },building -> {
                    if (islaunch[0])
                        return false;
                    islaunch[0] = true;
                    IntSeq seq = new IntSeq();
                    seq.add(building.pos(),0);
                    building.tile().removeNet();
                    //Call.setTile(building.tile, Blocks.air,build.team(),0);
                    //Call.buildHealthUpdate(seq);
                    //Log.info(2+building.toString());
                    return false;
                },UnitSorts.closest);
            },1,this);
            SuperBuild.addTask(task);
        }

        @Override
        public void run() {

        }
    }
}
