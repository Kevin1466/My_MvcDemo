package control;

import view.DJView;
import common.BeatModelInterface;

/**
 * Controller create views, so it needn't
 * refer controller to views.
 * @author renguangkai
 *
 */
public class BeatController implements ControllerInterface {
	
	/**
	 * Controller connect model and view, so
	 * it should hold the model and view references.
	 */
	BeatModelInterface model;
	DJView view;
	
	public BeatController(BeatModelInterface model) {
		this.model = model;
		view = new DJView(model, this);
		view.createView();
		view.createControls();
		view.disableStopMenuItem();
		view.enableStartMenuItem();
		model.initialize();	//?????????
	}

	@Override
	public void start() {
		model.on();
		view.disableStartMenuItem();
		view.enableStopMenuItem();
	}

	@Override
	public void stop() {
		model.off();
		view.disableStopMenuItem();
		view.enableStopMenuItem();
	}

	@Override
	public void increaseBPM() {
		int bpm = model.getBPM();
		model.setBPM(bpm + 1);
	}

	@Override
	public void decreaseBPM() {
		int bpm = model.getBPM();
		model.setBPM(bpm - 1);
	}

	@Override
	public void setBPM(int bpm) {
		model.setBPM(bpm);
	}

}
