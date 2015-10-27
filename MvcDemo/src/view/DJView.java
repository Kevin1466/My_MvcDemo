package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import common.BPMObserver;
import common.BeatModelInterface;
import common.BeatObserver;
import control.ControllerInterface;

public class DJView extends JFrame implements ActionListener, BeatObserver, BPMObserver {
	BeatModelInterface model;
	ControllerInterface controller;	//controller is only used in controller interface
	
	//some view components
	JFrame viewFrame, controlFrame;
	JPanel viewPanel, controlPanel;
	BeatBar beatBar;
	
	
	JLabel bpmLabel, bpmOutputLabel;
	JTextField bpmTextField;
	JButton setBPMButton, increaseBPMButton, decreaseBPMButton;
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem startMenuItem, stopMenuItem;
	
	public DJView(BeatModelInterface model, ControllerInterface controller)
			throws HeadlessException {
		this.model = model;
		this.controller = controller;
		model.registerObserver((BeatObserver)this);
		model.registerObserver((BPMObserver)this);
	}
	
	//create all Swing components
	public void createView() {
		viewPanel = new JPanel(new GridLayout(1, 2));
		viewFrame = new JFrame("View");
		viewFrame.setSize(new Dimension(100, 80));
		
		bpmOutputLabel = new JLabel("offline", SwingConstants.CENTER);
		
		beatBar = new BeatBar();
		
		beatBar.setValue(0);
		
		JPanel bpmPanel = new JPanel(new GridLayout(2, 1));
		bpmPanel.add(beatBar);
		bpmPanel.add(bpmOutputLabel);
		
		viewPanel.add(bpmPanel);
		viewFrame.getContentPane().add(viewPanel, BorderLayout.CENTER);
		viewFrame.pack();
		viewFrame.setVisible(true);
	}
	
	public void createControls() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		controlFrame = new JFrame("Control");
		controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		controlFrame.setSize(new Dimension(100, 80));
		
		controlPanel = new JPanel(new GridLayout(1, 2));
		
		menuBar = new JMenuBar();
		menu = new JMenu("DJ Control");
		startMenuItem = new JMenuItem("Start");
		menu.add(startMenuItem);
		startMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.start();
			}
		});
		
		stopMenuItem = new JMenuItem("Stop");
		menu.add(stopMenuItem);
		stopMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.stop();
			}
		});
		
		JMenuItem exit = new JMenuItem("Quit");
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		menu.add(exit);
		menuBar.add(menu);
		controlFrame.setJMenuBar(menuBar);
		
		bpmTextField = new JTextField(2);
		bpmLabel = new JLabel("Enter BPM: ", SwingConstants.RIGHT);
		setBPMButton = new JButton("Set");
		setBPMButton.setSize(new Dimension(10, 40));
		increaseBPMButton = new JButton(">>");
		decreaseBPMButton = new JButton("<<");
		setBPMButton.addActionListener(this);
		increaseBPMButton.addActionListener(this);
		decreaseBPMButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		buttonPanel.add(decreaseBPMButton);
		buttonPanel.add(decreaseBPMButton);
		
		JPanel enterPanel = new JPanel(new GridLayout(1, 2));
		enterPanel.add(bpmLabel);
		enterPanel.add(bpmTextField);
		
		JPanel insideControlPanel = new JPanel(new GridLayout(3, 1));
		insideControlPanel.add(enterPanel);
		insideControlPanel.add(setBPMButton);
		insideControlPanel.add(buttonPanel);
		
		bpmLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		bpmOutputLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		controlFrame.getRootPane().setDefaultButton(setBPMButton);
		controlFrame.getContentPane().add(controlPanel, BorderLayout.CENTER);
		
		controlFrame.pack();
		controlFrame.setVisible(true);
	}
	
	public void enableStopMenuItem() {
		stopMenuItem.setEnabled(true);
	}
	
	public void disableStopMenuItem() {
		stopMenuItem.setEnabled(false);
	}
	
	public void enableStartMenuItem() {
		startMenuItem.setEnabled(true);
	}
	
	public void disableStartMenuItem() {
		startMenuItem.setEnabled(false);
	}
	
	public void updateBPM() {
		int bpm = model.getBPM();
		if(bpm == 0) {
			bpmOutputLabel.setText("offline");
		} else {
			bpmOutputLabel.setText("Current BPM: "+model.getBPM());
		}
	}
	
	public void updateBeatBar() {
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == setBPMButton) {
			int bpm = Integer.parseInt(bpmTextField.getText());
			controller.setBPM(bpm);
		} else if(event.getSource() == increaseBPMButton) {
			controller.increaseBPM();
		} else if(event.getSource() == decreaseBPMButton) {
			controller.decreaseBPM();
		}
	}

	@Override
	public void updateBeat() {
		// TODO Auto-generated method stub
		
	}
}
