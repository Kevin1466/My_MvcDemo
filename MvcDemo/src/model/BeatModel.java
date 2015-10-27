package model;

import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import common.BPMObserver;
import common.BeatModelInterface;
import common.BeatObserver;

public class BeatModel implements BeatModelInterface, MetaEventListener {
	
	Sequencer sequencer;
	ArrayList beatObservers = new ArrayList<>();	//ц©р╩╫зед
	ArrayList bpmObservers = new ArrayList<>();		//
	int bpm = 90;	//default, 90 beats per minute
	
	Sequence sequence;
	Track track;

	@Override
	public void meta(MetaMessage message) {
		if(message.getType() == 47) {
			beatEvent();
			sequencer.start();
			setBPM(getBPM());
		}
	}

	private void beatEvent() {
		notifyBeatObservers();
	}

	@Override
	public void initialize() {
		setUpMidi();
		buildTrackAndStart();
	}

	@Override
	public void on() {
		sequencer.start();
		setBPM(90);
	}

	@Override
	public void off() {
		setBPM(0);
		sequencer.stop();
	}

	@Override
	public void setBPM(int bpm) {
		this.bpm = bpm;
		sequencer.setTempoInBPM(getBPM());
		notifyBPMObservers();
	}

	@Override
	public int getBPM() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void registerObserver(BPMObserver o) {
		beatObservers.add(o);
	}
	
	public void notifyBPMObservers() {
		for(int i = 0; i < bpmObservers.size(); i++) {
			BPMObserver observer = (BPMObserver)bpmObservers.get(i);
//			observer.updateBPM();
		}
	}

	@Override
	public void removeObserver(BPMObserver o) {
		int i = bpmObservers.indexOf(o);
		if(bpmObservers.size() >= 0) {
			bpmObservers.remove(i);
		}
	}

	@Override
	public void registerObserver(BeatObserver o) {
		beatObservers.add(o);
	}
	
	public void notifyBeatObservers() {
		for(int i = 0; i < beatObservers.size(); i++) {
			BeatObserver observer = (BeatObserver)beatObservers.get(i);
//			observer.updateBeat();
		}
	}

	@Override
	public void removeObserver(BeatObserver o) {
		int i = beatObservers.indexOf(o);
		if(beatObservers.size() >= 0) {
			beatObservers.remove(i);
		}
	}
	
	public void setUpMidi() {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.addMetaEventListener(this);
			sequence = new Sequence(Sequence.PPQ, 4);
			track = sequence.createTrack();
			sequencer.setTempoInBPM(getBPM());
		} catch (MidiUnavailableException | InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}
	
	public void buildTrackAndStart() {
		int[] trackList = {35, 0, 46, 0};
		
		sequence.deleteTrack(null);
		track = sequence.createTrack();
		makeTrack(trackList);
		track.add(makeEvent(192, 9, 1, 0, 4));
		try {
			sequencer.setSequence(sequence);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}
	
	public void makeTrack(int[] list) {
		for(int i = 0; i < list.length; i++) {
			int key = list[i];
			
			if(key != 0) {
				track.add(makeEvent(144, 9, key, 100, i));
				track.add(makeEvent(128, 9, key, 100, i + 1));
			}
		}
	}
	
	public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
		MidiEvent event = null;
		ShortMessage a = new ShortMessage();
		try {
			a.setMessage(comd, chan, one, two);
			event = new MidiEvent(a, tick);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
		return event;
	}
	
}
