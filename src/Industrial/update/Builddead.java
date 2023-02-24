
package Industrial.update;

import Industrial.Block.SuperBuild;
import Industrial.table.WorldTable;
import mindustry.Vars;
import mindustry.gen.Call;

public class Builddead implements Runnable {
    public Runnable runnable;
    public SuperBuild b;
    public final int id;

    public Builddead(Runnable run, SuperBuild b, int id) {
        this.id = id;
        this.runnable = run;
        this.b = b;
    }

    public void run() {
        if (!this.b.dead&&Vars.world.build(Math.round(this.b.build.x() / 8.0F), Math.round(this.b.build.y / 8.0F)) == this.b.build&&WorldTable.getSuperBuilder(Math.round(this.b.build.x() / 8.0F), Math.round(this.b.build.y / 8.0F))==this.b) {
            this.runnable.run();
            this.b.dead = false;
        } else {
            this.b.dead = true;
            this.b.dead();

            SuperBuild.allbuildupdate.remove(this);
            WorldTable.clearBuild(this.b.build.x / 8.0F, this.b.build.y / 8.0F);
        }

    }

    public void returnItems() {
        Vars.content.items().each((item) -> {
            int Y = (Integer)this.b.block.formulation.get(item);
            if (Y != 0) {
                Call.setItem(this.b.build.team().core(), item, Y + this.b.build.team().core().items().get(item));
            }
        });
    }
}
