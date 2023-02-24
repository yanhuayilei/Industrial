
package Industrial.time;

import Industrial.Block.SuperBuild;
import Industrial.table.WorldTable;
import mindustry.Vars;

public class ifBuilding implements Runnable {
    public Runnable r;
    public SuperBuild build;
    public Task task;

    public ifBuilding(Runnable r, int time, SuperBuild build) {
        this.r = r;
        this.build = build;
        this.task = new Task(time, this);
    }

    public void run() {
        if (!build.dead&&Vars.world.build(Math.round(this.build.build.x() / 8.0F), Math.round(this.build.build.y / 8.0F)) == this.build.build) {
            this.r.run();
            this.build.dead = false;
        }

    }
}
