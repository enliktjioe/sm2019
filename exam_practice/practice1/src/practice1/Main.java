package practice1;

import practice1.lamp.LampStatemachine;

public class Main {
	
	public static void main(String[] args) {
		LampStatemachine sm = new LampStatemachine();
		InterfaceST ui = new InterfaceST();
		ui.addEventListener(sm.getSCIUI());
		sm.getSCIUI().setSCIUIOperationCallback(ui);
		sm.setTimer(new TimerService());
		sm.init();
		sm.enter();
		RuntimeService.getInstance().registerStatemachine(sm, 100);
	}

}
