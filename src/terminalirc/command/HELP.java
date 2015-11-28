/*
 $ Copyright (c) 2014 Jasper Reddin
 $ All rights reserved.
 */
package terminalirc.command;

import terminalirc.Client;

/**
 *
 * @author jasper
 */
public class HELP implements Command{
	
	Client client;

	public HELP(Client client) {
		this.client = client;
	}

	@Override
	public boolean execute(String[] args) {
		for(Command c:client.getCommands()){
			terminalirc.TerminalIRC.printlnWithoutStashing("/"+c.getName()+": "+c.getDesc());
		}
		return true;
	}

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public String getShortcut() {
		return "help";
	}

	@Override
	public String getUsage() {
		return "/help or /help <command>";
	}

	@Override
	public String getDesc() {
		return "Lists all the commands.";
	}
	
}
