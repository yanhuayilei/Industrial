
package Industrial.update;

import java.util.HashSet;
import java.util.Set;

public class updateTask extends Thread {
    public static Set<Runnable> task = new HashSet();

    public updateTask() {
        this.setDaemon(true);
    }

    public void run() {
        while(true) {
            try {
                Object[] var1 = task.toArray();
                int var2 = var1.length;

                for(int var3 = 0; var3 < var2; ++var3) {
                    Object obj = var1[var3];
                    Runnable r = (Runnable)obj;
                    r.run();
                }

                sleep(1L);
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        }
    }
}
