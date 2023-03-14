
package Industrial.table;

import Industrial.Block.Blocks;
import Industrial.Block.SuperBlock;
import Industrial.Block.SuperBuild;
import Industrial.json.Jsonfactory;
import arc.Events;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Building;

public class WorldTable {
    private static boolean isEvent = false;
    private static SuperBuild[][] allBuild = new SuperBuild[0][0];

    public WorldTable() {
        if (!isEvent) {
            isEvent = true;
            Events.on(EventType.ResetEvent.class, (i) -> {
                SuperBuild.allbuildupdate.clear();
                SuperBuild.alltimeTask.clear();
                SuperBuild[][] var1 = allBuild;
                int var2 = var1.length;

                for(int var3 = 0; var3 < var2; ++var3) {
                    SuperBuild[] y = var1[var3];
                    SuperBuild[] var5 = y;
                    int var6 = y.length;

                    for(int var7 = 0; var7 < var6; ++var7) {
                        SuperBuild x = var5[var7];
                        if (x != null) {
                            x.dead();
                            x.dead = true;
                        }
                    }
                }

                Log.info("重置所有更新");
                for (SuperBlock build:Jsonfactory.jsonBlock){
                    Blocks.remove(build);
                }
                Jsonfactory.jsonBlock.clear();
            });
            Events.on(EventType.WorldLoadEndEvent.class, (i) -> {
                allBuild = new SuperBuild[Vars.world.width()][Vars.world.height()];
                Log.info("h:" + Vars.world.height() + "w:" + Vars.world.width());
            });
        }

    }
    public static SuperBuild getSuperBuilder(Building building){
        if (building==null)
            return null;
        return getSuperBuilder(Math.round(building.x()/8),Math.round(building.y()/8));
    }

    public static SuperBuild getSuperBuilder(int x, int y) {
        Building building = Vars.world.build(x,y);
        if(building==null)
            return null;
        int xx = Math.round(building.x()/8),yy = Math.round(building.y()/8);
        return allBuild.length == 0 ? null : allBuild[xx][yy];
    }

    public static void setSuperBuilder(int x, int y, SuperBuild build) {
        if (allBuild.length != 0) {
            allBuild[x][y] = build;
        }
    }

    public static void setSuperBuilder(float x, float y, SuperBuild build) {
        int xx = Math.round(x);
        int yy = Math.round(y);
        if (Vars.world.build(xx, yy) != null) {
            setSuperBuilder(xx, yy, build);
        }

    }

    public static void clearBuild(float x, float y) {
        int xx = Math.round(x);
        int yy = Math.round(y);
        allBuild[xx][yy] = null;
    }

    public static boolean isBuild(float x, float y) {
        int xx = Math.round(x);
        int yy = Math.round(y);
        return allBuild[xx][yy] != null;
    }
}
