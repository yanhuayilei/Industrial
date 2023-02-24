
package Industrial.time;

import Industrial.Block.SuperBuild;
import arc.struct.Seq;
import java.util.Iterator;

public class Timer extends Thread {
    public static final Seq<Task> allTime = new Seq();

    public Timer() {
        this.setDaemon(true);
    }

    public void run() {
        while(true) {
            try {
                Thread.sleep(1000L);
                Iterator var1 = allTime.iterator();

                while(var1.hasNext()) {
                    Object obj = var1.next();
                    Task task = (Task)obj;
                    --task.currentTime;
                    task.update();
                }

                Object[] var7 = SuperBuild.alltimeTask.toArray();
                int var8 = var7.length;

                for(int var9 = 0; var9 < var8; ++var9) {
                    Object obj = var7[var9];
                    ifBuilding task = (ifBuilding)obj;
                    if (task.build.dead) {
                        SuperBuild.alltimeTask.remove(task);
                    } else {
                        --task.task.currentTime;
                        task.task.update();
                    }
                }
            } catch (InterruptedException var6) {
                var6.printStackTrace();
            }
        }
    }
}
