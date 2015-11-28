/*
 $ Copyright (c) 2014 Jasper Reddin
 $ All rights reserved.
 */
package terminalirc.command;

import terminalirc.TerminalIRC;

/**
 *
 * @author jasper
 */
public class LINE implements Command{

	@Override
	public boolean execute(String[] args) {
		int width = TerminalIRC.console.getTerminal().getWidth();
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < width; i++){
			builder = builder.append("-");
		}
		
		TerminalIRC.printlnWithoutStashing(builder.toString());
		return true;
	}

	@Override
	public String getName() {
		return "line";
	}

	@Override
	public String getShortcut() {
		return "l";
	}

	@Override
	public String getUsage() {
		return "/line";
	}

	@Override
	public String getDesc() {
		return "Places a line.";
	}
	
}
