package common;

public interface BeatModelInterface {
	public void initialize();
	public void on();
	public void off();
	public void setBPM(int bpm);
	
	public int getBPM();
	public void registerObserver(BPMObserver o);
	public void removeObserver(BPMObserver o);
	public void registerObserver(BeatObserver o);
	public void removeObserver(BeatObserver o);
}
