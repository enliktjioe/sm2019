package org.yakindu.scr;
import java.awt.BorderLayout; 
import java.awt.Dimension; 
import java.awt.Graphics; 
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon; 
import javax.swing.JButton; 
import javax.swing.JFrame; 
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.yakindu.scr.lamp.ILampStatemachine.SCIUI;
import org.yakindu.scr.lamp.ILampStatemachine.SCIUIOperationCallback;

public class InterfaceST implements SCIUIOperationCallback { 
	
	Image image, on, off;
	JFrame frame; 
	JPanel imagePanel;
	
	JButton switchButton;
	JButton flashButton;
	
	
	@SuppressWarnings("serial") public InterfaceST() {
		
		on = new ImageIcon("bulb-on.png").getImage(); 
		off = new ImageIcon("bulb-off.png").getImage(); 
		image = off;
		
		imagePanel = new JPanel() {
				public void paintComponent(Graphics g) { g.drawImage(image, 0, 0, null); }
			};
		
		Dimension size = new Dimension(image.getWidth(null), image.getHeight(null) + 30); imagePanel.setPreferredSize(size);
		imagePanel.setMinimumSize(size); 
		imagePanel.setMaximumSize(size); 
		imagePanel.setSize(size);
		
		switchButton = new JButton("Switch");
		flashButton = new JButton("Flash");
		
		JPanel container = new JPanel(new BorderLayout());
		container.add(imagePanel, BorderLayout.PAGE_START);
		container.add(switchButton, BorderLayout.CENTER);
		container.add(flashButton, BorderLayout.PAGE_END);
		
		size = new Dimension(image.getWidth(null), image.getHeight(null) + 90); frame = new JFrame();
		frame.add(container);
		frame.setPreferredSize(size);
		frame.setResizable(true);
		frame.pack();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	@Override
	public void turnOff() {
		image = off;
		frame.repaint();
	}

	@Override
	public void turnOn() {
		image = on;
		frame.repaint();
	}
	
	public void addEventListener(final SCIUI sciui) { 
		
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
