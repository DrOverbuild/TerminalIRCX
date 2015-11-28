/*
 $ Copyright (c) 2014 Jasper Reddin
 $ All rights reserved.
 */
package terminalirc.command;

import terminalirc.Client;
import terminalirc.TerminalIRC;

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
		if(args.length == 1){
			Command c = client.parseCommand(args[0]);
			if (c == null){
				TerminalIRC.printlnWithoutStashing("Unknown command.");
				return true;
			}

			TerminalIRC.printlnWithoutStashing("");
			if (c.getName().equalsIgnoreCase(c.getShortcut())){
				TerminalIRC.printlnWithoutStashing("/"+c.getName());
			}else {
				TerminalIRC.printlnWithoutStashing("/" + c.getName() + " or /" + c.getShortcut());
			}
			TerminalIRC.printlnWithoutStashing(c.getDesc());
			TerminalIRC.printlnWithoutStashing("Usage: " + c.getUsage());
			TerminalIRC.printlnWithoutStashing("");
			return true;
		}
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
		return "Lists all the commands or gives information about a specific command.";
	}
	
}
