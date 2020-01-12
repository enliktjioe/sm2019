package practice1; // <-- NB! Your name
 
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import practice1.lamp.ILampStatemachine.SCIUI;
import practice1.lamp.ILampStatemachine.SCIUIOperationCallback;  // <-- NB!
 
public class InterfaceST implements SCIUIOperationCallback {
    ImageIcon on = new ImageIcon("bulb-on.png");
    ImageIcon off = new ImageIcon("bulb-off.png");
    JLabel image = new JLabel(off);
    JButton switchButton = new JButton("Switch");
    JButton flashButton = new JButton("Flash");

 
    public InterfaceST() {
        JPanel container = new JPanel(new BorderLayout());
        container.add(image, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(switchButton);
        buttonPanel.add(flashButton);
        container.add(buttonPanel, BorderLayout.SOUTH);
 
        JFrame frame = new JFrame();
        frame.add(container);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

	@Override
	public void turnOff() {
		image.setIcon(off);
	}

	@Override
	public void turnOn() {
		image.setIcon(on);
	}

	public void addEventListener(SCIUI sciui) {
		switchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sciui.raiseSwitch();
			}
		});
		
		flashButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sciui.raiseFlash();
			}
		});
		
	}
}