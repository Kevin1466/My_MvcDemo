package test;

import model.BeatModel;
import common.BeatModelInterface;
import control.BeatController;
import control.ControllerInterface;

public class DJTestDriver {

	public static void main(String[] args) {
		BeatModelInterface model = new BeatModel();
		ControllerInterface controller = new BeatController(model);
	}

}
