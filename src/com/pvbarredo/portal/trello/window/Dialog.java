package com.pvbarredo.portal.trello.window;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

public class Dialog {

	public JFrame frame;

	

	/**
	 * Create the application.
	 */
	public Dialog(String message) {
		initialize(message);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String message) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextArea textArea = new JTextArea();
		frame.getContentPane().add(textArea, BorderLayout.CENTER);
		textArea.setText(message);
	}

}
