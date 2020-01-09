package org.yakindu.scr;
import org.yakindu.scr.RuntimeService;
import org.yakindu.scr.lamp.LampStatemachine;

public class Main {

	public static void main(String[] args) {
		LampStatemachine sm = new LampStatemachine(); // Create your state machine
		InterfaceST ui = new InterfaceST(); // Create the instance of your interface handler
		ui.addEventListener(sm.getSCIUI()); // Add the event listener(s) to the button(s)
		sm.getSCIUI().setSCIUIOperationCallback(ui); // Link callback methods to statechart
		sm.setTimer(new TimerService()); // Set the timer service
		sm.init(); // Initialize the internal objects of the statechart
		sm.enter(); // Enter the initial state
		RuntimeService.getInstance().registerStatemachine(sm, 1); // 1 is the delay of the statechart’s internal clock.
	}
}
