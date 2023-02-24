
package Industrial.time;

public class Task {
    public Runnable run;
    public final int time;
    public int currentTime = 0;

    public Task(int time, Runnable run) {
        this.run = run;
        this.currentTime = time;
        this.time = time;
    }

    public void update() {
        if (this.currentTime == 0) {
            this.currentTime = this.time;
            this.run.run();
        }

    }
}
