package test;

import control.ControllerInterface;
import control.HeartController;
import model.HeartModel;

public class HeartTestDriver {

	public static void main(String[] args) {
		HeartModel heartModel = new HeartModel();
		ControllerInterface model = new HeartController(heartModel);
	}

}
