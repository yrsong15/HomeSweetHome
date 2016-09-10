import javafx.animation.AnimationTimer;

public class Timer{
	
	AnimationTimer timer;
	boolean running = false;
    double time;

	public Timer(){
        
        timer = new AnimationTimer() {
        private long startTime;
        @Override
        public void start() {
            startTime = System.currentTimeMillis();
            running = true;
            super.start();
        }
        @Override
        public void stop() {
            running = false;
            super.stop();
        }
        @Override
        public void handle(long timestamp) {
            long now = System.currentTimeMillis();
//                time.set((now - startTime) / 1000.0);
            time = now - startTime / 1000.0;
        }   
        };
	}
	
	public double getTime(){
		return time;
	}
}
