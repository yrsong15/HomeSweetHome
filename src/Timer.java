
public class Timer{
	private long startTime;
	private float duration;
	
	Timer(){
		startTime = System.nanoTime();
	}
	
	public void resetTimer(){
		startTime = System.nanoTime();
		return;
	}
	
	public String getFormattedTime(){
		long currTime = System.nanoTime();
		duration = (currTime - startTime) / 1000000000f;
		String res = String.format("%.2f", duration);
		return res;
	}
	
	public String countdown(float limit){
		long currTime = System.nanoTime();
		duration = (currTime - startTime) / 1000000000f;
		float countdown = limit - duration;
		String res = String.format("%.2f", countdown);
		return res;
	}
	
	
	public float getTimeValue(){
		long currTime = System.nanoTime();
		duration = (currTime - startTime) / 1000000000f;
		return duration;
	}
	
}
